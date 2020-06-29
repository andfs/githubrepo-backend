package com.br.gitrepos.gitrepo.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .securitySchemes(Arrays.asList(new ApiKey("apiKey", HttpHeaders.AUTHORIZATION, "header")))
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(getApiInfo())
        .pathMapping("/");
  }

    private ApiInfo getApiInfo() {
        ApiInfo info = new ApiInfo("GithubRepo", 
            "Pesquisa por repositórios públicos do github por nome e por usuário. \nTambém é possível favoritar até 5 repositórios. "+
            "Para isso é necessário estar logado. \nA persistência é feita em um banco h2.\n" +
            "<b>Para testar os endpoints protegitos: crie um usuário, copie o token, clique em Authorize e digite 'Bearer <token_copiado>'", 
            "1.0", 
            null, new Contact("Anderson", null, "andfariasilva@gmail.com"), null, null, Collections.emptyList());
        return info;
    }
}