package com.example.testesummitoficial.dto;

public class GitHubRepositoryInfo {
    private String name;
    private String description;
    private String url;
    private int stars;
    private String language;

    public GitHubRepositoryInfo(String name, String description, String url, int stars, String language) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.stars = stars;
        this.language = language;
    }

    public GitHubRepositoryInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}