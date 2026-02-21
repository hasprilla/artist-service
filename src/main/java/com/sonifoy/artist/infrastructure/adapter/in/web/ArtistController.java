package com.sonifoy.artist.infrastructure.adapter.in.web;

import com.sonifoy.artist.application.service.ArtistService;
import com.sonifoy.artist.domain.model.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/public")
    public Flux<Artist> getAllArtists(@RequestParam(required = false) String genre) {
        return artistService.getAllArtists(genre);
    }

    @GetMapping("/public/{id}")
    public Mono<Artist> getArtistById(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @PostMapping
    public Mono<Artist> createArtist(@RequestBody Artist artist) {
        return artistService.createArtist(artist);
    }
}
