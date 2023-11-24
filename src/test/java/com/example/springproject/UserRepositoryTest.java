package com.example.springproject;

import com.example.springproject.role.Role;
import com.example.springproject.user.User;
import com.example.springproject.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenSavingUser_thenUserHasId() {
        final User user = new User("John", "Doe", "john@example.com", "password", Set.of(new Role("USER")));
        userRepository.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void givenTwoSavedUsers_whenFindingAll_thenShouldReturnListOfTwo() {
        final User user1 = new User("John", "Doe", "john@example.com", "password", Collections.emptySet());
        final User user2 = new User("Jane", "Doe", "jane@example.com", "password", Collections.emptySet());

        userRepository.saveAll(List.of(user1, user2));

        final List<User> allUsers = userRepository.findAll();

        assertTrue(allUsers.containsAll(List.of(user1, user2)));
        assertEquals(2, allUsers.size());
    }

    @Test
    public void givenSavedUser_whenGettingById_thenShouldReturnTheSameUser() {
        final User user = new User("John", "Doe", "john@example.com", "password", Set.of(new Role("USER")));

        userRepository.save(user);
        long id = user.getId();

        final User fetchedUser = userRepository.getById(id);

        assertEquals(fetchedUser, user);
    }

    @Test
    public void givenSavedUser_whenGettingByEmail_thenShouldReturnTheSameUser() {
        final User user = new User("John", "Doe", "john@example.com", "password", Set.of(new Role("USER")));

        userRepository.save(user);

        final User fetchedUser = userRepository.findByEmail("john@example.com");

        assertEquals(fetchedUser, user);
    }
}
