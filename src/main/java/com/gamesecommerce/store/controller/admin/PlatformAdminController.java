package com.gamesecommerce.store.controller.admin;

import com.gamesecommerce.store.model.Platform;
import com.gamesecommerce.store.record.PlatformDTO;
import com.gamesecommerce.store.service.PlatformService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/platforms")
public class PlatformAdminController {

    @Autowired
    PlatformService platformService;


    @GetMapping
    public ResponseEntity<Page<PlatformDTO>> listAll(
            @PageableDefault(size = 20, sort = "name")
            Pageable pageable) {

        Page<PlatformDTO> platforms =
                platformService.getPlatforms(pageable);

        return ResponseEntity.ok(platforms);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Platform> getPlatform(
            @PathVariable UUID id) {

        Platform platform = platformService.findById(id);

        if (platform == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(platform);
    }


    @PostMapping
    public ResponseEntity<Platform> createPlatform(
            @RequestBody @Validated Platform platform) {

        Platform created = platformService.create(platform);

        return ResponseEntity.status(201).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Platform> updatePlatform(
            @PathVariable UUID id,
            @RequestBody @Validated Platform platform) {

        Platform updated =
                platformService.update(id, platform);

        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlatform(
            @PathVariable UUID id) {

        platformService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}