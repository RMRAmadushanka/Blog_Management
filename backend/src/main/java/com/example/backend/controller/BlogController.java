package com.example.backend.controller;

import com.example.backend.model.BlogPost;
import com.example.backend.repository.BlogPostRepository;
import com.example.backend.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    private BlogPostService blogPostService;

    @PostMapping
    public Mono<BlogPost> createPost(@RequestBody BlogPost blogPost) {
        return blogPostService.createPost(blogPost);
    }

    @PutMapping("/{id}")
    public Mono<BlogPost> updatePost(@PathVariable Long id,  @RequestBody BlogPost blogPost) {
        return blogPostService.updatePost(blogPost, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePost(@PathVariable Long id) {
        return blogPostService.deletePost(id);
    }

    @GetMapping("/{id}")
    public Mono<BlogPost> getPostById(@PathVariable Long id){
        return blogPostService.getPostById(id);
    }

    @GetMapping
    public Flux<BlogPost> getAllPosts(){
        return blogPostService.getAllPosts();
    }
}
