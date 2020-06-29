package com.br.gitrepos.gitrepo.controller;

import java.net.HttpURLConnection;

import javax.validation.Valid;

import com.br.gitrepos.gitrepo.exceptions.DuplicateUserException;
import com.br.gitrepos.gitrepo.exceptions.UserNotFoundException;
import com.br.gitrepos.gitrepo.model.User;
import com.br.gitrepos.gitrepo.model.dto.UserDTO;
import com.br.gitrepos.gitrepo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/auth")
@Api(description = "Endpoints para cadastrar e efetuar login.")
public class AuthController {

    @Autowired
    private UserService userService;

    @ApiOperation(produces = "String", value = "Cria um usuário e retorna um token baseado no username.")
    @ApiResponses(value = {@ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Conflict. Já existe um usuário com este usarname")})
    @PostMapping(value = "/signup", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String signup(@Valid @RequestBody UserDTO user) throws DuplicateUserException {
        return userService.save(new User(user));
    }

    @ApiOperation(produces = "String", value = "Verifica se o username e password estão corretos e em caso positivo retorna um token baseado no username.")
    @ApiResponses(value = {@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Usuário ou senha incorretos.")})
    @PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String login(@Valid @RequestBody UserDTO user) throws UserNotFoundException {
        return userService.login(new User(user));
    }
}