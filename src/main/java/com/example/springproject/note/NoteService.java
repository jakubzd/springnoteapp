package com.example.springproject.note;


import com.example.springproject.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public Note getNote(Long id) {
        return noteRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Long.toString(id)));
    }

    public void save(Note note) {
        noteRepository.save(note);
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

}
