package com.airtribe.NewAggregator.controller;


import com.airtribe.NewAggregator.entity.NewsPreferences;
import com.airtribe.NewAggregator.entity.User;
import com.airtribe.NewAggregator.repository.UserRepository;
import com.airtribe.NewAggregator.service.NewsPreferencesService;
import com.airtribe.NewAggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NewsController {


    //api key : 7bafe151da4044edb9d4ad3132c3d03b
    @Autowired
    private NewsPreferencesService newsPreferencesService;

    @Autowired
    private UserRepository userRepository;


    // Add new preferences
    @PostMapping("/preference")
    public ResponseEntity<String> addPreferences(@RequestBody NewsPreferences preferences, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        preferences.setUser(user);
        newsPreferencesService.addPreferences(preferences);
        return new ResponseEntity<>("Preferences added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/preference/{userId}")
    public ResponseEntity<NewsPreferences> getPreferences(@PathVariable Long userId, Authentication authentication) {
        // Verify that the authenticated user is accessing their own preferences
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userId).orElse(null);

        if (user == null || !user.getUsername().equals(userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        NewsPreferences preferences = newsPreferencesService.getPreferences(userId);
        if (preferences != null) {
            return new ResponseEntity<>(preferences, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/preference/{userId}")
    public ResponseEntity<NewsPreferences> updatePreferences(@PathVariable Long userId, @RequestBody NewsPreferences preferences, Authentication authentication) {
        // Verify that the authenticated user is updating their own preferences
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userId).orElse(null);

        if (user == null || !user.getUsername().equals(userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Retrieve the existing preferences by user ID
        NewsPreferences existingPreferences = newsPreferencesService.getPreferences(userId);

        if (existingPreferences == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the existing preferences with new values
        existingPreferences.setCategory(preferences.getCategory());
        //existingPreferences.setLanguage(preferences.getLanguage());
        existingPreferences.setCountry(preferences.getCountry());

        // Save the updated preferences
        NewsPreferences updatedPreferences = newsPreferencesService.updatePreferences(existingPreferences);

        return new ResponseEntity<>(updatedPreferences, HttpStatus.OK);
    }


    @GetMapping("/news")
    public ResponseEntity<String> getNews(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        String news = newsPreferencesService.getNewsBasedOnUser(user.getId());
        if (news != null && !news.isEmpty()) {
            return new ResponseEntity<>(news, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No news found for the given preferences", HttpStatus.NOT_FOUND);
        }
    }

}
