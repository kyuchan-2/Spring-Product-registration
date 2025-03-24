package com.dcu.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductsController {
    @GetMapping("/productList")
    String productsList(Model model) {
        model.addAttribute("phone1", "Galaxy S25 Ultra");
        model.addAttribute("price1", "1,731,400원");
        model.addAttribute("phone2", "Galaxy Z Fold6");
        model.addAttribute("price2", "2,335,400원");
        model.addAttribute("phone3", "Galaxy Z Flip6");
        model.addAttribute("price3", "1,405,300원");
        return "./productList.html";
    }
}
