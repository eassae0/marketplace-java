package marketplace.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.ProductCreateRequest;
import marketplace.dto.request.ProductUpdateRequest;
import marketplace.dto.response.ProductResponse;
import marketplace.entity.Product;
import marketplace.mapper.ProductMapper;
import marketplace.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreateRequest request) {
        Product product = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toResponse(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        Product product = productService.getById(id);
        return ResponseEntity.ok(ProductMapper.toResponse(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        List<Product> products = productService.getAll();
        List<ProductResponse> responses = products.stream().map(ProductMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request) {
        Product product = productService.update(id, request);
        return ResponseEntity.ok(ProductMapper.toResponse(product));
    }
}
