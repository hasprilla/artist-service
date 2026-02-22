package com.sonifoy.artist.infrastructure.adapter.out.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.Instant;

@Table("artists")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistEntity {
    @Id
    private Long id;
    private String name;
    private String bio;
    private String imageUrl;
    private String genre; // Changed to store genre name directly
    private String country;
    private String language;
    private Long followersCount;
    private Integer currentStars;
    private Integer goalStars;
    private Instant createdAt;
    private Instant updatedAt;
}
