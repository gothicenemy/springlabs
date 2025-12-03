package com.example.lab2.repository;

import com.example.lab2.model.Tale;
import java.util.List;
import java.util.Optional;

public interface TaleDao {
    int create(Tale tale);
    Optional<Tale> findById(int id);
    List<Tale> findAll();
    List<Tale> findByTitle(String title);
    void update(int id, Tale tale);
    void delete(int id);
}