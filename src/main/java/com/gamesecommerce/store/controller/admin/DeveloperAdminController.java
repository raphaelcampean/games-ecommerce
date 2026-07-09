package com.gamesecommerce.store.controller.admin;

import com.gamesecommerce.store.model.Developer;
import com.gamesecommerce.store.record.DeveloperDTO;
import com.gamesecommerce.store.service.DeveloperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/desenvolvedoras")
public class DeveloperAdminController {

    @Autowired
    DeveloperService developerService;


    @GetMapping
    public ResponseEntity<Page<DeveloperDTO>> listAll(
            @PageableDefault(size = 20, sort = "name")
            Pageable pageable) {

        Page<DeveloperDTO> developers =
                developerService.getDevelopers(pageable);

        return ResponseEntity.ok(developers);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloper(
            @PathVariable UUID id) {

        Developer developer = developerService.findById(id);

        if (developer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(developer);
    }


    @PostMapping
    public ResponseEntity<Developer> createDeveloper(
            @RequestBody @Validated Developer developer) {

        Developer created = developerService.create(developer);

        return ResponseEntity.status(201).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Developer> updateDeveloper(
            @PathVariable UUID id,
            @RequestBody @Validated Developer developer) {

        Developer updated =
                developerService.update(id, developer);

        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(
            @PathVariable UUID id) {

        developerService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}