package com.example.lab2.repository;

import com.example.lab2.model.Tale;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository("jdbcTemplateDao")
public class JdbcTemplateTaleDao implements TaleDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTaleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Tale> taleRowMapper = (rs, rowNum) -> new Tale(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getString("author"),
            rs.getInt("rating")
    );

    @Override
    public int create(Tale tale) {
        String sql = "INSERT INTO tales (title, content, author, rating) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tale.getTitle());
            ps.setString(2, tale.getContent());
            ps.setString(3, tale.getAuthor());
            ps.setInt(4, tale.getRating());
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : 0;
    }

    @Override
    public Optional<Tale> findById(int id) {
        String sql = "SELECT * FROM tales WHERE id = ?";
        return jdbcTemplate.query(sql, taleRowMapper, id).stream().findFirst();
    }

    @Override
    public List<Tale> findAll() {
        return jdbcTemplate.query("SELECT * FROM tales", taleRowMapper);
    }

    @Override
    public List<Tale> findByTitle(String title) {
        String sql = "SELECT * FROM tales WHERE lower(title) LIKE lower(?)";
        return jdbcTemplate.query(sql, taleRowMapper, "%" + title + "%");
    }

    @Override
    public void update(int id, Tale tale) {
        String sql = "UPDATE tales SET title=?, content=?, author=?, rating=? WHERE id=?";
        jdbcTemplate.update(sql, tale.getTitle(), tale.getContent(), tale.getAuthor(), tale.getRating(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM tales WHERE id=?", id);
    }
}