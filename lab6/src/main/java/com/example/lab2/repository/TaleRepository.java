package com.example.lab2.repository;

import com.example.lab2.model.Tale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TaleRepository extends JpaRepository<Tale, Long> {

    @Query("SELECT t FROM Tale t WHERE t.title LIKE %?1%")
    List<Tale> searchByTitleCustom(String keyword);

    List<Tale> findByRatingGreaterThan(Integer rating);
}