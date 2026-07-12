package marketplace.repositories;

import marketplace.entity.Category;
import marketplace.entity.Product;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProductRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public ProductRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    List<Product> findAllProducts() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Product", Product.class)
                .getResultList();
    }

    public void printAllProducts() {
        for (Product product : findAllProducts()) {
            System.out.println(product.toString());
        }
    }

    @Transactional(readOnly = true)
    public Category findCategoryByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Category WHERE name = :nameParam", Category.class)
                .setParameter("nameParam", name)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public Product findProductByTitle(String title) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Product WHERE title = :titleParam", Product.class)
                .setParameter("titleParam", title)
                .uniqueResult();
    }

    public void saveCategory(String name) {
        Session session = sessionFactory.getCurrentSession();

        Category existingCategory = session.createQuery("FROM Category WHERE name = :nameParam", Category.class)
                .setParameter("nameParam", name)
                .uniqueResult();

        if (existingCategory != null) {
            System.out.println("Category " + name + " already existed!");
        } else {
            session.persist(new Category(0, name));
            System.out.println("Category " + name + " successfully saved!");
        }
    }

    public void saveProduct(Product newProduct) {
        Session session = sessionFactory.getCurrentSession();
        Product existingProduct = session.createQuery("FROM Product WHERE title = :titleParam", Product.class)
                .setParameter("titleParam", newProduct.getTitle())
                .uniqueResult();

        if (existingProduct != null) {
            existingProduct.setQuantity(existingProduct.getQuantity() + newProduct.getQuantity());
            existingProduct.setPrice(newProduct.getPrice());
        } else {
            session.persist(newProduct);
        }
    }

    public void deleteCategory(int id) {
        Session session = sessionFactory.getCurrentSession();
        Category category = session.find(Category.class, id);

        if (category != null) {
            session.remove(category);
            System.out.println("Category " + category.getName() + " successfully deleted.");
        } else {
            System.out.println("Category id = " + id + " not found.");
        }
    }

    public void deleteCategory(String name) {
        Session session = sessionFactory.getCurrentSession();
        Category category = session.createQuery("FROM Category WHERE name = :nameParam", Category.class)
                .setParameter("nameParam", name)
                .uniqueResult();
        if (category != null) {
            session.remove(category);
            System.out.println("Category " + name + " successfully deleted.");
        } else {
            System.out.println("Category " + name + " not found.");
        }
    }

    public void deleteProduct(int id) {
        Session session = sessionFactory.getCurrentSession();

        Product product = session.find(Product.class, id);

        if (product != null) {
            session.remove(product);
            System.out.println("Product " + product.getTitle() + " successfully deleted.");
        } else {
            System.out.println("Product id = " + id + " not found.");
        }

    }

    public void deleteProduct(String title) {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.createQuery("FROM Product WHERE title = :titleParam", Product.class)
                .setParameter("titleParam", title)
                .uniqueResult();
        if (product != null) {
            session.remove(product);
            System.out.println("Product " + title + " successfully deleted.");
        } else {
            System.out.println("Product " + title + " not found.");
        }
    }
}


