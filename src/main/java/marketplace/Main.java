package marketplace;

import marketplace.entity.Category;
import marketplace.entity.Product;
import marketplace.repositories.CategoryRepository;
import marketplace.repositories.ProductRepository;
import marketplace.services.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);

        ProductRepository productRepository = context.getBean(ProductRepository.class);
        CategoryRepository categoryRepository = context.getBean(CategoryRepository.class);


    }
}