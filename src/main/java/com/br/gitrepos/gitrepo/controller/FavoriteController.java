package com.br.gitrepos.gitrepo.controller;

import java.net.HttpURLConnection;

import com.br.gitrepos.gitrepo.exceptions.FavoritesLimitException;
import com.br.gitrepos.gitrepo.service.FavoritesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(value = "/favorite")
@Api(description = "Endpoints para favoritar e desfavoritar um repositório.")
public class FavoriteController {
    
    @Autowired
    private FavoritesService favoriteService;

    @ApiOperation(value = "Favorita o repositório para o usuário logado.", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Limite de favoritos excedido.")})
    @GetMapping(value = "/add", produces = MediaType.TEXT_PLAIN_VALUE)
    public void add(@RequestParam(name = "id") @NonNull Long idRepository) throws FavoritesLimitException {
        favoriteService.add(idRepository);
    }

    @ApiOperation(value = "Desfavorita o repositório para o usuário logado.", authorizations = { @Authorization(value="apiKey") })
    @DeleteMapping(value = "/remove", produces = MediaType.TEXT_PLAIN_VALUE)
    public void remove(@RequestParam(name = "id") @NonNull Long idRepository) {
        favoriteService.remove(idRepository);
    }
}