package com.example.springproject.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    /**
    TO DO
     Error handling when no such categoryId.
     **/
    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).get();
    }
    public void addNoteToCategory() {
    }
}
