package com.gamesecommerce.store.service;

import java.util.Optional;
import java.util.UUID;

import com.gamesecommerce.store.model.Genre;
import com.gamesecommerce.store.record.GenreDTO;
import com.gamesecommerce.store.repository.GenreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public Optional<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }

    public Genre create(Genre genre) {
        Optional<Genre> existingGenre = genreRepository.findByName(genre.getName());

        if (existingGenre != null) {
            throw new RuntimeException("Genre with name '" + genre.getName() + "' already exists.");
        }

        genre.setSlug(generateSlug(genre.getName()));

        return genreRepository.save(genre);
    }

    public void deleteById(UUID id) {
        genreRepository.deleteById(id);
    }

    public Genre update(Genre genre) {
        return genreRepository.save(genre);
    }

    public Page<GenreDTO> getGenres(Pageable pageable) {
        return genreRepository.findAll(pageable)
                .map(GenreDTO::new);
    }

    public Genre findById(UUID id) {
        return genreRepository.findById(id).orElse(null);
    }

    public Genre update(UUID id, Genre genre) {
        Genre existingGenre = findById(id);

        if (existingGenre == null) {
            throw new RuntimeException("Genre with id '" + id + "' not found.");
        }

        if (genre.getName() != null && !genre.getName().equals(existingGenre.getName())) {
            existingGenre.setName(genre.getName());
            existingGenre.setSlug(generateSlug(genre.getName()));
        }

        existingGenre.setDescription(
                genre.getDescription() == null
                        ? existingGenre.getDescription()
                        : genre.getDescription()
        );

        return genreRepository.save(existingGenre);
    }

    public String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }

    public Optional<Genre> findBySlug(String slug) {
        return genreRepository.findBySlug(slug);
    }

    public long countGenres() {
        return genreRepository.count();
    }
}