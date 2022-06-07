package com.example.springproject.category;

import com.example.springproject.note.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


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

    public void addNoteToCategory(Category category, Note note) {
        category.addNote(note);
    }

    public Category getCategoryByCategoryName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public void removeNoteFromCategory(Note note) {
        Optional<Category> category = categoryRepository.findById(note.getCategory().getId());
        if(category.isEmpty()) {
            throw new EntityNotFoundException(note.getCategoryName());
        }
        category.get().removeNote(note);
    }


}
