package com.example.testesummitoficial.service;

import com.example.testesummitoficial.dto.GitHubRepositoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


@Service
public class GitHubApiService {
    private static final String GITHUB_API_URL = "https://api.github.com/users/%s/repos";
    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubApiService.class);
    private final RestTemplate restTemplate;

    @Autowired
    public GitHubApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GitHubRepositoryInfo[] getPublicRepositories(String username) {
        var apiUrl = String.format(GITHUB_API_URL, username);
        try {
            return restTemplate.exchange(
                    apiUrl, HttpMethod.GET, null, GitHubRepositoryInfo[].class).getBody();
        } catch (HttpClientErrorException httpClientErrorException) {
            LOGGER.error(String.valueOf(httpClientErrorException));
            if (httpClientErrorException.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or no public repositories");
            } else if (httpClientErrorException.getStatusCode().is5xxServerError()) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Server error in github API");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
            }
        }
    }
}