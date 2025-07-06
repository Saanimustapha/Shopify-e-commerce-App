package com.saani.shopify.services.product;

import com.saani.shopify.models.Products;

import java.util.List;

public interface ProductServiceInterface {
    Products addProduct(Products product);
    Products getProductById(Long Id);
    List<Products> getAllProducts();
    List<Products> getProductByName(String name);
    List<Products> getProductsByNameAndBrand(String name,String brand);
    List<Products> getProductsByCategory(String category);
    List<Products> getProductsByCategoryAndBrand(String category,String brand);
    List<Products> getProductsByBrand(String brand);
    void updateProduct(Products product,Long productId);
    void deleteProduct(Long productId);
    Long countProductsByNameAndBrand(String name,String brand);



}
