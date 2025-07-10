package com.saani.shopify.requests.ProductRequests;

import com.saani.shopify.models.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private Category category;
}
