package com.dcu.test.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
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

    void productCreate(String imagePath, String title, Integer price, String company, LocalDate release_date) {
        Product product = new Product();
        product.setImage(imagePath);
        product.setTitle(title);
        product.setPrice(price);
        product.setCompany(company);
        product.setRelease_date(release_date);
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
        // API URL
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=Cv1RB2yD%2FK8O58G0AZl%2B80CpfMtlQaqjizVCy0OVTle8QZjguLdmnR9E9hZ9cklZEFuN0SpKMgbPhu0TodPbpA%3D%3D&numOfRows=10&pageNo=1&base_date=20250329&base_time=0600&nx=55&ny=127&dataType=JSON";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response);

        return response.getBody();
    }


}