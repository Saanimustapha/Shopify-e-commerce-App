package com.saani.shopify.services.category;

import com.saani.shopify.exceptions.CategoryNotFoundException;
import com.saani.shopify.exceptions.CategoryExistsException;
import com.saani.shopify.models.Category;
import com.saani.shopify.repository.CategoryRepository;
import com.saani.shopify.requests.CategoryRequests.AddCategoryRequest;
import com.saani.shopify.requests.CategoryRequests.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceInterface{

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long Id) {
        return categoryRepository.findById(Id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist."));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist."));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addNewCategory(AddCategoryRequest request) {

        Optional<Category> category = categoryRepository.findByName(request.getName());

        if (category.isPresent()){
            throw new CategoryExistsException("The category '" + request.getName() + "' already exists.");
        }

        return categoryRepository.save(createNewCategory(request));
    }

    private Category createNewCategory(AddCategoryRequest request){
        return new Category(request.getName());
    }

    @Override
    public Category updateCategory(UpdateCategoryRequest request) {
        return categoryRepository.findByName(request.getName())
                .map(existingCategory -> updateExistingCategory(request,existingCategory))
                .map(categoryRepository :: save)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist."));

    }

    private Category updateExistingCategory(UpdateCategoryRequest request,Category existingCategory){
        existingCategory.setName(request.getName());

        return existingCategory;
    }

    @Override
    public void deleteCategory(Long Id) {
        categoryRepository.findById(Id)
                .ifPresentOrElse(categoryRepository :: delete,
                        () -> {throw new CategoryNotFoundException("Category does not exist.");});

    }
}
