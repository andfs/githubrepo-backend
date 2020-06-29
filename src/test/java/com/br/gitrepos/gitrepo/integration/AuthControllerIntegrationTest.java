package com.br.gitrepos.gitrepo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.br.gitrepos.gitrepo.model.dto.UserDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void sigunUpReturnsTokenSignupSameUserReturnsConflict() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setUsername("username");
        String response = testRestTemplate.postForObject("/auth/signup", userDTO, String.class);
        assertNotNull(response);
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity("/auth/signup", userDTO, String.class);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    @Test
    public void loginRegisteredUserReturnsTokenUnregisteredReturnsBadRequest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("signupuser");
        userDTO.setUsername("sigunupuserpassword");
        String response = testRestTemplate.postForObject("/auth/signup", userDTO, String.class);
        assertNotNull(response);
        String responseLogin = testRestTemplate.postForObject("/auth/login", userDTO, String.class);
        assertEquals(response, responseLogin);

        UserDTO wrongUuserDTO = new UserDTO();
        wrongUuserDTO.setPassword("passwordWrong");
        wrongUuserDTO.setUsername("usernameWrong");
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity("/auth/login", wrongUuserDTO, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}