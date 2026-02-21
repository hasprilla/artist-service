package com.sonifoy.artist.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    private Long id;
    private String name;
    private String bio;
    private String imageUrl;
    private String genre;
    private String country;
    private String language;
    private Long followersCount;
    private Integer currentStars;
    private Integer goalStars;
    private Instant createdAt;
    private Instant updatedAt;
}
