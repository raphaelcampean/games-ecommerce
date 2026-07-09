package com.gamesecommerce.store.controller;

import com.gamesecommerce.store.record.PlatformDTO;
import com.gamesecommerce.store.service.PlatformService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plataformas")
public class PlatformController {

    @Autowired
    private PlatformService platformService;


    @GetMapping
    public ResponseEntity<Page<PlatformDTO>> listAll(
            @PageableDefault(size = 20, sort = "name")
            Pageable pageable) {

        Page<PlatformDTO> platforms =
                platformService.getPlatforms(pageable);

        return ResponseEntity.ok(platforms);
    }


    @GetMapping("/{slug}")
    public ResponseEntity<PlatformDTO> getPlatform(
            @PathVariable String slug) {

        return platformService.findBySlug(slug)
                .map(platform ->
                        ResponseEntity.ok(new PlatformDTO(platform)))
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }
}