package com.example.testesummitoficial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GitHubRepositoryController {
    private final GitHubApiClient gitHubApiClient;

    @Autowired
    public GitHubRepositoryController(GitHubApiClient gitHubApiClient) {
        this.gitHubApiClient = gitHubApiClient;
    }
        @GetMapping("/repositories/{username}")
        public ResponseEntity<?> getPublicRepositories(@PathVariable String username) {
            try {
                GitHubRepositoryInfo[] repositories = gitHubApiClient.getPublicRepositories(username);
                if (repositories.length > 0) {
                    return ResponseEntity.ok(repositories);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } catch (ResponseStatusException e) {
                String errorMessage = e.getReason();
                return ResponseEntity.status(e.getStatusCode()).body(errorMessage);
            } catch (Exception e) {
                String errorMessage = "Internal server error";
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
            }
        }
    }
