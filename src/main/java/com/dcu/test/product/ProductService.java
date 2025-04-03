package com.dcu.test.product;

import com.dcu.test.ProductCreateDTO;
import com.dcu.test.ProductDTO;
import com.dcu.test.ProductUpdateDTO;
import com.dcu.test.security.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final FileService fileService;

    // 모든 상품 조회
    Page<ProductDTO> productListPageination(String keyword, Pageable pageable) {
        Page<Product> productPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            productPage = productRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }
            return productPage.map(product -> {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(product.getId());
                productDTO.setTitle(product.getTitle());
                productDTO.setImage(product.getImage());
                productDTO.setPrice(product.getPrice());
                return productDTO;
            });
    }

    void productUpdate(ProductUpdateDTO productUpdateDTO) {
        Product product = productRepository.findById(productUpdateDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));
        String imagePath = productUpdateDTO.getOriginalImage();
        if (productUpdateDTO.getImage() != null && !productUpdateDTO.getImage().isEmpty()) {
            try {
                fileService.fileDelete(String.valueOf(Paths.get(productUpdateDTO.getOriginalImage()).getFileName()));
                imagePath = fileService.imageSave(productUpdateDTO.getImage());
            } catch (IOException e) {
                throw new RuntimeException("이미지 처리 중 오류 발생", e);
            }
        }
        product.setImage(imagePath);
        product.setTitle(productUpdateDTO.getTitle());
        product.setPrice(productUpdateDTO.getPrice());
        product.setCompany(productUpdateDTO.getCompany());
        product.setRelease_date(productUpdateDTO.getRelease_date());
        product.setCategory(productUpdateDTO.getCategory());
        productRepository.save(product);
    }

    // 검색 기능 추가
    public List<ProductDTO> searchProducts(String keyword, Pageable pageable) {
        Page<Product> productPage;
        if(keyword != null && !keyword.trim().isEmpty()) {
            productPage = productRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        return productPage.stream().map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(product.getId());
                    productDTO.setImage(product.getImage());
                    productDTO.setTitle(product.getTitle());
                    productDTO.setCompany(product.getCompany());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setCategory(product.getCategory());
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    void productCreate(ProductCreateDTO productCreateDTO, String imagePath) {
        Product product = new Product();
        product.setImage(imagePath);
        product.setTitle(productCreateDTO.getTitle());
        product.setPrice(productCreateDTO.getPrice());
        product.setCompany(productCreateDTO.getCompany());
        product.setRelease_date(productCreateDTO.getRelease_date());
        product.setCategory(productCreateDTO.getCategory());
        productRepository.save(product);
    }

    void productSave(Product product) {
        productRepository.save(product);
    }

    Optional<Product> productFindById(Long id) {
        return productRepository.findById(id);
    }

    void productDelete(Long id) {
        productRepository.deleteById(id);
    }
}
