package com.dcu.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

@Controller
public class MainController {
    @GetMapping("/")
    String index() {
        return "./index.html";
    }
    @GetMapping("/Login")
    @ResponseBody
    String Login() {
        return "로그인";
    }


    @GetMapping("/Time")
    @ResponseBody
    String Time() {
        return ZonedDateTime.now().toString();
    }
}
