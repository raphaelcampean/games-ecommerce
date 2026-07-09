package com.gamesecommerce.store.service;

import com.gamesecommerce.store.model.Developer;
import com.gamesecommerce.store.record.DeveloperDTO;
import com.gamesecommerce.store.repository.DeveloperRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeveloperService {

    @Autowired
    DeveloperRepository developerRepository;


    public Optional<Developer> findByName(String name) {
        return developerRepository.findByName(name);
    }


    public Developer create(Developer developer) {

        Optional<Developer> existingDeveloper =
                developerRepository.findByName(developer.getName());

        if (existingDeveloper.isPresent()) {
            throw new RuntimeException(
                    "Developer with name '" + developer.getName() + "' already exists."
            );
        }

        developer.setSlug(generateSlug(developer.getName()));

        return developerRepository.save(developer);
    }


    public void deleteById(UUID id) {
        developerRepository.deleteById(id);
    }


    public Page<DeveloperDTO> getDevelopers(Pageable pageable) {
        return developerRepository.findAll(pageable)
                .map(DeveloperDTO::new);
    }


    public Developer findById(UUID id) {
        return developerRepository.findById(id)
                .orElse(null);
    }


    public Developer update(UUID id, Developer developer) {

        Developer existingDeveloper = findById(id);

        if (existingDeveloper == null) {
            throw new RuntimeException(
                    "Developer with id '" + id + "' not found."
            );
        }


        if (developer.getName() != null &&
                !developer.getName().equals(existingDeveloper.getName())) {

            existingDeveloper.setName(developer.getName());
            existingDeveloper.setSlug(generateSlug(developer.getName()));
        }


        existingDeveloper.setDescription(
                developer.getDescription() == null
                        ? existingDeveloper.getDescription()
                        : developer.getDescription()
        );


        return developerRepository.save(existingDeveloper);
    }


    public Optional<Developer> findBySlug(String slug) {
        return developerRepository.findBySlug(slug);
    }


    public String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }


    public long countDevelopers() {
        return developerRepository.count();
    }
}