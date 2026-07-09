import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductRepository {

    List<Product> findAllProducts() {
        List<Product> products = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            products = session.createQuery("FROM Product", Product.class).getResultList();
        } catch (Exception e) {
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
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Category existingCategory = session.createQuery("FROM Category WHERE name = :nameParam", Category.class)
                    .setParameter("nameParam", name)
                    .uniqueResult();

            if (existingCategory != null) {
                System.out.println("Category " + name + "already existed!");
            } else {
                session.persist(new Category(0, name));
                System.out.println("Category " + name + " successfully saved!");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Product findByTitle(String title) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Product WHERE title = :titleParam", Product.class)
                    .setParameter("titleParam", title)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveProduct(Product newProduct) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Product existingProduct = session.createQuery("FROM Product WHERE title = :titleParam", Product.class)
                    .setParameter("titleParam", newProduct.getTitle())
                    .uniqueResult();

            if (existingProduct != null) {
                existingProduct.setQuantity(existingProduct.getQuantity() + newProduct.getQuantity());
                existingProduct.setPrice(newProduct.getPrice());
            } else {
                session.persist(newProduct);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteCategory(int id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Category category = session.get(Category.class, id);
            session.remove(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteCategory(String name) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Category category = session.createQuery("FROM Category WHERE name = :nameParam", Category.class)
                    .setParameter("nameParam", name)
                    .uniqueResult();
            if (category != null) {
                session.remove(category);
                System.out.println("Category " + name + " successfully deleted.");
            } else {
                System.out.println("Category " + name + " not found.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.remove(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteProduct(String title) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = session.createQuery("FROM Product WHERE title = :titleParam", Product.class)
                    .setParameter("titleParam", title)
                    .uniqueResult();
            if (product != null) {
                session.remove(product);
                System.out.println("Product " + title + " successfully deleted.");
            } else {
                System.out.println("Product " + title + " not found.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }
}

