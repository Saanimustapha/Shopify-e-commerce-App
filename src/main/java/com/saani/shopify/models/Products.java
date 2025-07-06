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
@AllArgsConstructor
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
}
