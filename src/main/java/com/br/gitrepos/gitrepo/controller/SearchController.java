package com.br.gitrepos.gitrepo.controller;

import java.net.HttpURLConnection;

import com.br.gitrepos.gitrepo.model.GitHubRepo;
import com.br.gitrepos.gitrepo.service.SearchGithubService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/search")
@Api(description = "Endpoints para pesquisar repositórios no github.")
public class SearchController {

    @Autowired
    private SearchGithubService searchGithubService;
    
    @ApiOperation(value = "Busca repositórios. Deve ser passado o nome do repositório que se deseja procurar como query param.", response = GitHubRepo.class)
    @ApiResponses(value = {@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Repo não informado")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GitHubRepo> search(@RequestParam(name = "repo") @NonNull String repo) {
        return searchGithubService.search(repo);
    }

    @ApiOperation(value = "Busca repositórios por usuário. Deve ser passado o nome do usuário que se deseja procurar como query param.", response = GitHubRepo.class)
    @ApiResponses(value = {@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Usarname não informado")})
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GitHubRepo> searchByUser(@RequestParam(name = "username") @NonNull String username) {
        return searchGithubService.searchByUser(username);
    }
}