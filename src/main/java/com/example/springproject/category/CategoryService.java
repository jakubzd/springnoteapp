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

    public Category getCategoryById(final long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow( () ->
                new EntityNotFoundException("Entity with id: " + categoryId + " not found."));
    }

    public void addNoteToCategory(final Category category, final Note note) {
        category.addNote(note);
    }

    public Category getCategoryByCategoryName(final String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public void removeNoteFromCategory(final Note note) {
        final Category category = getCategoryById(note.getCategory().getId());
        category.removeNote(note);
    }


}
