package com.example.testesummitoficial;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


@Component
public class GitHubApiClient {
    private static final String GITHUB_API_URL = "https://api.github.com/users/%s/repos";

    public GitHubRepositoryInfo[] getPublicRepositories(String username) {
        String apiUrl = String.format(GITHUB_API_URL, username);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GitHubRepositoryInfo[]> response = restTemplate.exchange(
                apiUrl, HttpMethod.GET, null, GitHubRepositoryInfo[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or no public repositories");
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        } else {
            throw new ResponseStatusException(response.getStatusCode(), "Unknown error");
        }
    }
}

