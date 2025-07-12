package com.saani.shopify.services.category;

import com.saani.shopify.exceptions.ResourceNotFoundException;
import com.saani.shopify.exceptions.ResourceExistsException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Category does not exist."));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category does not exist."));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addNewCategory(AddCategoryRequest request) {
        categoryRepository.findByNameIgnoreCase(request.getName())
                .ifPresent(existing -> {
                    throw new ResourceExistsException("The category '" + request.getName() + "' already exists.");
                });

        return categoryRepository.save(createNewCategory(request));
    }

    private Category createNewCategory(AddCategoryRequest request) {
        return new Category(request.getName().trim());
    }


    @Override
    public Category updateCategory(UpdateCategoryRequest request, Long id) {
        return categoryRepository.findById(id)
                .map(existingCategory -> updateExistingCategory(request,existingCategory))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }


    private Category updateExistingCategory(UpdateCategoryRequest request,Category existingCategory){
        existingCategory.setName(request.getName());

        return existingCategory;
    }

    @Override
    public void deleteCategory(Long Id) {
        categoryRepository.findById(Id)
                .ifPresentOrElse(categoryRepository :: delete,
                        () -> {throw new ResourceNotFoundException("Category does not exist.");});

    }
}
