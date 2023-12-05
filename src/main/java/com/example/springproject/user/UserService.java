package com.example.springproject.user;

import com.example.springproject.dto.UserRegistrationDto;
import com.example.springproject.note.Note;
import com.example.springproject.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    void save(UserRegistrationDto userRegistrationDto);
    User getUserById(Long id);
    User getUserByEmail(String email);
    void addNoteToUser(User user, Note note);
    List<UserResponse> getAll();
}
