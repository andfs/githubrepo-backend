package com.br.gitrepos.gitrepo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GitHubRepo {

    @JsonProperty(value = "total_count")
    private Long totalCount;
    
    @JsonProperty(value = "incomplete_results")
    private Boolean incompleteResults;

    @JsonProperty(value = "items")
    private List<Repo> repositories;
}