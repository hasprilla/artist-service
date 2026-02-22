package com.sonifoy.artist.infrastructure.config;

import com.github.javafaker.Faker;
import com.sonifoy.artist.infrastructure.adapter.out.persistence.ArtistEntity;
import com.sonifoy.artist.infrastructure.adapter.out.persistence.ArtistRepository;
import com.sonifoy.artist.infrastructure.adapter.out.persistence.GenreEntity;
import com.sonifoy.artist.infrastructure.adapter.out.persistence.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Component
@Profile("local")
@RequiredArgsConstructor
@Slf4j
public class LocalArtistSeeder implements CommandLineRunner {

        private final ArtistRepository artistRepository;
        private final GenreRepository genreRepository;
        private final Faker faker = new Faker();
        private final Random random = new Random();

        private static final int ARTIST_COUNT = 50;

        @Override
        public void run(String... args) {
                log.info("LocalArtistSeeder execution started.");
                genreRepository.count()
                                .flatMap(count -> {
                                        if (count > 0) {
                                                log.info("Artist database already seeded.");
                                                return Mono.empty();
                                        }
                                        log.info("Seeding genres and artists...");
                                        return seedGenres().then(seedArtists());
                                })
                                .subscribe();
        }

        private Mono<Void> seedGenres() {
                List<String> genreNames = List.of(
                                "Reguetón", "Salsa", "Vallenato", "Merengue", "Bachata",
                                "Pop", "Rock", "Trap", "Hispano", "Urbano", "Jazz", "Electronics");

                return genreRepository.saveAll(
                                genreNames.stream()
                                                .map(name -> GenreEntity.builder().name(name).build())
                                                .toList())
                                .then();
        }

        private Mono<Void> seedArtists() {
                return genreRepository.findAll().collectList()
                                .flatMap(genres -> {
                                        return Flux.range(1, ARTIST_COUNT)
                                                        .flatMap(i -> {
                                                                String name = faker.artist().name();
                                                                String genreName = genres.isEmpty() ? null
                                                                                : genres.get(random
                                                                                                .nextInt(genres.size()))
                                                                                                .getName();

                                                                ArtistEntity artist = ArtistEntity.builder()
                                                                                .name(name)
                                                                                .bio(faker.lorem().paragraph())
                                                                                .imageUrl("https://i.pravatar.cc/300?u="
                                                                                                + name.replace(" ", ""))
                                                                                .genre(genreName)
                                                                                .country(faker.address().country())
                                                                                .language(random.nextBoolean()
                                                                                                ? "English"
                                                                                                : "Spanish")
                                                                                .followersCount((long) faker.number()
                                                                                                .numberBetween(100,
                                                                                                                1000000))
                                                                                .currentStars(random.nextInt(101))
                                                                                .goalStars(100)
                                                                                .build();
                                                                return artistRepository.save(artist);
                                                        })
                                                        .then();
                                });
        }
}
