package marketplace.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.CategoryUpdateRequest;
import marketplace.entity.Category;
import marketplace.exceptions.CategoryAlreadyExistsException;
import marketplace.exceptions.CategoryNotFoundException;
import marketplace.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category add(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistsException(category.getName());
        }
        return categoryRepository.save(category);
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.delete(getById(id));
    }

    @Transactional
    public Category update(Long id, CategoryUpdateRequest request) {
        Category category = getById(id);
        if (!Objects.equals(category.getName(), request.name())
                && categoryRepository.existsByName(request.name())) {
            throw new CategoryAlreadyExistsException(request.name());
        }
        category.setName(request.name());
        return category;
    }
}
