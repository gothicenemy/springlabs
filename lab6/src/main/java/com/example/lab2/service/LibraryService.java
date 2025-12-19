package com.example.lab2.service;

import com.example.lab2.dto.AuthorDto;
import com.example.lab2.dto.TaleDto;
import com.example.lab2.model.Author;
import com.example.lab2.model.Tale;
import com.example.lab2.repository.AuthorRepository;
import com.example.lab2.repository.TaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final TaleRepository taleRepository;

    public LibraryService(AuthorRepository authorRepository, TaleRepository taleRepository) {
        this.authorRepository = authorRepository;
        this.taleRepository = taleRepository;
    }

    public AuthorDto createAuthorWithTales(AuthorDto dto) {
        Author author = new Author(dto.name());

        if (dto.tales() != null) {
            for (TaleDto tDto : dto.tales()) {
                Tale tale = new Tale(tDto.title(), tDto.content(), tDto.rating());
                tale.setAuthor(author);
                author.getTales().add(tale);
            }
        }

        Author saved = authorRepository.save(author);
        return mapToAuthorDto(saved);
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::mapToAuthorDto)
                .collect(Collectors.toList());
    }


    public List<TaleDto> searchTales(String title) {
        return taleRepository.searchByTitleCustom(title).stream()
                .map(this::mapToTaleDto)
                .collect(Collectors.toList());
    }

    public List<TaleDto> getTopRatedTales(int minRating) {
        return taleRepository.findByRatingGreaterThan(minRating).stream()
                .map(this::mapToTaleDto)
                .collect(Collectors.toList());
    }

    private AuthorDto mapToAuthorDto(Author author) {
        List<TaleDto> taleDtos = author.getTales().stream()
                .map(this::mapToTaleDto)
                .toList();
        return new AuthorDto(author.getId(), author.getName(), taleDtos);
    }

    private TaleDto mapToTaleDto(Tale tale) {
        return new TaleDto(
                tale.getId(),
                tale.getTitle(),
                tale.getContent(),
                tale.getRating(),
                tale.getAuthor() != null ? tale.getAuthor().getName() : "Unknown"
        );
    }
}