package marketplace.mapper;

import marketplace.dto.request.ProductCreateRequest;
import marketplace.dto.response.ProductResponse;
import marketplace.entity.Product;

public final class ProductMapper {
    private ProductMapper() {}

    public static Product toEntity(ProductCreateRequest request) {
        Product product = new Product();;
        product.setTitle(request.title());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());

        return product;
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getQuantity(),
                CategoryMapper.toResponse(product.getCategory())
        );
    }
}
