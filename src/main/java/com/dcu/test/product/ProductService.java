package com.dcu.test.product;

import com.dcu.test.ProductCreateDTO;
import com.dcu.test.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 모든 상품 조회
    List<ProductDTO> productFindAll() {
        return productRepository.findAll().stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setImage(product.getImage());
            productDTO.setTitle(product.getTitle());
            productDTO.setPrice(product.getPrice());
            return productDTO;
        }).collect(Collectors.toList());
    }

    // 검색 기능 추가
    public List<ProductDTO> searchProducts(String keyword) {
        return productRepository.findByTitleContainingOrCompanyContaining(keyword, keyword).stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(product.getId());
                    productDTO.setImage(product.getImage());
                    productDTO.setTitle(product.getTitle());
                    productDTO.setPrice(product.getPrice());
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

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchDataFromExternalApi() {
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=Cv1RB2yD%2FK8O58G0AZl%2B80CpfMtlQaqjizVCy0OVTle8QZjguLdmnR9E9hZ9cklZEFuN0SpKMgbPhu0TodPbpA%3D%3D&numOfRows=10&pageNo=1&base_date=20250329&base_time=0600&nx=55&ny=127&dataType=JSON";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response);
        return response.getBody();
    }
}
