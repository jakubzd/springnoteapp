package com.example.springproject.note;

import com.example.springproject.category.Category;
import com.example.springproject.category.CategoryService;
import com.example.springproject.user.User;
import com.example.springproject.user.UserRepository;
import com.example.springproject.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping(path="/notes")
public class NoteController {


    private final NoteService noteService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public NoteController(NoteService noteService, CategoryService categoryService, UserService userService) {
        this.noteService = noteService;
        this.categoryService = categoryService;
        this.userService = userService;

    }
    public User getCurrentUser() {
        return userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping
    public String getNotes(Model model) {
        User currentUser = getCurrentUser();
        List<Note> notes = currentUser.getNotes();
        model.addAttribute("name", currentUser.getFirstName());
        model.addAttribute("notes", notes);
        return "note/notes";
    }

    @GetMapping("/{noteId}")
    public String getNoteDetails(@PathVariable Long noteId, Model model) {
        Note note = noteService.getNote(noteId);
        model.addAttribute("note", note);

        return "note/note_details";
    }

    @GetMapping("/add")
    public String addNote(Model model) {
        Note note = new Note();

        model.addAttribute("note", note);
        model.addAttribute("categories", categoryService.getCategories());

        return "note/add_note";
    }

    @PostMapping("/add")
    public String assignNoteToCategory(Note note) {
        User currentUser = getCurrentUser();
        Category category = categoryService.getCategoryByCategoryName(note.getCategoryName());

        note.setCategory(category);
        note.setDateOfPost(LocalDateTime.now());
        note.setUser(currentUser);

        categoryService.addNoteToCategory(note.getCategory(), note);
        userService.addNoteToUser(currentUser, note);
        noteService.save(note);
        return "redirect:/notes";
    }

    @GetMapping("/edit/{noteId}")
    public String showEditForm(@PathVariable Long noteId, Model model) {
        model.addAttribute("editedNote", noteService.getNote(noteId));
        model.addAttribute("categories", categoryService.getCategories());

        return "note/edit_note";
    }

    @PostMapping("/edit/{noteId}")
    public String editNote(@PathVariable Long noteId, Note note) throws Exception {
        Note editedNote = noteService.getNote(noteId);
        Category category = categoryService.getCategoryByCategoryName(note.getCategoryName());

        boolean diffCategories = (editedNote.getCategory() == note.getCategory());
        boolean diffBodies = (editedNote.getBody().equals(note.getBody()));

        if (diffCategories && diffBodies) {
            throw new Exception("Nothing was edited!");
        }
        editedNote.setCategory(category);
        editedNote.setBody(note.getBody());

        noteService.save(editedNote);
        return "redirect:/notes";
    }


    @GetMapping("delete/{noteId}")
    public String deleteNote(@PathVariable Long noteId) {
        categoryService.removeNoteFromCategory(noteService.getNote(noteId));
        noteService.delete(noteId);

        return "redirect:/notes";
    }
}
