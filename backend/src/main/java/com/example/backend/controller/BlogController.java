package com.example.backend.controller;

import com.example.backend.dto.BlogPostDTO;
import com.example.backend.model.BlogPost;
import com.example.backend.repository.BlogPostRepository;
import com.example.backend.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;


@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogPostService blogPostService;

    @PostMapping
    public Mono<BlogPostDTO> createPost(@RequestParam("title") String title,
                                     @RequestParam("content") String content,
                                     @RequestParam("author") String author,
                                     @RequestParam("image") MultipartFile image) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(title);
        blogPost.setContent(content);
        blogPost.setAuthor(author);
        return blogPostService.createPost(blogPost, image);
    }

    @PutMapping("/{id}")
    public Mono<BlogPostDTO> updatePost(@PathVariable Long id,
                                     @RequestParam("title") String title,
                                     @RequestParam("content") String content,
                                     @RequestParam("author") String author,
                                     @RequestParam(value = "image", required = false) MultipartFile image) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(title);
        blogPost.setContent(content);
        blogPost.setAuthor(author);
        return blogPostService.updatePost(blogPost, id, image);
    }


    @DeleteMapping("/{id}")
    public Mono<Void> deletePost(@PathVariable Long id) {
        return blogPostService.deletePost(id);
    }

    @GetMapping("/{id}")
    public Mono<BlogPost> getPostById(@PathVariable Long id) {
        return blogPostService.getPostById(id);
    }

    @GetMapping
    public Flux<BlogPostDTO> getAllPosts() {
        return blogPostService.getAllPosts();
    }

    @GetMapping("/popular")
    public Flux<BlogPostDTO> getPopularPosts(@RequestParam("minLikes") int minLikes, @RequestParam("minViews") int minViews) {
        return blogPostService.getPopularPosts(minLikes, minViews);
    }

    @GetMapping("/by-tags")
    public Flux<BlogPostDTO> getPostsByTags(@RequestParam Set<String> tags) {
        return blogPostService.getPostByTags(tags);
    }

    @GetMapping("/most-comment")
    public Flux<BlogPostDTO> getMostCommentedPosts(@RequestParam int minComments) {
        return blogPostService.getMostCommentedPosts(minComments);
    }

    @GetMapping("/categories")
    public Flux<BlogPostDTO> getPostsByCategory(@RequestParam String category) {
        return blogPostService.getPostsByCategory(category);
    }
}
