package com.saani.shopify.services.product;

import com.saani.shopify.exceptions.CategoryNotFoundException;
import com.saani.shopify.exceptions.ProductNotFoundException;
import com.saani.shopify.models.Category;
import com.saani.shopify.models.Products;
import com.saani.shopify.repository.CategoryRepository;
import com.saani.shopify.repository.ProductRepository;
import com.saani.shopify.requests.ProductRequests.AddProductRequest;
import com.saani.shopify.requests.ProductRequests.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInterface{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Products addProduct(AddProductRequest request) {
        Category category = categoryRepository.findByName(request.getCategory().getName())
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

        request.setCategory(category);
        return productRepository.save(createProduct(request,category));

    }

    private Products createProduct(AddProductRequest request, Category category){
        return new Products(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getQuantity(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Products getProductById(Long Id) {
        return productRepository.findById(Id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Products> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Products> getProductsByNameAndBrand(String name, String brand) {
        return productRepository.findByNameAndBrand(name,brand);
    }

    @Override
    public List<Products> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Products> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Products> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public Products updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist."));

    }

    private Products updateExistingProduct(Products existingProduct,ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName())
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist"));
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(productRepository::delete,
                        () -> {throw new ProductNotFoundException("Product not found");});

    }

    @Override
    public Long countProductsByNameAndBrand(String name, String brand) {
        return productRepository.countByNameAndBrand(name,brand);
    }
}
