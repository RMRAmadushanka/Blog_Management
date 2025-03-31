package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "blogpost_tags")
public class BlogPostTags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blogpost_id", referencedColumnName = "id", nullable = false)
    private BlogPost blogPost;

    private String tag;

    public BlogPostTags() {}

    public BlogPostTags(BlogPost blogPost, String tag) {
        this.blogPost = blogPost;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
