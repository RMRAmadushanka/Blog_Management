package com.example.backend.service;

import com.example.backend.model.BlogPost;
import com.example.backend.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    public Mono<BlogPost> createPost(BlogPost blogPost, MultipartFile imageFile) {
        blogPost.setCreationDate(new Date());
        if (imageFile != null) {
            try {
                String imageUrl = imageUploadService.uploadImage(imageFile);
                blogPost.setImageUrl(imageUrl);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return Mono.just(blogPostRepository.save(blogPost));
    }

    public Mono<BlogPost> updatePost(BlogPost blogPost, Long id, MultipartFile imageFile) {
        Optional<BlogPost> existingPost = blogPostRepository.findById(id);
        if(existingPost.isPresent()){
            BlogPost updatedPost = existingPost.get();
            updatedPost.setTitle(blogPost.getTitle());
            updatedPost.setContent(blogPost.getContent());

            if (imageFile != null) {
                try {
                    imageUploadService.deleteImage(updatedPost.getImageUrl());

                    String imageUrl = imageUploadService.uploadImage(imageFile);
                    updatedPost.setImageUrl(imageUrl);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

            return Mono.just(blogPostRepository.save(updatedPost));
        }
        return Mono.empty();
    }

    public Mono<Void> deletePost(Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {

            String imageUrl = blogPost.get().getImageUrl();
            if (imageUrl != null) {
                imageUploadService.deleteImage(imageUrl);
            }
            blogPostRepository.deleteById(id);
        }
        return Mono.empty();
    }

    public Mono<BlogPost> getPostById(Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.isPresent() ? Mono.just(blogPost.get()) : Mono.empty();
    }

    public Flux<BlogPost> getAllPosts() {
        return Flux.fromIterable(blogPostRepository.findAll());
    }
}
