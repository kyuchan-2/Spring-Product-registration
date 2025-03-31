package com.dcu.test;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class ProductCreateDTO {
    private MultipartFile image;
    private String title;
    private Integer price;
    private String company;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate release_date;
}
