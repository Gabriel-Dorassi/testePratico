package com.example.testesummitoficial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repositories")
public class GitHubRepositoryController {
    private final GitHubApiClient gitHubApiClient;

    @Autowired
    public GitHubRepositoryController(GitHubApiClient gitHubApiClient) {
        this.gitHubApiClient = gitHubApiClient;
    }

    @GetMapping("/{username}")
    public GitHubRepositoryInfo[] getRepositories(@PathVariable String username) {
        return gitHubApiClient.getPublicRepositories(username);
    }
}
