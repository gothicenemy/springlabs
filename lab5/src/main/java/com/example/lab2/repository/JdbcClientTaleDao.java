package com.example.lab2.repository;

import com.example.lab2.model.Tale;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbcClientDao")
@Primary
public class JdbcClientTaleDao implements TaleDao {

    private final JdbcClient jdbcClient;

    public JdbcClientTaleDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public int create(Tale tale) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO tales (title, content, author, rating) VALUES (:title, :content, :author, :rating)";

        jdbcClient.sql(sql)
                .param("title", tale.getTitle())
                .param("content", tale.getContent())
                .param("author", tale.getAuthor())
                .param("rating", tale.getRating())
                .update(keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : 0;
    }

    @Override
    public Optional<Tale> findById(int id) {
        return jdbcClient.sql("SELECT * FROM tales WHERE id = :id")
                .param("id", id)
                .query(Tale.class)
                .optional();
    }

    @Override
    public List<Tale> findAll() {
        return jdbcClient.sql("SELECT * FROM tales")
                .query(Tale.class)
                .list();
    }

    @Override
    public List<Tale> findByTitle(String title) {
        return jdbcClient.sql("SELECT * FROM tales WHERE lower(title) LIKE lower(:title)")
                .param("title", "%" + title + "%")
                .query(Tale.class)
                .list();
    }

    @Override
    public void update(int id, Tale tale) {
        jdbcClient.sql("UPDATE tales SET title=?, content=?, author=?, rating=? WHERE id=?")
                .params(List.of(tale.getTitle(), tale.getContent(), tale.getAuthor(), tale.getRating(), id))
                .update();
    }

    @Override
    public void delete(int id) {
        jdbcClient.sql("DELETE FROM tales WHERE id = :id")
                .param("id", id)
                .update();
    }
}