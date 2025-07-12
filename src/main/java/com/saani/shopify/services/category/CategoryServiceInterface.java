package com.saani.shopify.services.category;

import com.saani.shopify.models.Category;
import com.saani.shopify.requests.CategoryRequests.AddCategoryRequest;
import com.saani.shopify.requests.CategoryRequests.UpdateCategoryRequest;

import java.util.List;

public interface CategoryServiceInterface {
    Category getCategoryById(Long Id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addNewCategory(AddCategoryRequest request);
    Category updateCategory(UpdateCategoryRequest request,Long id);
    void deleteCategory(Long Id);
}
