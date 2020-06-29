package com.br.gitrepos.gitrepo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GithubRepoExceptionHandler {
 
    @ExceptionHandler(value = DuplicateUserException.class)
    protected ResponseEntity<Object> handleConflictUser(DuplicateUserException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com este username.");
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário ou senha inválidos.");
    }

    @ExceptionHandler(value = FavoritesLimitException.class)
    protected ResponseEntity<Object> handleFavoritesLimit(FavoritesLimitException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Limite de favoritos excedido.");
    }
}