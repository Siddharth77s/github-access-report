package com.example.githubaccessreport.controller;

import com.example.githubaccessreport.model.UserAccess;
import com.example.githubaccessreport.service.GithubService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccessReportController {

    private final GithubService githubService;

    public AccessReportController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/access-report")
    public List<UserAccess> getAccessReport() {
        return githubService.generateAccessReport();
    }
}