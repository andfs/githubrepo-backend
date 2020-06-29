package com.br.gitrepos.gitrepo.controller;

import com.br.gitrepos.gitrepo.service.SearchGithubService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchGithubService searchGithubService;

    @Test
    public void searchWithoutRepo() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/search").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void searchWithRepo() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/search?repo=zemo").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void searchByUserWithoutUser() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/search/user").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void searchByUser() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/search/user?username=andfs").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }
}