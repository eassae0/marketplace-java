package marketplace.mapper;

import marketplace.dto.request.CategoryCreateRequest;
import marketplace.dto.response.CategoryResponse;
import marketplace.entity.Category;

public final class CategoryMapper {
    private CategoryMapper() {}

    public static Category toEntity(CategoryCreateRequest request) {
        Category category = new Category();
        category.setName(request.name());
        return category;
    }

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
