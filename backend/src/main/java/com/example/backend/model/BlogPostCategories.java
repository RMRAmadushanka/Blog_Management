package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "blogpost_categories")
public class BlogPostCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blogpost_id", referencedColumnName = "id", nullable = false)
    private BlogPost blogPost;

    private String category;

    public BlogPostCategories() {}

    public BlogPostCategories(BlogPost blogPost, String category) {
        this.blogPost = blogPost;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

