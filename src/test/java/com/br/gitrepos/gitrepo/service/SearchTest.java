package com.br.gitrepos.gitrepo.service;

import java.io.IOException;
import java.util.List;

import com.br.gitrepos.gitrepo.model.GitHubRepo;
import com.br.gitrepos.gitrepo.model.Repo;
import com.br.gitrepos.gitrepo.model.RepoOwner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(SpringExtension.class)
public class SearchTest {
    
    @Value("${github.url}")
    private String githubUrl;

    @Value("${github.url.user}")
    private String githubUrlUser;

    public static MockWebServer mockBackEnd;
 
    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }
 
    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    public void search() throws JsonProcessingException {
        String repositoryName = "repositoryName";

        GitHubRepo githubRepo = new GitHubRepo();
        githubRepo.setIncompleteResults(false);
        githubRepo.setTotalCount(1l);
        Repo repo = new Repo();
        repo.setName(repositoryName);
        RepoOwner owner = new RepoOwner();
        owner.setLogin("login");
        repo.setOwner(owner);
        githubRepo.setRepositories(List.of(repo));

        mockBackEnd.enqueue(new MockResponse().setBody(new ObjectMapper().writeValueAsString(githubRepo)).addHeader("Content-Type", "application/json"));

        SearchGithubService searchGithubService = new SearchGithubService(String.format("http://localhost:%s", mockBackEnd.getPort()));

        Mono<GitHubRepo> searchResult = searchGithubService.search(repositoryName);

        StepVerifier.create(searchResult).expectNextMatches(r -> r.getTotalCount().equals(1l)).verifyComplete();

    }

    @Test
    public void searchByUser() throws JsonProcessingException {
        String repositoryName = "repositoryName";

        GitHubRepo githubRepo = new GitHubRepo();
        githubRepo.setIncompleteResults(false);
        githubRepo.setTotalCount(1l);
        Repo repo = new Repo();
        repo.setName(repositoryName);
        RepoOwner owner = new RepoOwner();
        owner.setLogin("login");
        repo.setOwner(owner);
        githubRepo.setRepositories(List.of(repo));

        mockBackEnd.enqueue(new MockResponse().setBody(new ObjectMapper().writeValueAsString(githubRepo)).addHeader("Content-Type", "application/json"));

        SearchGithubService searchGithubService = new SearchGithubService(String.format("http://localhost:%s", mockBackEnd.getPort()));

        Mono<GitHubRepo> searchResult = searchGithubService.searchByUser(repositoryName);

        StepVerifier.create(searchResult).expectNextMatches(r -> r.getTotalCount().equals(1l)).verifyComplete();
    }

    @Test
    public void searchByUserWithoutUser() throws JsonProcessingException {
        String repositoryName = "repositoryName";

        GitHubRepo githubRepo = new GitHubRepo();
        githubRepo.setIncompleteResults(false);
        githubRepo.setTotalCount(1l);
        Repo repo = new Repo();
        repo.setName(repositoryName);
        RepoOwner owner = new RepoOwner();
        owner.setLogin("login");
        repo.setOwner(owner);
        githubRepo.setRepositories(List.of(repo));

        mockBackEnd.enqueue(new MockResponse().setBody(new ObjectMapper().writeValueAsString(githubRepo)).addHeader("Content-Type", "application/json"));

        SearchGithubService searchGithubService = new SearchGithubService();

        Mono<GitHubRepo> searchResult = searchGithubService.searchByUser(repositoryName);

        StepVerifier.create(searchResult).expectNextMatches(r -> r.getTotalCount() == null).verifyComplete();
    }

    @Test
    public void searchithoutRepo() throws JsonProcessingException {
        String repositoryName = "repositoryName";

        GitHubRepo githubRepo = new GitHubRepo();
        githubRepo.setIncompleteResults(false);
        githubRepo.setTotalCount(1l);
        Repo repo = new Repo();
        repo.setName(repositoryName);
        RepoOwner owner = new RepoOwner();
        owner.setLogin("login");
        repo.setOwner(owner);
        githubRepo.setRepositories(List.of(repo));

        mockBackEnd.enqueue(new MockResponse().setBody(new ObjectMapper().writeValueAsString(githubRepo)).addHeader("Content-Type", "application/json"));

        SearchGithubService searchGithubService = new SearchGithubService();

        Mono<GitHubRepo> searchResult = searchGithubService.search(repositoryName);

        StepVerifier.create(searchResult).expectNextMatches(r -> r.getTotalCount() == null).verifyComplete();
    }
}