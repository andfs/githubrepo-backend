package com.br.gitrepos.gitrepo.integration;

//Estes endpoints dependem apenas de um serviço externo, por isso o teste não será rodado.

// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SearchControllerIntegrationTest {
  
    // @Autowired
    // private TestRestTemplate testRestTemplate;

    // @Test
    // public void searchWithouRepoAndSearchCorrectly() {
    //     ResponseEntity<GitHubRepo> noRepoResponseEntity = testRestTemplate.getForEntity("/search", GitHubRepo.class);
    //     assertEquals(HttpStatus.BAD_REQUEST, noRepoResponseEntity.getStatusCode());

    //     ResponseEntity<GitHubRepo> responseEntity = testRestTemplate.getForEntity("/search?repo=zemo", GitHubRepo.class);
    //     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    // }
}