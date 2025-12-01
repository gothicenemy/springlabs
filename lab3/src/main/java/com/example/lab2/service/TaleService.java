package com.example.lab2.service;

import com.example.lab2.model.Tale;
import com.example.lab2.repository.TaleRepository;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaleService {

    private final TaleRepository repository;

    private ObjectFactory<String> timeFactory;

    @Autowired
    public TaleService(TaleRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setStatusMessage(ObjectFactory<String> timeFactory) {
        this.timeFactory = timeFactory;
    }

    public List<Tale> getAll() { return repository.findAll(); }
    public List<Tale> search(String query) { return repository.search(query); }
    public Tale getById(int id) { return repository.findById(id); }
    public void save(Tale tale) { repository.save(tale); }
    public void remove(int id) { repository.delete(id); }

    public String getStatus() {
        return timeFactory.getObject();
    }
}