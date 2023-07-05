package com.example.testesummitoficial.controller;

import com.example.testesummitoficial.dto.GitHubRepositoryInfo;
import com.example.testesummitoficial.service.GitHubApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitHubRepositoryControllerTest {
    @Mock
    private GitHubApiService gitHubApiService;

    @InjectMocks
    private GitHubRepositoryController gitHubRepositoryController;


    @Test
    void testGetPublicRepositories_Success() {
        GitHubRepositoryInfo[] repositories = {new GitHubRepositoryInfo("test", "test desc", "http://github.com/username/test", 4, "Java")};
        when(gitHubApiService.getPublicRepositories(anyString())).thenReturn(repositories);

        ResponseEntity<Object> responseEntity = gitHubRepositoryController.getPublicRepositories("username");

        verify(gitHubApiService, times(1)).getPublicRepositories(anyString());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArrayEquals(repositories, (GitHubRepositoryInfo[]) responseEntity.getBody());
    }

    @Test
    void testGetPublicRepositories_UserNotFound() {
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or no public repositories");
        when(gitHubApiService.getPublicRepositories(anyString())).thenThrow(exception);

        ResponseEntity<Object> responseEntity = gitHubRepositoryController.getPublicRepositories("username");

        verify(gitHubApiService, times(1)).getPublicRepositories(anyString());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User not found or no public repositories", responseEntity.getBody());
    }

    @Test
    void testGetPublicRepositories_InternalServerError() {
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        when(gitHubApiService.getPublicRepositories(anyString())).thenThrow(exception);

        ResponseEntity<Object> responseEntity = gitHubRepositoryController.getPublicRepositories("username");

        verify(gitHubApiService, times(1)).getPublicRepositories(anyString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal Server Error", responseEntity.getBody());
    }

    @Test
    void testGetPublicRepositories_UnknownError() {
        RuntimeException exception = new RuntimeException("Unknown error");
        when(gitHubApiService.getPublicRepositories(anyString())).thenThrow(exception);

        ResponseEntity<Object> responseEntity = gitHubRepositoryController.getPublicRepositories("username");

        verify(gitHubApiService, times(1)).getPublicRepositories(anyString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal Server Error", responseEntity.getBody());
    }
}