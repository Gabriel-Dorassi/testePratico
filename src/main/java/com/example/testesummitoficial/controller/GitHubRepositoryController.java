package com.example.testesummitoficial.controller;

import com.example.testesummitoficial.service.GitHubApiService;
import com.example.testesummitoficial.dto.GitHubRepositoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GitHubRepositoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubRepositoryController.class);
    private final GitHubApiService gitHubApiService;

    @Autowired
    public GitHubRepositoryController(GitHubApiService gitHubApiService) {
        this.gitHubApiService = gitHubApiService;
    }

    @GetMapping("/repositories/{username}")
    public ResponseEntity<Object> getPublicRepositories(@PathVariable String username) {
        try {
            GitHubRepositoryInfo[] repositories = gitHubApiService.getPublicRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (ResponseStatusException e) {
            LOGGER.error(String.valueOf(e));
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}