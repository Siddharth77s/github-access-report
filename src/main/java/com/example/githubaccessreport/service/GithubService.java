package com.example.githubaccessreport.service;

import com.example.githubaccessreport.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GithubService {

    @Value("${github.org}")
    private String org;

    @Value("${github.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders createHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Accept", "application/vnd.github+json");

        return headers;
    }

    public List<UserAccess> generateAccessReport() {

        Map<String, List<String>> userRepoMap = new ConcurrentHashMap<>();

        List<Repository> repos = getRepositories();

        repos.parallelStream().forEach(repo -> {

            List<Collaborator> collaborators =
                    getCollaborators(repo.getName());

            for (Collaborator user : collaborators) {

                userRepoMap
                        .computeIfAbsent(user.getLogin(), k -> new ArrayList<>())
                        .add(repo.getName());
            }
        });

        List<UserAccess> result = new ArrayList<>();

        userRepoMap.forEach((user, repoList) ->
                result.add(new UserAccess(user, repoList)));

        return result;
    }

    private List<Repository> getRepositories() {

        List<Repository> repos = new ArrayList<>();
        int page = 1;

        while (true) {

            String url = "https://api.github.com/orgs/" + org +
                    "/repos?per_page=100&page=" + page;

            HttpEntity<String> entity = new HttpEntity<>(createHeaders());

            ResponseEntity<Repository[]> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Repository[].class);

            Repository[] body = response.getBody();

            if (body == null || body.length == 0)
                break;

            repos.addAll(Arrays.asList(body));

            page++;
        }

        return repos;
    }

    private List<Collaborator> getCollaborators(String repo) {

        String url = "https://api.github.com/repos/" + org +
                "/" + repo + "/collaborators";

        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<Collaborator[]> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, Collaborator[].class);

        return Arrays.asList(response.getBody());
    }
}