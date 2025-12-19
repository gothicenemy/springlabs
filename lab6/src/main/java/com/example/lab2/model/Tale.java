package com.example.lab2.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tales")
@Data
@NoArgsConstructor
@NamedQuery(
        name = "Tale.findByRatingGreaterThan",
        query = "SELECT t FROM Tale t WHERE t.rating > ?1"
)
public class Tale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String content;

    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public Tale(String title, String content, Integer rating) {
        this.title = title;
        this.content = content;
        this.rating = rating;
    }
}