package com.example.lab2.repository;

import com.example.lab2.model.Tale;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TaleRepository {
    private final List<Tale> db = new ArrayList<>();

    public TaleRepository() {
        db.add(new Tale(1, "Котигорошко", "Дуже епічна битва зі змієм, але сюжет трохи затягнутий. Рекомендую!", "Вітя Мітя", 85));
        db.add(new Tale(2, "Колобок", "Сумний фінал, головного героя з'їли. Не для вразливих.", "Скібіді туалет", 40));
        db.add(new Tale(3, "Рукавичка", "Проблеми перенаселення розкриті ідеально. Життєво.", "Ліл Пінч", 95));
    }

    public List<Tale> findAll() { return db; }

    public List<Tale> search(String query) {
        if (query == null || query.isEmpty()) return db;
        return db.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Tale findById(int id) {
        return db.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public void save(Tale tale) {
        if (tale.getId() == 0) {
            tale.setId(db.isEmpty() ? 1 : db.get(db.size() - 1).getId() + 1);
            db.add(tale);
        } else {
            Tale existing = findById(tale.getId());
            if (existing != null) {
                existing.setTitle(tale.getTitle());
                existing.setContent(tale.getContent());
                existing.setAuthor(tale.getAuthor());
                existing.setRating(tale.getRating());
            }
        }
    }

    public void delete(int id) {
        db.removeIf(t -> t.getId() == id);
    }
}