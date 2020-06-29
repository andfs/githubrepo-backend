package com.br.gitrepos.gitrepo.repository;

import java.util.Optional;

import com.br.gitrepos.gitrepo.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
}