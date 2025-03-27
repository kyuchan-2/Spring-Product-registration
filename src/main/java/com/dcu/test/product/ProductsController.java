package com.dcu.test.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;



    @GetMapping({"/","/productList"})
    String productList(Model model) {
        List<Product> products = productService.productFindAll();
        model.addAttribute("products", products);
        return "product/productList";
    }

    @GetMapping("/productRegister")
    String productRegister() {
        return "product/productRegistration";
    }

    @PostMapping("/productRegister")
    String productRegister(@ModelAttribute Product product) {
        // JPA를 통해 데이터베이스에 저장
        productService.productSave(product);
        // 저장 후 상품 목록 페이지로 이동
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
    String productEdit(@ModelAttribute Product product) {
        productService.productSave(product);
        return "redirect:/productList";
    }

    @PostMapping("/productDelete")
    String productDelete(@ModelAttribute Product product) {
        productService.productDelete(product.getId());
        return "redirect:/productList";
    }


}
