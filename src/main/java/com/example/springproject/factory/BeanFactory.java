package com.example.springproject.factory;

import com.example.springproject.response.UserResponse;
import com.example.springproject.role.Role;
import com.example.springproject.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BeanFactory {

    public UserResponse userResponse(final User user) {
        final List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        return new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail(), roles);
    }

    public List<UserResponse> userResponses(final List<User> users) {
        return users.stream().map(this::userResponse).toList();
    }
}
