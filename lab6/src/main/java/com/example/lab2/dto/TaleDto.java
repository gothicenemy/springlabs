package com.example.lab2.dto;

public record TaleDto(
        Long id,
        String title,
        String content,
        Integer rating,
        String authorName
) {}