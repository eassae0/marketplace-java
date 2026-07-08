import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final String url = "jdbc:postgresql://localhost:5433/postgres";
    private final String user = "postgres";
    private final String password = "root";


    List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        String sqlQuery = "SELECT * FROM products";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");
                    int category_id = resultSet.getInt("category_id");

                    Product product = new Product(id, title, price, quantity, category_id);

                    products.add(product);
                }
            }
        } catch (Exception e) {
            System.out.println("\nError occurred while executing findAll in ProductRepository: ");
            e.printStackTrace();
        }
        return products;
    }

    public void printAllProducts() {
        for (Product product : findAllProducts()) {
            System.out.println(product.toString());
        }
    }

    public void saveCategory(String name) {
        String checkQuery = "SELECT COUNT(*) FROM categories WHERE name = ?";
        String insertQuery = "INSERT INTO categories (name) VALUES (?)";

        try(Connection connection = DriverManager.getConnection(url, user, password)) {

            try(PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, name);
                try(ResultSet resultSet = checkStmt.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.println("\nCategory " + name + " already exists!\n");
                        return;
                    }
                }
            }

            try(PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, name);
                insertStmt.executeUpdate();
                System.out.println("\nCategory successfully saved to database!\n");
            }
        } catch (Exception e) {
            System.out.println("\nError occurred while executing saveCategories in ProductRepository: ");
            e.printStackTrace();
        }
    }

    public void saveProduct(Product product) {
        String checkQuery = "SELECT COUNT(*) FROM products WHERE title = ?";
        String insertQuery = "INSERT INTO products (title, price, quantity, category_id) VALUES (?, ?, ?, ?)";
        String updateQuery = "UPDATE products SET quantity = quantity + ? WHERE title = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            boolean productExist = false;

            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, product.getTitle());
                try (ResultSet checkResultSet = checkStatement.executeQuery()) {
                    if (checkResultSet.next() && checkResultSet.getInt(1) > 0) {
                        productExist = true;
                    }
                }
            }

            if (productExist) {
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, product.getQuantity());
                    updateStmt.setString(2, product.getTitle());

                    updateStmt.executeUpdate();
                    System.out.println("\nProduct '" + product.getTitle() + "' already exists. Quantity updated!\n");
                }
            } else {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, product.getTitle());
                    preparedStatement.setDouble(2, product.getPrice());
                    preparedStatement.setInt(3, product.getQuantity());
                    preparedStatement.setInt(4, product.getCategoryId());

                    preparedStatement.executeUpdate();
                    System.out.println("\nProduct successfully saved to database!\n");
                }
            }

        } catch (Exception e) {
            System.out.println("\nError occurred while executing saveProduct in ProductRepository: ");
            e.printStackTrace();
        }
    }

    public void deleteCategory(int id) {
        String deleteQuery = "DELETE FROM categories WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStmt = connection.prepareStatement(deleteQuery)) {

            preparedStmt.setInt(1, id);

            int rowsDeleted = preparedStmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Category with ID: " + id + " was successfully deleted.");
            } else {
                System.out.println("Category with ID: " + id + " not found. Nothing was deleted.");
            }

        } catch (Exception e) {
            System.out.println("\nError occurred while executing deleteCategory in ProductRepository: ");
            e.printStackTrace();
        }
    }

    public void deleteCategory(String name) {
        String deleteQuery = "DELETE FROM categories WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStmt = connection.prepareStatement(deleteQuery)) {

            preparedStmt.setString(1, name);

            int rowsDeleted = preparedStmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Category with name: " + name + " was successfully deleted.");
            } else {
                System.out.println("Category with name: " + name + " not found. Nothing was deleted.");
            }

        } catch (Exception e) {
            System.out.println("\nError occurred while executing deleteCategory in ProductRepository: ");
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String deleteQuery = "DELETE FROM products WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStmt = connection.prepareStatement(deleteQuery)) {

            preparedStmt.setInt(1, id);

            int rowsDeleted = preparedStmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Product with ID: " + id + " was successfully deleted.");
            } else {
                System.out.println("Product with ID: " + id + " not found. Nothing was deleted.");
            }

        } catch (Exception e) {
            System.out.println("\nError occurred while executing deleteProduct in ProductRepository: ");
            e.printStackTrace();
        }
    }

    public void deleteProduct(String title) {
        String deleteQuery = "DELETE FROM products WHERE title = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStmt = connection.prepareStatement(deleteQuery)) {

            preparedStmt.setString(1, title);

            int rowsDeleted = preparedStmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Product with title: " + title + " was successfully deleted.");
            } else {
                System.out.println("Product with title: " + title + " not found. Nothing was deleted.");
            }

        } catch (Exception e) {
            System.out.println("\nError occurred while executing deleteProduct in ProductRepository: ");
            e.printStackTrace();
        }
    }
}

