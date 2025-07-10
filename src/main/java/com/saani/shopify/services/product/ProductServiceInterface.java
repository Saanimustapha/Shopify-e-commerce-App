package com.saani.shopify.services.product;

import com.saani.shopify.models.Products;
import com.saani.shopify.requests.ProductRequests.AddProductRequest;
import com.saani.shopify.requests.ProductRequests.ProductUpdateRequest;

import java.util.List;

public interface ProductServiceInterface {
    Products addProduct(AddProductRequest request);
    Products getProductById(Long Id);
    List<Products> getAllProducts();
    List<Products> getProductByName(String name);
    List<Products> getProductsByNameAndBrand(String name,String brand);
    List<Products> getProductsByCategory(String category);
    List<Products> getProductsByCategoryAndBrand(String category,String brand);
    List<Products> getProductsByBrand(String brand);
    Products updateProduct(ProductUpdateRequest request, Long productId);
    void deleteProduct(Long productId);
    Long countProductsByNameAndBrand(String name,String brand);

}
