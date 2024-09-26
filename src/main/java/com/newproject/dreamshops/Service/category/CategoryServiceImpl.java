package com.newproject.dreamshops.Service.category;


import com.amazonaws.services.kms.model.AlreadyExistsException;
import com.newproject.dreamshops.Entity.Category;
import com.newproject.dreamshops.Repository.CategoryRepository;
import com.newproject.dreamshops.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

 private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with the ID !"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        // Check if a category with the same name already exists
        if (categoryRepository.existsByName(category.getName())) {
            throw new AlreadyExistsException(category.getName() + " already exists");
        }
        // Save the new category
        return categoryRepository.save(category);
    }


    @Override
    public Category updateCategory(Category category, Long id) {
        // Fetch the existing category by ID
        Category oldCategory = getCategoryById(id);
        // Check if the category is found
        if (oldCategory == null) {
            throw new ResourceNotFoundException("Could not find category with ID " + id);
        }
        // Update the old category with new details
        oldCategory.setName(category.getName());
        // Save the updated category
        return categoryRepository.save(oldCategory);
    }


    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresent(categoryRepository::delete);
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with the ID !");
        }

    }
}
