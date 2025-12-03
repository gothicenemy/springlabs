package com.example.lab2.service;

import com.example.lab2.model.Tale;
import com.example.lab2.repository.TaleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class TaleService {

    private final TaleRepository repository;

    public TaleService(TaleRepository repository) {
        this.repository = repository;
    }

    public Page<Tale> getAllTales(String title, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id"));
        if (title != null && !title.isEmpty()) {
            return repository.findByTitleContainingIgnoreCase(title, pageable);
        }
        return repository.findAll(pageable);
    }

    public Optional<Tale> getTaleById(Integer id) {
        return repository.findById(id);
    }

    public Tale createTale(Tale tale) {
        return repository.save(tale);
    }

    public Optional<Tale> updateTale(Integer id, Tale taleDetails) {
        return repository.findById(id).map(existingTale -> {
            existingTale.setTitle(taleDetails.getTitle());
            existingTale.setAuthor(taleDetails.getAuthor());
            existingTale.setContent(taleDetails.getContent());
            existingTale.setRating(taleDetails.getRating());
            return repository.save(existingTale);
        });
    }

    public Optional<Tale> patchTale(Integer id, Map<String, Object> updates) {
        Optional<Tale> taleOptional = repository.findById(id);
        if (taleOptional.isEmpty()) return Optional.empty();

        Tale tale = taleOptional.get();
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Tale.class, key);
            if (field != null) {
                field.setAccessible(true);
                if (key.equals("rating") && value instanceof Number) {
                    ReflectionUtils.setField(field, tale, ((Number) value).intValue());
                } else {
                    ReflectionUtils.setField(field, tale, value);
                }
            }
        });
        return Optional.of(repository.save(tale));
    }

    public boolean deleteTale(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}