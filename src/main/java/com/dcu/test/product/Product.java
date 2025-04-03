package com.dcu.test.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false, unique = true)
    private String title;
    private Integer price;

    @Column(length = 100)
    private String company;
    private LocalDate release_date;
    private String category;

}
