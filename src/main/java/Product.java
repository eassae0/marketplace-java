public class Product {

    private int id;
    private String title;
    private double price;
    private int quantity;
    private int categoryId;

    public Product() {
    }

    public Product(int id, String title, double price, int quantity, int categoryId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Цена: %.2f руб. | Количество: %d шт. | Категория: %d",
                id, title, price, quantity, categoryId);
    }
}