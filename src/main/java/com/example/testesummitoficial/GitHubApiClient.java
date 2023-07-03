package com.example.testesummitoficial;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GitHubApiClient {
    private static final String GITHUB_API_URL = "https://api.github.com/users/%s/repos";

    public GitHubRepositoryInfo[] getPublicRepositories(String username) {
        String apiUrl = String.format(GITHUB_API_URL, username);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GitHubRepositoryInfo[]> response = restTemplate.exchange(
                apiUrl, HttpMethod.GET, null, GitHubRepositoryInfo[].class);
        return response.getBody();
    }
}
