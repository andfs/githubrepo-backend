package com.br.gitrepos.gitrepo.service;

import com.br.gitrepos.gitrepo.model.User;
import com.br.gitrepos.gitrepo.repository.UserRepository;
import com.br.gitrepos.gitrepo.security.GithubRepoUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GithubRepoService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User getUserFromRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GithubRepoUserDetails principal = (GithubRepoUserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow();

        return user;
    }
}