package com.example.lab2.service;

import com.example.lab2.model.Tale;
import com.example.lab2.repository.TaleDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaleService {

    private final TaleDao taleDao;

    public TaleService(TaleDao taleDao) {
        this.taleDao = taleDao;
    }

    public List<Tale> getAllTales(String title) {
        if (title != null && !title.isEmpty()) {
            return taleDao.findByTitle(title);
        }
        return taleDao.findAll();
    }

    public Optional<Tale> getTaleById(Integer id) {
        return taleDao.findById(id);
    }

    public Tale createTale(Tale tale) {
        int newId = taleDao.create(tale);
        tale.setId(newId);
        return tale;
    }

    public void updateTale(Integer id, Tale tale) {
        taleDao.update(id, tale);
    }

    public void deleteTale(Integer id) {
        taleDao.delete(id);
    }

    // ТРАНЗАКЦІЙНИЙ МЕТОД (Вимога лаби)
    @Transactional
    public void createBatchTales(List<Tale> tales, boolean failOnPurpose) {
        for (Tale tale : tales) {
            taleDao.create(tale);
        }
        if (failOnPurpose) {
            throw new RuntimeException("Simulated Error for Transaction Rollback check!");
        }
    }
}