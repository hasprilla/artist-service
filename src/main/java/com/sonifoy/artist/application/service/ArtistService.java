package com.sonifoy.artist.application.service;

import com.sonifoy.artist.domain.model.Artist;
import com.sonifoy.artist.infrastructure.adapter.out.persistence.ArtistEntity;
import com.sonifoy.artist.infrastructure.adapter.out.persistence.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public Flux<Artist> getAllArtists(String genre) {
        if (genre != null && !genre.isBlank()) {
            return artistRepository.findByGenre(genre).map(this::mapToDomain);
        }
        return artistRepository.findAll().map(this::mapToDomain);
    }

    public Mono<Artist> getArtistById(Long id) {
        return artistRepository.findById(id).map(this::mapToDomain);
    }

    public Mono<Artist> createArtist(Artist artist) {
        ArtistEntity entity = mapToEntity(artist);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        return artistRepository.save(entity).map(this::mapToDomain);
    }

    private Artist mapToDomain(ArtistEntity entity) {
        return Artist.builder()
                .id(entity.getId())
                .name(entity.getName())
                .bio(entity.getBio())
                .imageUrl(entity.getImageUrl())
                .genre(entity.getGenre())
                .country(entity.getCountry())
                .language(entity.getLanguage())
                .followersCount(entity.getFollowersCount())
                .currentStars(entity.getCurrentStars())
                .goalStars(entity.getGoalStars())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private ArtistEntity mapToEntity(Artist domain) {
        return ArtistEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .bio(domain.getBio())
                .imageUrl(domain.getImageUrl())
                .genre(domain.getGenre())
                .country(domain.getCountry())
                .language(domain.getLanguage())
                .followersCount(domain.getFollowersCount())
                .currentStars(domain.getCurrentStars())
                .goalStars(domain.getGoalStars())
                .build();
    }
}
