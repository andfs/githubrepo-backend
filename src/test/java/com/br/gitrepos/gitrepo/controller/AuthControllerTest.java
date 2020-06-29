package com.br.gitrepos.gitrepo.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.br.gitrepos.gitrepo.exceptions.DuplicateUserException;
import com.br.gitrepos.gitrepo.exceptions.UserNotFoundException;
import com.br.gitrepos.gitrepo.model.User;
import com.br.gitrepos.gitrepo.model.dto.UserDTO;
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
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private UserService userService;

    private String createJsonString(UserDTO userDTO) throws JsonProcessingException {
        String userDTOString = new ObjectMapper().writeValueAsString(userDTO);

        return userDTOString;
    }

    @Test
    public void loginWithoutUsername() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        String userDTOString = createJsonString(userDTO);

        RequestBuilder request = MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).content(userDTOString);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void loginWithoutPassword() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        String userDTOString = createJsonString(userDTO);

        RequestBuilder request = MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).content(userDTOString);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void loginWithPasswordLengthLessThan6() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("pass");
        String userDTOString = createJsonString(userDTO);

        RequestBuilder request = MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).content(userDTOString);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void loginWithoUnregisteredUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        String userDTOString = createJsonString(userDTO);

        when(userService.login(new User(userDTO))).thenThrow(UserNotFoundException.class);

        RequestBuilder request = MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).content(userDTOString);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void loginWithUserAndPassword() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        String userDTOString = createJsonString(userDTO);

        RequestBuilder request = MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).content(userDTOString);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void signupWithoutUsername() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        String userDTOString = createJsonString(userDTO);

        RequestBuilder request = MockMvcRequestBuilders.post("/auth/signup").contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).content(userDTOString);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void signupWithoutPassword() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        String userDTOString = createJsonString(userDTO);

        RequestBuilder request = MockMvcRequestBuilders.post("/auth/signup").contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).content(userDTOString);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void signupWithPasswordLengthLessThan6() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("pass");
        String userDTOString = createJsonString(userDTO);

        RequestBuilder request = MockMvcRequestBuilders.post("/auth/signup").contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).content(userDTOString);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void signupWithUnRegisteredUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        String userDTOString = createJsonString(userDTO);

        RequestBuilder request = MockMvcRequestBuilders.post("/auth/signup").contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).content(userDTOString);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void signupWithRegisteredUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        String userDTOString = createJsonString(userDTO);

        when(userService.save(new User(userDTO))).thenThrow(DuplicateUserException.class);

        RequestBuilder request = MockMvcRequestBuilders.post("/auth/signup").contentType(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).content(userDTOString);
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
}