package com.example.testesummitoficial.service;

import com.example.testesummitoficial.dto.GitHubRepositoryInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitHubApiServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubApiService gitHubApiService;

    @Test
    void testGetPublicRepositories_Success() {
        GitHubRepositoryInfo[] repositories = {new GitHubRepositoryInfo("test", "test desc", "https://github.com/username/test", 4, "Java")};
        ResponseEntity<GitHubRepositoryInfo[]> response = new ResponseEntity<>(repositories, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(GitHubRepositoryInfo[].class))).thenReturn(response);

        GitHubRepositoryInfo[] result = gitHubApiService.getPublicRepositories("username");

        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(GitHubRepositoryInfo[].class));
        assertArrayEquals(repositories, result);
    }

    @Test
    void testGetPublicRepositories_UserNotFound() {
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(GitHubRepositoryInfo[].class))).thenThrow(exception);

        assertThrows(ResponseStatusException.class, () -> gitHubApiService.getPublicRepositories("username"), "User not found or no public repositories");
    }

    @Test
    void testGetPublicRepositories_ServerError() {
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(GitHubRepositoryInfo[].class))).thenThrow(exception);

        assertThrows(ResponseStatusException.class, () -> gitHubApiService.getPublicRepositories("username"), "Server error in GitHub API");
    }

    @Test
    void testGetPublicRepositories_UnknownError() {
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.FORBIDDEN);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(GitHubRepositoryInfo[].class))).thenThrow(exception);

        assertThrows(ResponseStatusException.class, () -> gitHubApiService.getPublicRepositories("username"), "Unknown error");
    }
}