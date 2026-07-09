package com.gamesecommerce.store.service;

import com.gamesecommerce.store.model.Platform;
import com.gamesecommerce.store.record.PlatformDTO;
import com.gamesecommerce.store.repository.PlatformRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlatformService {

    @Autowired
    PlatformRepository platformRepository;


    public Optional<Platform> findByName(String name) {
        return platformRepository.findByName(name);
    }


    public Platform create(Platform platform) {

        Optional<Platform> existingPlatform =
                platformRepository.findByName(platform.getName());

        if (existingPlatform.isPresent()) {
            throw new RuntimeException(
                    "Platform with name '" + platform.getName() + "' already exists."
            );
        }

        platform.setSlug(generateSlug(platform.getName()));

        return platformRepository.save(platform);
    }


    public void deleteById(UUID id) {
        platformRepository.deleteById(id);
    }


    public Page<PlatformDTO> getPlatforms(Pageable pageable) {

        return platformRepository.findAll(pageable)
                .map(PlatformDTO::new);
    }


    public Platform findById(UUID id) {

        return platformRepository.findById(id)
                .orElse(null);
    }


    public Platform update(UUID id, Platform platform) {

        Platform existingPlatform = findById(id);

        if (existingPlatform == null) {
            throw new RuntimeException(
                    "Platform with id '" + id + "' not found."
            );
        }


        if (platform.getName() != null &&
                !platform.getName().equals(existingPlatform.getName())) {

            existingPlatform.setName(platform.getName());
            existingPlatform.setSlug(generateSlug(platform.getName()));
        }


        existingPlatform.setDescription(
                platform.getDescription() == null
                        ? existingPlatform.getDescription()
                        : platform.getDescription()
        );


        return platformRepository.save(existingPlatform);
    }


    public Optional<Platform> findBySlug(String slug) {
        return platformRepository.findBySlug(slug);
    }


    public String generateSlug(String name) {

        return name.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }


    public long countPlatforms() {
        return platformRepository.count();
    }
}