package com.dcu.test.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 모든 상품 조회 메서드
    List<Product> productFindAll() {
        return productRepository.findAll();
    }

    void productSave(Product product) {
        productRepository.save(product);
    }

    Optional<Product> productFinById(Long id) {
        return productRepository.findById(id);
    }

    void productDelete(Long id) {
        productRepository.deleteById(id);
    }

}