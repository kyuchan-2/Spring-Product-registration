package com.dcu.test;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(columnDefinition = "TEXT")
    public String image;

    @Column(nullable = false, unique = true)
    public String title;
    public Integer price;

    @Column(length = 100)
    public String company;
    public LocalDate release_date;
}
