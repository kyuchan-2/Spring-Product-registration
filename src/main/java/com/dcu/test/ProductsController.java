package com.dcu.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductsController {
    private final ProductRepository productRepository;

    @GetMapping("/productList")
    String productList(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/productRegister")
    String productRegister(Model model) {
        return "productRegistration";
    }

    @PostMapping("/productRegister")
    String productRegister(@ModelAttribute Product product) {
        System.out.println(product.getImage());
        System.out.println(product.getTitle());
        System.out.println(product.getCompany());
        System.out.println(product.getPrice());
        System.out.println(product.getRelease_date());

        // JPA를 통해 데이터베이스에 저장
        productRepository.save(product);

        // 저장 후 상품 목록 페이지로 이동
        return "redirect:/productList";
    }
}
