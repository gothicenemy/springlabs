package com.example.lab2.dto;

import java.util.List;

public record AuthorDto(
        Long id,
        String name,
        List<TaleDto> tales
) {}