package com.sonifoy.artist.infrastructure.adapter.out.persistence;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface GenreRepository extends R2dbcRepository<GenreEntity, Long> {
    Mono<GenreEntity> findByName(String name);
}
