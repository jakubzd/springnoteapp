package com.example.springproject.response;

import com.example.springproject.category.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteResponse {
    private String body;
    private Category category;
    private LocalDateTime dateOfPost;
}
