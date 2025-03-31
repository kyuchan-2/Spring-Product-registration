package com.dcu.test.product;

import com.dcu.test.ProductCreateDTO;
import com.dcu.test.ProductDTO;
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
    private final ProductRepository productRepository;
    private final FileService fileService;

    // 이미지 업로드 경로 설정
    private final String imageUploadDir = "src/main/resources/static/images/";

    @GetMapping({"/", "/productList"})
    String productList(Model model) {
        List<ProductDTO> products = productService.productFindAll();
        model.addAttribute("products", products);
        return "product/productList";
    }

    @GetMapping("/productRegister")
    String productRegister() {
        return "product/productRegistration";
    }

    @PostMapping("/productRegister")
    String productRegister(@ModelAttribute ProductCreateDTO productCreateDTO) throws IOException {
        // 파일이 업로드되었을 경우 처리
        String imagePath = fileService.imageSave(productCreateDTO.getImage());
        productService.productCreate(productCreateDTO, imagePath);
        return "redirect:/productList";
    }

    @GetMapping("/productDetail/{id}")
    String productDetail(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.productFindById(id);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "product/productDetail";
        } else {
            return "redirect:/productList";
        }
    }

    @GetMapping("/productEdit/{id}")
    String productEdit(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.productFindById(id);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "product/productEdit";
        } else {
            return "redirect:/productList";
        }
    }

    @PostMapping("/productEdit")
    String productEdit(@ModelAttribute Product product, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        // 파일이 업로드되었을 경우 처리
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            Path targetLocation = Paths.get(imageUploadDir + fileName);
            Files.copy(imageFile.getInputStream(), targetLocation);
            product.setImage("images/" + fileName);  // 이미지 경로 설정
        }

        // 상품 수정 저장
        productService.productSave(product);
        return "redirect:/productList";
    }

    @PostMapping("/productDelete")
    String productDelete(@ModelAttribute Product product) {
        productService.productDelete(product.getId());
        return "redirect:/productList";
    }
}
