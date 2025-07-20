package com.saani.shopify.controllers;

import com.saani.shopify.exceptions.ResourceExistsException;
import com.saani.shopify.exceptions.ResourceNotFoundException;
import com.saani.shopify.models.Products;
import com.saani.shopify.requests.ProductRequests.AddProductRequest;
import com.saani.shopify.requests.ProductRequests.ProductUpdateRequest;
import com.saani.shopify.response.ApiResponse;
import com.saani.shopify.services.product.ProductServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductServiceInterface productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        try {
            List<Products> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("All Products Retrieved Successfully",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Products Retrieval Failed",e.getMessage()));
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try {
            Products product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product Retrieved Successfully",product));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNewProduct(@RequestBody AddProductRequest newProduct){
        try {
            Products product = productService.addProduct(newProduct);
            return ResponseEntity.ok(new ApiResponse("Product Added Successfully",product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/product/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody  ProductUpdateRequest updatedProduct,@PathVariable Long productId){
        try {
            Products newUpdatedProduct = productService.updateProduct(updatedProduct,productId);
            return ResponseEntity.ok(new ApiResponse("Product Update Success",newUpdatedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/product/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Product Delete Success",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Product Deletion Failed",null));
        }
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Products> products = productService.getProductByName(name);
            return ResponseEntity.ok(new ApiResponse("Products Retrieved Success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Products Retrieval Failed",null));
        }
    }

    @GetMapping("/product/by-name-and-brand")
    public ResponseEntity<ApiResponse> getProductByNameAndBrand(@RequestParam String name,@RequestParam String brand){
        try {
            List<Products> products = productService.getProductsByNameAndBrand(name,brand);

            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products not found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Products Retrieved Success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Products Retrieval Failed",null));
        }
    }

    @GetMapping("product/{category}")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){
        try {
            List<Products> products = productService.getProductsByCategory(category);

            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products not found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Products Retrieved Success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Products Retrieval Failed",null));
        }
    }

    @GetMapping("/product/by-category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brand){
        try {
            List<Products> products = productService.getProductsByCategoryAndBrand(category,brand);

            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products not found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Products Retrieved Success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Products Retrieval Failed",null));
        }
    }

    @GetMapping("/product/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand){
        try {
            List<Products> products = productService.getProductsByBrand(brand);

            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products not found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Products Retrieved Success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Products Retrieval Failed",null));
        }
    }

    @GetMapping("/count/by-name-and-brand")
    public ResponseEntity<ApiResponse> countProductsByNameAndBrand(@RequestParam String name,@RequestParam String brand){
        try {
            Long productCount = productService.countProductsByNameAndBrand(name,brand);
            return ResponseEntity.ok(new ApiResponse("Count Retrieval Success",productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Count Retrieval Failed",null));
        }
    }
}
