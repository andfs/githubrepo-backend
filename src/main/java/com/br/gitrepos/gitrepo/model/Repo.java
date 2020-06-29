package com.br.gitrepos.gitrepo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Repo {

    @JsonProperty(value = "id")
    private Long id;
    
    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "full_name")
    private String fullName;

    @JsonProperty(value = "private")
    private Boolean nonPublic;

    @JsonProperty(value = "owner")
    private RepoOwner owner;

    @JsonProperty(value = "html_url")
    private String url;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "language")
    private String language;

    @JsonProperty(value = "open_issues")
    private Long openIssues;

    @JsonProperty(value = "score")
    private Integer score;
}
