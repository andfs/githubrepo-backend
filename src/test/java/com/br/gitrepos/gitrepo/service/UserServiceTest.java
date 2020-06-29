package com.br.gitrepos.gitrepo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Optional;

import com.br.gitrepos.gitrepo.exceptions.DuplicateUserException;
import com.br.gitrepos.gitrepo.exceptions.UserNotFoundException;
import com.br.gitrepos.gitrepo.model.User;
import com.br.gitrepos.gitrepo.repository.UserRepository;
import com.br.gitrepos.gitrepo.security.service.JwtService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User createUser() {
        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        return user;
    }

    @Test
    public void save() throws DuplicateUserException {
        User user = createUser();
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        when(userRepository.save(user)).thenReturn(user);
        userService.save(user);
        
        verify(passwordEncoder).encode(argument.capture());
        verify(jwtService).createToken(new HashMap<>(), "username");
        verify(userRepository).save(user);
        assertEquals("password", argument.getValue());
    }

    @Test
    public void whenDataIntegrityViolationExceptionMustThrowDuplicateUserException() throws DuplicateUserException {
        User user = new User();
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateUserException.class, () -> userService.save(user));
    }

    @Test
    public void login() throws UserNotFoundException {
        User user = createUser();
        Optional<User> userSaved = Optional.of(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(userSaved);
        when(passwordEncoder.matches(user.getPassword(), userSaved.get().getPassword())).thenReturn(true);
        userService.login(user);
        verify(jwtService).createToken(new HashMap<>(), "username");
    }

    @Test
    public void loginDontFindUsername() throws UserNotFoundException {
        User user = createUser();
        Optional<User> userSaved = Optional.ofNullable(null);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(userSaved);
        assertThrows(UserNotFoundException.class, () -> userService.login(user));
    }

    @Test
    public void loginPasswordDoesntMatch() throws UserNotFoundException {
        User user = createUser();
        User userDB = createUser();
        userDB.setPassword("wrong");
        Optional<User> userSaved = Optional.of(userDB);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(userSaved);
        assertThrows(UserNotFoundException.class, () -> userService.login(user));
    }
}