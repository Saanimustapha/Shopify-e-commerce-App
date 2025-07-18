package com.saani.shopify.controllers;

import com.saani.shopify.exceptions.ResourceExistsException;
import com.saani.shopify.exceptions.ResourceNotFoundException;
import com.saani.shopify.models.Category;
import com.saani.shopify.requests.CategoryRequests.AddCategoryRequest;
import com.saani.shopify.response.ApiResponse;
import com.saani.shopify.services.category.CategoryServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryServiceInterface categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();

            return ResponseEntity.ok(new ApiResponse("Get Categories Success",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Get Categories Failed",INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/category/${categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Category Found",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Category not found",e.getMessage()));
        }
    }

    @GetMapping("/category/${categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName){

        try {
            Category category = categoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new ApiResponse("Category Found",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Category Not Found",NOT_FOUND));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody AddCategoryRequest newCategory){

        try {
            Category category = categoryService.addNewCategory(newCategory);
            return ResponseEntity.ok(new ApiResponse("Category Added Successfully",category));
        } catch (ResourceExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Failed Adding Category",CONFLICT));
        }
    }

    @DeleteMapping("/category/${categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(new ApiResponse("Category Deleted",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Failed Category Deletion",null));
        }
    }





}
