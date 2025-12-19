package com.example.lab2.controller;

import com.example.lab2.dto.AuthorDto;
import com.example.lab2.dto.TaleDto;
import com.example.lab2.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@Tag(name = "Library API", description = "Lab 6: JPA Relations & DTO")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Operation(summary = "Створити автора з книгами", description = "Демонстрація One-To-Many + Cascade")
    @PostMapping("/authors")
    public AuthorDto createAuthor(@RequestBody AuthorDto authorDto) {
        return libraryService.createAuthorWithTales(authorDto);
    }

    @Operation(summary = "Отримати всіх авторів")
    @GetMapping("/authors")
    public List<AuthorDto> getAllAuthors() {
        return libraryService.getAllAuthors();
    }

    @Operation(summary = "Пошук казок (Custom Query)", description = "Використовує JPQL @Query")
    @GetMapping("/tales/search")
    public List<TaleDto> searchTales(@RequestParam String title) {
        return libraryService.searchTales(title);
    }

    @Operation(summary = "ТОП казок (Named Query)", description = "Використовує @NamedQuery")
    @GetMapping("/tales/top")
    public List<TaleDto> getTopTales(@RequestParam int minRating) {
        return libraryService.getTopRatedTales(minRating);
    }
}