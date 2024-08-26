package com.airtribe.NewAggregator.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "newsPreferences")
public class NewsPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    //private String language;
    private String country;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
