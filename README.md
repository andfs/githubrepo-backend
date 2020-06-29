# GithubRepo

Projeto em SpringBoot que contempla o desafio de se criar um sistema para pesquisar repositórios públicos no github.

Além de procurar repositórios públicos deve-se procurar por usuário e ter a possibilidade de favoritar até 5 repositórios.

### `O que e como foi feito`

#### Pesquisa de repositórios

Foi criado um controller `SearchController` para receber as requisições de pesquisa por repositórios e por usuários. Os endpoints `search`e `searchByUser` são endpoints que recebem um `GET` e ambos recebem as informações por `QueryParam`. Os parâmetros são obrigatórios e validados com a anotação `org.springframework.lang.NonNull`.

Ambos os métodos chamam o `SearchGithubService` que executa a pesquisa no github de forma **não blocante**. A url, tanto para buscar por usuário como por nome de repositório são injetadas através do `application.properties`. Foi criado um construtor recebendo uma url para ser usado com o **MockWebServer**.

#### Favoritar repositórios

Os endpoints para se favoritar e desfavoritar estão protegidos através do **Spring Security** com um token **jwt**.

Caso a requisição tenha um token válido, os endpoints de `FavoriteController` espera o `idRepository` como obrigatório. Os endpoints são um `GET` para adicionar um favorito e um `DELETE` para remover um favorito.

Através do token o sistema captura o usuário que está logado e salva uma relação NxN com o repositório que está sendo favoritado. Caso a requisição seja para desfavoritar, a relação é removida.

O endpoint `add` para favoritar um repositório pode lançar uma `FavoritesLimitException` caso o usuário logado tente adicionar mais de 5 favoritos.

#### Login e Cadastro

`AuthController` possui dois endpoints como `POST` que recebem um `UserDTO` com username e password. Este objeto é validado se possuem os campos obrigatórios e se a senha é maior que 6 dígitos. O endpoint `signup` pode lançar uma exceção `DuplicateUserException` caso se tente criar mais de um usuário com o mesmo nome. Já o endpoint `login` lança `UserNotFoundException` caso o nome de usuário ou senha não sejam encontrados na base. Para a validação e criptografia da senha foi usado o `BCrypt`.

#### Exceções

As exceções criadas para o negócio: `FavoritesLimitException`, `DuplicateUserException` e `UserNotFoundException` são capturadas e tratadas para que o retorno tenham o _HttpStatus_ correto. A classe que as trata é a `GithubRepoExceptionHandler`.

#### O que foi usado

- Para a documentação foi usado o **Swagger**.
- Para se mockar os testes foi usado o **MockWebServer**
- Foi usado o [jib](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) para a geração da imagem docker.

### `Deploy`

A aplicaçã está rodando no [Cloud Run](https://cloud.google.com/run?&utm_source=google&utm_medium=cpc&utm_campaign=latam-BR-all-pt-dr-skws-all-all-trial-b-dr-1008075-LUAC0008679&utm_content=text-ad-none-none-DEV_c-CRE_434180531011-ADGP_SKWS+%7C+Multi+~+Compute+%7C+Cloud+Run-KWID_43700053588659795-kwd-677335471139-userloc_1001566&utm_term=KW_%2Bcloud%20%2Brun-ST_%2BCloud+%2BRun&gclid=CjwKCAjw_-D3BRBIEiwAjVMy7FmbynOHXBmOiAJDKYBj8T95OOYSGvhr9hfTikLhBEZqdWlHb-tBmhoC9o8QAvD_BwE&gclsrc=aw.ds).

### `Considerações`

- Não foi implementado os testes de integração do `SearchController` já que ele é todo dependente de um serviço externo.
- Não foram implementados testes para a camada de **Repository**, pois são pouquissimas queries e as que foram usadas são geradas pelo proprio SpringBoot.
- O padrão do **SpringBoot Seciruty** é retornar `403` quando a validação falha. Por simplicidade este comportamento não foi alterado para um `401`.
- Por simplicidade foi utilizado como banco de dados o **H2**
- A url do console do H2 está liberada para fins de conferência.
- Por simplicidade não foi adicionado mais funcionalidades no sistema, como por exemplo paginação da busca no github, busca por repositórios que foram favoritados, favoritos do usuátio etc.

### `Informações de Acesso`

- **URL do backend**: https://githubrepo-dinlr7z3yq-uc.a.run.app
- **URL do console do H2**: https://githubrepo-dinlr7z3yq-uc.a.run.app/h2-console
  - **JDBC URL**: jdbc:h2:mem:githubrepos
  - **User Name**: sa
  - **Password**: password
- **URL do frontend**: https://githubrepo-f3cf4.web.app/
- **URL do swagger**: https://githubrepo-dinlr7z3yq-uc.a.run.app/swagger-ui.html

> **Obs: Caso queira mais informações sobre o frontend, o link do github é: https://github.com/andfs/githubrepo-frontend**
