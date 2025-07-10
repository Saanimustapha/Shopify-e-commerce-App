package com.saani.shopify.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer quantity;
    private String description;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="category_id")
    private Category category;


    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Images> images;

    public Products(String name, String brand, BigDecimal price, Integer quantity, String description, Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
    }
}
