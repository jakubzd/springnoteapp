package com.example.springproject.category;

import com.example.springproject.note.Note;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    public void saveCategory(@RequestBody  Category category) {
        categoryService.saveCategory(category);
    }

    @PutMapping("/categories/add/{noteId}")
    public void addNoteToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long noteId
    ) {
       Category category = categoryService.getCategory(categoryId);
    }

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

}
