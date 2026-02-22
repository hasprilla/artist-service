package com.sonifoy.artist.infrastructure.adapter.out.persistence;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface ArtistRepository extends R2dbcRepository<ArtistEntity, Long> {
    Flux<ArtistEntity> findByGenreId(Long genreId);

    Flux<ArtistEntity> findByGenre(String genre);

    Mono<ArtistEntity> findByName(String name);
}
