package com.giftGreenEcom.Service;

import com.giftGreenEcom.Entity.Category;
import com.giftGreenEcom.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getCategoriesByType(String type) {
        return categoryRepository.findByCategoryType(type);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (updatedCategory.getName() != null && !updatedCategory.getName().isBlank()) {
            existing.setName(updatedCategory.getName());
        }

        if (updatedCategory.getCategoryType() != null && !updatedCategory.getCategoryType().isBlank()) {
            existing.setCategoryType(updatedCategory.getCategoryType());
        }

        return categoryRepository.save(existing);
    }

}
