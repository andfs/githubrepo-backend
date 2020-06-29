package com.br.gitrepos.gitrepo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import com.br.gitrepos.gitrepo.model.dto.UserDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FavoriteControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

    @Test
    public void favoriteDesfavoriteAccessDenied() throws URISyntaxException {
        ResponseEntity<Void> addResponseEntity = testRestTemplate.postForEntity("/favorite/add", 1l, Void.class);
        assertEquals(HttpStatus.FORBIDDEN, addResponseEntity.getStatusCode());

        RequestEntity<Void> request = RequestEntity.delete(new URI("http:localhost:" + port + "/favorite/remove?id=1")).build();
		ResponseEntity<Void> removeResponseEntity = testRestTemplate.exchange(request, Void.class);
        assertEquals(HttpStatus.FORBIDDEN, removeResponseEntity.getStatusCode());
    }

    @Test
    public void favoriteDesfavoriteSuccess() throws URISyntaxException {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("favoritepassword");
        userDTO.setUsername("usernamepassword");
        String token = testRestTemplate.postForObject("/auth/signup", userDTO, String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ token);
        headers.set("Accept", "*/*");

		RequestEntity<Void> request = RequestEntity.get(new URI("http:localhost:" + port + "/favorite/add?id=1")).headers(headers).build();
		ResponseEntity<Void> addResponseEntity = testRestTemplate.exchange(request, Void.class);
        assertEquals(HttpStatus.OK, addResponseEntity.getStatusCode());

        RequestEntity<Void> requestDelete = RequestEntity.delete(new URI("http:localhost:" + port + "/favorite/remove?id=1")).headers(headers).build();
        ResponseEntity<Void> removeResponseEntity = testRestTemplate.exchange(requestDelete, Void.class);
        assertEquals(HttpStatus.OK, removeResponseEntity.getStatusCode());
    }

    private ResponseEntity<Void> favoriteUntil(int limit, HttpHeaders headers) throws URISyntaxException {
        ResponseEntity<Void> lastResponse = null;

        for(int i = 0; i < limit; i++) {
            RequestEntity<Void> request = RequestEntity.get(new URI("http:localhost:" + port + "/favorite/add?id=" + i)).headers(headers).build();
		    lastResponse = testRestTemplate.exchange(request, Void.class);
        }
        return lastResponse;
    }

    @Test
    public void favoriteExceedLimit() throws URISyntaxException {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setUsername("username");
        String token = testRestTemplate.postForObject("/auth/signup", userDTO, String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ token);
        headers.set("Accept", "*/*");

		ResponseEntity<Void> responseEntity = favoriteUntil(6, headers);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}