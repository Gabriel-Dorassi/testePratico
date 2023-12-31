# API GitHub

## Descrição

Esta é uma API Spring Java que se conecta à API do GitHub e exibe informações sobre os repositórios públicos de um
determinado usuário. A API é capaz de receber o nome de usuário do GitHub como entrada e retorna os seguintes detalhes
sobre os repositórios desse usuário:
`/respositories/{username}`

### Parâmetros de URL

    {username}: O nome de usuário do GitHub para obter informações dos repositórios.

A resposta contém uma lista de objetos de repositório, cada um contendo as seguintes informações:

    name: O nome do repositório.
    description: A descrição do repositório.
    url: A URL do repositório.
    stars: O número de estrelas do repositório.
    language: A linguagem principal do repositório.

## Exemplo de Uso

    GET http://localhost:8080/repositories/Gabriel-Dorassi

## Dependências

- Java 17
- Spring Boot
- Maven

## Como executar

Compile o código usando maven e java 17:

    mvn clean install

Usando o jar gerado:

    java -jar target/teste-summit-oficial-0.0.1-SNAPSHOT.jar
