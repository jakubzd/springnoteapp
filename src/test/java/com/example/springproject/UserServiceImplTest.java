package com.example.springproject;

import com.example.springproject.dto.UserRegistrationDto;
import com.example.springproject.note.Note;
import com.example.springproject.role.Role;
import com.example.springproject.role.RoleRepository;
import com.example.springproject.user.User;
import com.example.springproject.user.UserRepository;
import com.example.springproject.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void whenSavingUser_thenCorrectMethodsAreCalledTest() {
        final UserRegistrationDto dto = new UserRegistrationDto(
                "John", "Doe", "john@example.com", "password");
        final Role userRole = new Role("USER");

        when(roleRepository.findRoleByName("USER")).thenReturn(userRole);
        when(encoder.encode(dto.getPassword())).thenReturn("hashedPassword");

        userService.save(dto);

        verify(userRepository, times(1)).save(argThat(user ->
                user.getFirstName().equals(dto.getFirstName()) &&
                        user.getLastName().equals(dto.getLastName()) &&
                        user.getEmail().equals(dto.getEmail()) &&
                        user.getPassword().equals("hashedPassword") &&
                        user.getRoles().contains(userRole)
        ));
        verify(encoder, times(1)).encode(dto.getPassword());
    }

    @Test
    public void givenWrongId_whenGettingUserById_thenThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
        assertEquals("User with id: 1 not found.", exception.getMessage());
    }

    @Test
    public void givenExistingId_whenGettingUserById_thenReturnTheUser() {
        final User user = new User("John", "Doe", "john@example.com", "password", Set.of(new Role("USER")));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        final User fetchedUser = userService.getUserById(1L);
        assertEquals(user, fetchedUser);
    }

    @Test
    public void givenWrongEmail_whenGettingUserByEmail_thenThrowException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        Throwable exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.getUserByEmail("john@example.com"));
        assertEquals("User with email: john@example.com not found.", exception.getMessage());
    }

    @Test
    public void givenExistingEmail_whenGettingUserByEmail_thenReturnTheUser() {
        final User user = new User("John", "Doe", "john@example.com", "password", Set.of(new Role("USER")));
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        final User fetchedUser = userService.getUserByEmail("john@example.com");
        assertEquals(user, fetchedUser);
    }

    @Test
    public void givenUserWithoutNotes_whenAddingNote_thenSizeShouldChange() {
        final User user = new User("John", "Doe", "john@example.com", "password", Set.of(new Role("USER")));
        assertEquals(0, user.getNotes().size());
        final Note note = new Note();
        userService.addNoteToUser(user, note);
        assertEquals(1, user.getNotes().size());
    }

    @Test
    public void givenInvalidUsername_whenGettingUserDetailsByUsername_thenThrowException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        Throwable exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername("john@example.com"));
        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    public void givenExistingUsername_whenGettingUserDetailsByUsername_thenReturnTheUserDetails() {
        final User user = new User("John", "Doe", "john@example.com", "password", Set.of(new Role("USER")));
        final org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.
                        User("john@example.com", "password", List.of(new SimpleGrantedAuthority("USER")));
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        final org.springframework.security.core.userdetails.UserDetails fetchedUserDetails = userService.loadUserByUsername("john@example.com");
        assertEquals(userDetails, fetchedUserDetails);
    }
}
