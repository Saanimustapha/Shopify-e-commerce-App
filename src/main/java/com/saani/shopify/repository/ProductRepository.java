package com.saani.shopify.repository;

import com.saani.shopify.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {
    List<Products> findByCategoryName(String category);
    List<Products> findByBrand(String brand);
    List<Products> findByCategoryNameAndBrand(String category, String brand);
    List<Products> findByName(String name);
    List<Products> findByNameAndBrand(String name, String brand);
    Long countByNameAndBrand(String name, String brand);
}
