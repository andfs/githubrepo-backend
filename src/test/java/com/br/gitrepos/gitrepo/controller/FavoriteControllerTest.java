package com.br.gitrepos.gitrepo.controller;

import static org.mockito.Mockito.when;

import java.util.HashMap;

import com.br.gitrepos.gitrepo.model.User;
import com.br.gitrepos.gitrepo.security.GithubRepoUserDetails;
import com.br.gitrepos.gitrepo.security.GithubRepoUserDetailsService;
import com.br.gitrepos.gitrepo.security.service.JwtService;
import com.br.gitrepos.gitrepo.service.FavoritesService;
import com.br.gitrepos.gitrepo.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class FavoriteControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @MockBean
    private GithubRepoUserDetailsService userDetailsService;

    @MockBean
    private UserService userService;

    @MockBean
    private FavoritesService favoriteService;

    private String mockAuthorization() {
        String token = jwtService.createToken(new HashMap<>(), "teste");
        User user = new User();
        user.setUsername("teste");
        when(userDetailsService.loadUserByUsername("teste")).thenReturn(new GithubRepoUserDetails(user));
        return token;
    }

    @Test
    public void favoriteWithoutLogin() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/favorite/add").accept(MediaType.TEXT_PLAIN);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void favoriteWithoutIdRepository() throws Exception {
        String token = mockAuthorization();
        RequestBuilder request = MockMvcRequestBuilders.get("/favorite/add").header("Authorization", "Bearer "+ token).accept(MediaType.TEXT_PLAIN);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void favoriteWithIdRepository() throws Exception {
        String token = mockAuthorization();
        RequestBuilder request = MockMvcRequestBuilders.get("/favorite/add?id=5").header("Authorization", "Bearer "+ token).accept(MediaType.TEXT_PLAIN);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void removeFavoriteWithouLogin() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/favorite/remove").accept(MediaType.TEXT_PLAIN);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void removeFavoriteWithoutIdRepository() throws Exception {
        String token = mockAuthorization();
        RequestBuilder request = MockMvcRequestBuilders.delete("/favorite/remove").header("Authorization", "Bearer "+ token).accept(MediaType.TEXT_PLAIN);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void removeFavoriteWithIdRepository() throws Exception {
        String token = mockAuthorization();
        RequestBuilder request = MockMvcRequestBuilders.delete("/favorite/remove?id=5").header("Authorization", "Bearer "+ token).accept(MediaType.TEXT_PLAIN);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }
}