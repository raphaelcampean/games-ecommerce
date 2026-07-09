package com.gamesecommerce.store.controller;

import com.gamesecommerce.store.model.Developer;
import com.gamesecommerce.store.record.DeveloperDTO;
import com.gamesecommerce.store.service.DeveloperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/desenvolvedoras")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;


    @GetMapping
    public ResponseEntity<Page<DeveloperDTO>> listAll(
            @PageableDefault(size = 20, sort = "name")
            Pageable pageable) {

        Page<DeveloperDTO> developers =
                developerService.getDevelopers(pageable);

        return ResponseEntity.ok(developers);
    }


    @GetMapping("/{slug}")
    public ResponseEntity<DeveloperDTO> getDeveloper(
            @PathVariable String slug) {

        return developerService.findBySlug(slug)
                .map(developer ->
                        ResponseEntity.ok(new DeveloperDTO(developer)))
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }
}