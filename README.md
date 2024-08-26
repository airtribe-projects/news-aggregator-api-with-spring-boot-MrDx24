# News Aggregator API

This project is a Spring Boot application that provides a RESTful API for managing user registration, authentication, and news preferences. It uses JWT for token-based authentication and an in-memory database for storing user data and preferences. The application also integrates with external news APIs to fetch news articles based on user preferences.

## Features

- User registration and login with JWT authentication
- In-memory database for user and preference storage
- RESTful API with endpoints for user registration, login, and news preferences management
- Integration with external news APIs to fetch articles based on user preferences
- Exception handling and input validation

## Technologies Used

- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- In-Memory Database (H2)
- Spring Web (WebClient for asynchronous HTTP requests)
- Maven or Gradle (build tool)

## Endpoints

- #### Register a new user.
        POST /api/register

- #### Log in a user and receive a JWT token.
        POST /api/login

- #### Retrieve the news preferences for the logged-in user.
        GET /api/preferences/{userid}

- #### Update the news preferences for the logged-in user.
        PUT /api/preferences/{userid} 

- #### Get the news based on preferences for the logged-in user.
        PUT /api/preferences


## Authorization and Authentication: 

- #### Implemented using Jwt 
  - Token Generation
  - Token Validation
  - Token Expiration
  
  
## External News APIs
- The application fetches news articles from external news APIs. Some APIs that can be used:

- NewsAPI: Provides news articles from various sources. Note that this API has a limit on the number of requests per day. NewsAPI Documentation

## Testing
You can use Postman or Curl to test the API endpoints. Ensure to include the JWT token in the Authorization header for endpoints that require authentication.

## Exception Handling
- Exception handling is also implemented.