package com.br.gitrepos.gitrepo.security;

import java.util.Optional;

import com.br.gitrepos.gitrepo.model.User;
import com.br.gitrepos.gitrepo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GithubRepoUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		GithubRepoUserDetails details = null;
		Optional<User> user = userRepository.findByUsername(username);
		details = user.map(u -> new GithubRepoUserDetails(u)).orElse(null);
		return details;
	}
}