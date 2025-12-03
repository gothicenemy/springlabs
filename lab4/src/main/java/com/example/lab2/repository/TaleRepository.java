package com.example.lab2.repository;

import com.example.lab2.model.Tale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaleRepository extends JpaRepository<Tale, Integer> {
    Page<Tale> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}