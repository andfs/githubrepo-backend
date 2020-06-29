package com.br.gitrepos.gitrepo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RepoOwner {

    @JsonProperty(value = "login")
    private String login;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "avatar_url")
    private String avatarUrl;

    @JsonProperty(value = "html_url")
    private String url;

    @JsonProperty(value = "repos_url")
    private String reposUrl;
}
