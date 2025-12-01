package com.example.lab2.model;

public class Tale {
    private int id;
    private String title;
    private String content;
    private String author;
    private int rating;

    public Tale() {}

    public Tale(int id, String title, String content, String author, int rating) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.rating = rating;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}