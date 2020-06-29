package com.br.gitrepos.gitrepo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.br.gitrepos.gitrepo.exceptions.FavoritesLimitException;
import com.br.gitrepos.gitrepo.model.Favorites;
import com.br.gitrepos.gitrepo.model.User;
import com.br.gitrepos.gitrepo.repository.FavoriteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritesService extends GithubRepoService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Transactional
    public void add(Long idRepository) throws FavoritesLimitException {
        User user = super.getUserFromRequest();
        if(user.getFavorites().size() + 1 > 5) {
            throw new FavoritesLimitException();
        }
        Favorites favorite = favoriteRepository.findByIdRepository(idRepository).orElse(new Favorites(idRepository));
        user.getFavorites().add(favorite);
    }

    @Transactional
	public void remove(Long idRepository) {
        User user = super.getUserFromRequest();
        Optional<Favorites> favorites = favoriteRepository.findByIdRepository(idRepository);
        favorites.ifPresent(f -> user.getFavorites().remove(f));
	}
}