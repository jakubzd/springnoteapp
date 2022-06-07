package com.example.springproject.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategories(Model model) {
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "category/categories";
    }

    @GetMapping("/{categoryId}")
    public String getNoteDetails(@PathVariable Long categoryId, Model model) {
        Category category = categoryService.getCategory(categoryId);
        model.addAttribute("note", category);
        model.addAttribute("categoryNotes", category.getNotes());
        return "category/category_notes";
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category",category);
        return "category/add_category";
    }

    @PostMapping("/add")
    public String saveCategory(Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

}
