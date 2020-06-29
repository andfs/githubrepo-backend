package com.br.gitrepos.gitrepo.service;

import com.br.gitrepos.gitrepo.model.GitHubRepo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@NoArgsConstructor
public class SearchGithubService {

    private String urlTest;

    public SearchGithubService(String url) {
        this.urlTest = url;
    }
 
    @Value("${github.url}")
    private String githubUrl;

    @Value("${github.url.user}")
    private String githubUrlUser;
    
    public Mono<GitHubRepo> search(String repositoryName) {
        return performGithubSearch(githubUrl  + repositoryName);
    }

	public Mono<GitHubRepo> searchByUser(String username) {
        return performGithubSearch(githubUrlUser + username);
    }
    
    private Mono<GitHubRepo> performGithubSearch(String url) {
        if(githubUrl == null || githubUrlUser == null) {
            url = this.urlTest;
        }
        Mono<GitHubRepo> result = WebClient.create().get().uri(url).retrieve().bodyToMono(new ParameterizedTypeReference<GitHubRepo>() {}).onErrorReturn(new GitHubRepo());
        return result;
    }
}