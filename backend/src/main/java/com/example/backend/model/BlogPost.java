package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "blogpost")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    private String imageUrl;

    private int views = 0;
    private int likes = 0;

    // One-to-Many Relationship with BlogPostTags
    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<BlogPostTags> tags;

    // One-to-Many Relationship with BlogPostCategories
    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<BlogPostCategories> categories;

    // One-to-Many Relationship with Comments
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public BlogPost() {}

    public BlogPost(Long id, String title, String content, String author, Date creationDate, String imageUrl, int views, int likes, List<BlogPostTags> tags, List<BlogPostCategories> categories, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.creationDate = creationDate;
        this.imageUrl = imageUrl;
        this.views = views;
        this.likes = likes;
        this.tags = tags;
        this.categories = categories;
        this.comments = comments;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

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

    public List<BlogPostTags> getTags() { return tags; }
    public void setTags(List<BlogPostTags> tags) { this.tags = tags; }

    public List<BlogPostCategories> getCategories() { return categories; }
    public void setCategories(List<BlogPostCategories> categories) { this.categories = categories; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}

