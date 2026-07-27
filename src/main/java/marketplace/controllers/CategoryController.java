package marketplace.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.CategoryCreateRequest;
import marketplace.dto.request.CategoryUpdateRequest;
import marketplace.dto.response.CategoryResponse;
import marketplace.entity.Category;
import marketplace.mapper.CategoryMapper;
import marketplace.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryCreateRequest request) {
        Category category = CategoryMapper.toEntity(request);
        Category savedCategory = categoryService.add(category);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryMapper.toResponse(savedCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> get(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(CategoryMapper.toResponse(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        List<Category> categories = categoryService.getAll();

        List<CategoryResponse> responseCategories = categories.stream()
                .map(CategoryMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseCategories);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody CategoryUpdateRequest request) {
        Category category = categoryService.update(id, request);
        return ResponseEntity.ok(CategoryMapper.toResponse(category));
    }
}
