package com.example.lab2.controller;

import com.example.lab2.model.Tale;
import com.example.lab2.service.TaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tales")
@Tag(name = "Tale API", description = "Lab 4 Management")
public class TaleController {

    private final TaleService taleService;

    public TaleController(TaleService taleService) {
        this.taleService = taleService;
    }

    @Operation(summary = "Get all tales")
    @GetMapping
    public ResponseEntity<Page<Tale>> getAllTales(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(taleService.getAllTales(title, page, size));
    }

    @Operation(summary = "Get tale by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Tale> getTaleById(@PathVariable Integer id) {
        return taleService.getTaleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create tale")
    @PostMapping
    public ResponseEntity<Tale> createTale(@RequestBody Tale tale) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taleService.createTale(tale));
    }

    @Operation(summary = "Update full tale")
    @PutMapping("/{id}")
    public ResponseEntity<Tale> updateTale(@PathVariable Integer id, @RequestBody Tale tale) {
        return taleService.updateTale(id, tale)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Patch tale")
    @PatchMapping("/{id}")
    public ResponseEntity<Tale> patchTale(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        return taleService.patchTale(id, updates)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete tale")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTale(@PathVariable Integer id) {
        if (taleService.deleteTale(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}