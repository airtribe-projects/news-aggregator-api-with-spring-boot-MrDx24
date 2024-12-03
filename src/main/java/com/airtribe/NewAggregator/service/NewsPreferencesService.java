package com.airtribe.NewAggregator.service;

import com.airtribe.NewAggregator.entity.NewsPreferences;
import com.airtribe.NewAggregator.repository.NewsPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NewsPreferencesService {

    @Autowired
    private NewsPreferencesRepository newsPreferencesRepository;

    //@Value("${newsapi.key}")
    private String newsApiKey = "7bafe151da4044edb9d4ad3132c3d03b";

    private final WebClient webClient = WebClient.create("https://newsapi.org/v2");

    // Add new preferences
    public NewsPreferences addPreferences(NewsPreferences preferences) {
        return newsPreferencesRepository.save(preferences);
    }

    // Retrieve preferences by user ID
    public NewsPreferences getPreferences(Long userId) {
        return newsPreferencesRepository.findByUserId(userId);
    }

    // Update existing preferences
    public NewsPreferences updatePreferences(NewsPreferences preferences) {
        // Check if preferences exist
        if (newsPreferencesRepository.existsById(preferences.getId())) {
            return newsPreferencesRepository.save(preferences);
        }
        return null; // Or throw an exception if not found
    }

    public String getNewsBasedOnUser(Long userId) {
        NewsPreferences preferences = newsPreferencesRepository.findByUserId(userId);

        if (preferences == null) {
            return null; // or handle as needed
        }

        return fetchNews(preferences);
    }

    private String fetchNews(NewsPreferences preferences) {
        String category = preferences.getCategory();
        //String language = preferences.getLanguage();
        String country = preferences.getCountry();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/top-headlines")
                        .queryParam("category", category)
                        //.queryParam("language", language)
                        .queryParam("country", country)
                        .queryParam("apiKey", newsApiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}