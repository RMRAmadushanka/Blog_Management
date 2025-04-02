package com.example.backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "blogpost_id", nullable = false)
    private BlogPost blogPost;

    public Comment() {}

    public Comment(String author, String content, Date creationDate, BlogPost blogPost) {
        this.author = author;
        this.content = content;
        this.creationDate = creationDate;
        this.blogPost = blogPost;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public BlogPost getBlogPost() { return blogPost; }
    public void setBlogPost(BlogPost blogPost) { this.blogPost = blogPost; }
}

