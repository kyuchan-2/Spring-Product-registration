package com.dcu.test.product;

import com.dcu.test.ProductCreateDTO;
import com.dcu.test.ProductDTO;
import com.dcu.test.ProductUpdateDTO;
import com.dcu.test.security.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final FileService fileService;

    // 이미지 업로드 경로 설정
    private final String imageUploadDir = "src/main/resources/static/images/";

    // 상품 목록 조회
    @GetMapping({"/", "/productList"})
    String productList(String keyword, Model model) {
        List<ProductDTO> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        return "product/productList";  // 상품 목록 화면
    }

    // 상품 등록 페이지
    @GetMapping("/productRegister")
    String productRegister() {
        return "product/productRegistration";
    }

    // 상품 등록 처리
    @PostMapping("/productRegister")
    String productRegister(@ModelAttribute ProductCreateDTO productCreateDTO) throws IOException {
        // 이미지 파일 업로드
        String imagePath = fileService.imageSave(productCreateDTO.getImage());
        productService.productCreate(productCreateDTO, imagePath);  // 상품 생성
        return "redirect:/productList";
    }

    // 상품 상세 보기
    @GetMapping("/productDetail/{id}")
    String productDetail(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.productFindById(id);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "product/productDetail";  // 상품 상세 화면
        } else {
            return "redirect:/productList";  // 상품이 없으면 목록으로 돌아감
        }
    }

    // 상품 수정 페이지
    @GetMapping("/productEdit/{id}")
    String productEdit(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.productFindById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "product/productEdit";  // 상품 수정 페이지
        } else {
            return "redirect:/productList";
        }
    }

    // 상품 수정 처리
    @PostMapping("/productEdit")
    String productEdit(@ModelAttribute ProductUpdateDTO productUpdateDTO) {
        productService.productUpdate(productUpdateDTO);  // 상품 저장
        return "redirect:/productList";
    }

    // 상품 삭제 처리
    @PostMapping("/productDelete")
    String productDelete(@ModelAttribute Product product) {
        productService.productDelete(product.getId());
        return "redirect:/productList";
    }
}
