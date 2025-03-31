package com.example.backend.dto;

import java.util.Date;

public class BlogPostDTO {

    private Long id;
    private String title;
    private String author;
    private Date creationDate;
    private String imageUrl;
    private int views;
    private int likes;

    public BlogPostDTO(Long id, String title, String author, Date creationDate, String imageUrl, int views, int likes) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.imageUrl = imageUrl;
        this.views = views;
        this.likes = likes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }
}
