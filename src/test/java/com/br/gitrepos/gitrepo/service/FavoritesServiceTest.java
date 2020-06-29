package com.br.gitrepos.gitrepo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.br.gitrepos.gitrepo.exceptions.FavoritesLimitException;
import com.br.gitrepos.gitrepo.model.Favorites;
import com.br.gitrepos.gitrepo.model.User;
import com.br.gitrepos.gitrepo.repository.FavoriteRepository;
import com.br.gitrepos.gitrepo.repository.UserRepository;
import com.br.gitrepos.gitrepo.security.GithubRepoUserDetails;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class FavoritesServiceTest {

    @InjectMocks
    private FavoritesService favoritesService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    private User createUser() {
        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        return user;
    }

    public User mockGithubRepoService() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        User user = createUser();
        GithubRepoUserDetails principal = new GithubRepoUserDetails(user);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(userRepository.findByUsername(principal.getUsername())).thenReturn(Optional.of(user));

        return user;
    }

    @Test
    public void add() throws FavoritesLimitException {
        User user = mockGithubRepoService();
        favoritesService.add(1l);
        assertEquals(1, user.getFavorites().size());
    }

    @Test
    public void addMoreThan5Favorites() throws FavoritesLimitException {
        User user = mockGithubRepoService();
        user.getFavorites().add(new Favorites(1l));
        user.getFavorites().add(new Favorites(2l));
        user.getFavorites().add(new Favorites(3l));
        user.getFavorites().add(new Favorites(4l));
        user.getFavorites().add(new Favorites(5l));
        assertThrows(FavoritesLimitException.class, () -> favoritesService.add(6l));
    }

    @Test
    public void addNonExistingFavorite() throws FavoritesLimitException {
        mockGithubRepoService();
        Long idRepository = 1l;
        when(favoriteRepository.findByIdRepository(idRepository)).thenReturn(Optional.ofNullable(null));
        favoritesService.add(idRepository);
        assertEquals(new Favorites(idRepository), favoriteRepository.findByIdRepository(idRepository).orElse(new Favorites(idRepository)));
    }

    @Test
    public void addExistingFavorite() throws FavoritesLimitException {
        mockGithubRepoService();
        Long idRepository = 1l;
        Favorites favorites = new Favorites(idRepository);
        favorites.setId(1l);
        when(favoriteRepository.findByIdRepository(idRepository)).thenReturn(Optional.of(favorites));
        favoritesService.add(idRepository);
        assertEquals(favorites, favoriteRepository.findByIdRepository(idRepository).orElse(new Favorites(idRepository)));
    }

    @Test
    public void removeFromZero() throws FavoritesLimitException {
        User user = mockGithubRepoService();
        favoritesService.remove(1l);
        assertEquals(0, user.getFavorites().size());
    }

    @Test
    public void remove() throws FavoritesLimitException {
        User user = mockGithubRepoService();
        Long idRepository = 1l;
        Favorites favorites = new Favorites(idRepository);
        user.getFavorites().add(favorites);
        when(favoriteRepository.findByIdRepository(idRepository)).thenReturn(Optional.of(favorites));
        favoritesService.remove(1l);
        assertEquals(0, user.getFavorites().size());
    }

    @Test
    public void removeNonExistingId() throws FavoritesLimitException {
        User user = mockGithubRepoService();
        Long idRepository = 1l;
        Favorites favorites = new Favorites(idRepository);
        user.getFavorites().add(favorites);
        when(favoriteRepository.findByIdRepository(idRepository)).thenReturn(Optional.of(favorites));
        favoritesService.remove(2l);
        assertEquals(1, user.getFavorites().size());
    }
}