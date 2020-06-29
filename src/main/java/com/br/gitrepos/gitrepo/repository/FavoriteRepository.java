package com.br.gitrepos.gitrepo.repository;

import java.util.Optional;

import com.br.gitrepos.gitrepo.model.Favorites;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends CrudRepository<Favorites, Long> {
    
    Optional<Favorites> findByIdRepository(Long idRepository);
}