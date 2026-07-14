package marketplace.services;

import lombok.RequiredArgsConstructor;
import marketplace.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
}
