package com.gamesecommerce.store.controller;

import com.gamesecommerce.store.model.Genre;
import com.gamesecommerce.store.record.GenreDTO;
import com.gamesecommerce.store.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/generos")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity<Page<GenreDTO>> listAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {

        Page<GenreDTO> genres = genreService.getGenres(pageable);

        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<GenreDTO> getGenre(@PathVariable String slug) {
        return genreService.findBySlug(slug)
                .map(genre -> ResponseEntity.ok(new GenreDTO(genre)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}