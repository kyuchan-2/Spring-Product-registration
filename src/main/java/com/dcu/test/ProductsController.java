package com.dcu.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "productRegister";
    }
}
