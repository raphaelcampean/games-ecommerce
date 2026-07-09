package com.gamesecommerce.store.controller.admin;

import com.gamesecommerce.store.model.Genre;
import com.gamesecommerce.store.record.GenreDTO;
import com.gamesecommerce.store.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/generos")
public class GenreAdminController {

    @Autowired
    GenreService genreService;

    @GetMapping
    public ResponseEntity<Page<GenreDTO>> listAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {

        Page<GenreDTO> genres = genreService.getGenres(pageable);

        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable UUID id) {
        Genre genre = genreService.findById(id);
        return ResponseEntity.ok(genre);
    }

    @PostMapping
    public ResponseEntity<Genre> createGenre(
            @RequestBody @Validated Genre genre) {

        Genre created = genreService.create(genre);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(
            @PathVariable UUID id,
            @RequestBody @Validated Genre genre) {

        Genre updated = genreService.update(id, genre);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable UUID id) {
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}