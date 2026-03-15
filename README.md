# GitHub Access Report Service

## Overview

This project is a REST service that connects to the GitHub API and generates an access report showing which users have access to which repositories within a GitHub organization.

The service authenticates with GitHub, retrieves repositories from the organization, determines the collaborators for each repository, and exposes a REST endpoint that returns an aggregated access report.

---

## Tech Stack

* Java
* Spring Boot
* GitHub REST API
* Maven
* Postman (for testing)

---

## Project Structure

├── .mvn
│   └── wrapper
│       └── maven-wrapper.properties
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── githubaccessreport
│   │   │               ├── config
│   │   │               ├── controller
│   │   │               │   └── AccessReportController.java
│   │   │               ├── model
│   │   │               │   ├── AccessReport.java
│   │   │               │   ├── Collaborator.java
│   │   │               │   ├── Repository.java
│   │   │               │   └── UserAccess.java
│   │   │               ├── service
│   │   │               │   └── GithubService.java
│   │   │               └── GithubAccessReportApplication.java
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── githubaccessreport
│                       └── GithubAccessReportApplicationTests.java
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml

* controller – REST API controller
* service – Business logic and GitHub API calls
* model – Data models for repository and user access
* config – Configuration classes
* application.properties – Application configuration

---

## Authentication

The application authenticates with GitHub using a Personal Access Token.

Configuration is provided in `application.properties`.

Example:

github.org=YOUR_ORGANIZATION_NAME
github.token=YOUR_GITHUB_PERSONAL_ACCESS_TOKEN

The token is passed in the HTTP Authorization header when calling the GitHub API.

---

## Running the Application

1. Clone the repository

git clone (https://github.com/Siddharth77s/github-access-report)

2. Navigate to the project directory

cd github-access-report

3. Update the configuration

Edit `application.properties` and add your GitHub organization name and token.

4. Run the application

mvn spring-boot:run

The server will start on:

http://localhost:8080

---

## API Endpoint

Get the access report:

GET /api/access-report

Example request:

http://localhost:8080/api/access-report

Example response:

[
{
"username": "Siddharth77s",
"repositories": [
"demo-repo"
]
}
]

The response shows which repositories each user has access to.

---

## Scalability Considerations

The implementation supports organizations with large numbers of repositories and users.

Key design decisions:

* GitHub repositories are retrieved with pagination
* Repository collaborator lookups run in parallel using Java parallel streams
* ConcurrentHashMap is used for thread-safe aggregation
* The service avoids unnecessary sequential API calls

This ensures the system can handle organizations with 100+ repositories and 1000+ users.

---

## Assumptions

* The provided GitHub token has sufficient permissions to read organization repositories and collaborators.
* Only direct repository collaborators are considered in the access report.
* The service assumes the organization exists and the token has access to it.

---

## Future Improvements

* Add caching to reduce GitHub API calls
* Add Swagger API documentation
* Add rate limit handling for GitHub API
* Export access reports as CSV or Excel
