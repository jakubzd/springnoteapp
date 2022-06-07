package com.example.springproject.note;

import com.example.springproject.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    /*
     * TODO: getNote Error handling when no such categoryId.
     */
    public Note getNote(Long noteId) {
        return noteRepository.findById(noteId).get();
    }

    public void saveNote(Note note) {
        noteRepository.save(note);
        categoryRepository.findById(note.getCategory().getId());

    }

}
