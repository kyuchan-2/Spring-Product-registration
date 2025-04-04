package com.dcu.test;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String image;
    private String title;
    private Integer price;
    private String company;
    private LocalDate release_date;
    private String category;
}
