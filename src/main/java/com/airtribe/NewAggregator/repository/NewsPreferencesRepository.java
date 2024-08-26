package com.airtribe.NewAggregator.repository;

import com.airtribe.NewAggregator.entity.NewsPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsPreferencesRepository extends JpaRepository<NewsPreferences, Long> {
    NewsPreferences findByUserId(Long userId);
}
