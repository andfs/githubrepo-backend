package com.br.gitrepos.gitrepo.service;

import java.util.HashMap;
import java.util.Optional;

import com.br.gitrepos.gitrepo.exceptions.DuplicateUserException;
import com.br.gitrepos.gitrepo.exceptions.UserNotFoundException;
import com.br.gitrepos.gitrepo.model.User;
import com.br.gitrepos.gitrepo.repository.UserRepository;
import com.br.gitrepos.gitrepo.security.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String save(User user) throws DuplicateUserException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userRepository.save(user);
        } catch(DataIntegrityViolationException e) {
            throw new DuplicateUserException();
        }
        return jwtService.createToken(new HashMap<>(), user.getUsername());
    }

    public String login(User user) throws UserNotFoundException {
        Optional<User> userSaved = userRepository.findByUsername(user.getUsername());
        userSaved.orElseThrow(() -> new UserNotFoundException());
        if(!passwordEncoder.matches(user.getPassword(), userSaved.get().getPassword())) {
            throw new UserNotFoundException();
        }
        return jwtService.createToken(new HashMap<>(), user.getUsername());
    }
}