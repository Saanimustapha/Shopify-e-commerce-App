package com.saani.shopify.services.product;

import com.saani.shopify.exceptions.ProductNotFoundException;
import com.saani.shopify.models.Products;
import com.saani.shopify.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInterface{

    private ProductRepository productRepository;

    @Override
    public Products addProduct(Products product) {
        return null;
    }

    @Override
    public Products getProductById(Long Id) {
        return productRepository.findById(Id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public List<Products> getAllProducts() {
        return List.of();
    }

    @Override
    public List<Products> getProductByName(String name) {
        return List.of();
    }

    @Override
    public List<Products> getProductsByNameAndBrand(String name, String brand) {
        return List.of();
    }

    @Override
    public List<Products> getProductsByCategory(String category) {
        return List.of();
    }

    @Override
    public List<Products> getProductsByCategoryAndBrand(String category, String brand) {
        return List.of();
    }

    @Override
    public List<Products> getProductsByBrand(String brand) {
        return List.of();
    }

    @Override
    public void updateProduct(Products product, Long productId) {

    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public Long countProductsByNameAndBrand(String name, String brand) {
        return 0L;
    }
}
