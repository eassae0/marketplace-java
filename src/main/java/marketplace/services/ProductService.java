package marketplace.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.ProductCreateRequest;
import marketplace.dto.request.ProductUpdateRequest;
import marketplace.entity.Category;
import marketplace.entity.Product;
import marketplace.exceptions.CategoryNotFoundException;
import marketplace.exceptions.ProductAlreadyExistsException;
import marketplace.exceptions.ProductNotFoundException;
import marketplace.mapper.ProductMapper;
import marketplace.repositories.CategoryRepository;
import marketplace.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product create(ProductCreateRequest request) {
        if (productRepository.existsByTitle(request.title())) {
            throw new ProductAlreadyExistsException(request.title());
        }
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));
        Product product = ProductMapper.toEntity(request);
        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        productRepository.delete(getById(id));
    }

    @Transactional
    public Product update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));

        if (!request.title().equals(product.getTitle())
        && productRepository.existsByTitle(request.title())) {
            throw new ProductAlreadyExistsException(request.title());
        }

        product.setTitle(request.title());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setCategory(category);
        return product;
    }


}
