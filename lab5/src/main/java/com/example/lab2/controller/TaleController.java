package com.example.lab2.controller;

import com.example.lab2.model.Tale;
import com.example.lab2.service.TaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tales")
@Tag(name = "Tale API", description = "Lab 5 JDBC & Transactions")
public class TaleController {

    private final TaleService taleService;

    public TaleController(TaleService taleService) {
        this.taleService = taleService;
    }

    @Operation(summary = "Отримати всі казки")
    @GetMapping
    public ResponseEntity<List<Tale>> getAllTales(@RequestParam(required = false) String title) {
        return ResponseEntity.ok(taleService.getAllTales(title));
    }

    @Operation(summary = "Отримати казку за ID")
    @GetMapping("/{id}")
    public ResponseEntity<Tale> getTaleById(@PathVariable Integer id) {
        return taleService.getTaleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Створити казку")
    @PostMapping
    public ResponseEntity<Tale> createTale(@RequestBody Tale tale) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taleService.createTale(tale));
    }

    @Operation(summary = "Оновити казку")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTale(@PathVariable Integer id, @RequestBody Tale tale) {
        taleService.updateTale(id, tale);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Видалити казку")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTale(@PathVariable Integer id) {
        taleService.deleteTale(id);
        return ResponseEntity.noContent().build();
    }

    // Ендпоінт для тестування транзакцій
    @Operation(summary = "Створити кілька казок (Транзакція)")
    @PostMapping("/batch")
    public ResponseEntity<String> createBatch(
            @RequestBody List<Tale> tales,
            @RequestParam(defaultValue = "false") boolean fail
    ) {
        try {
            taleService.createBatchTales(tales, fail);
            return ResponseEntity.ok("Batch created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Transaction rolled back: " + e.getMessage());
        }
    }
}