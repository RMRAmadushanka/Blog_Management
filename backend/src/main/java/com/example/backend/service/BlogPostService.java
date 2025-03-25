package com.example.backend.service;

import com.example.backend.model.BlogPost;
import com.example.backend.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Optional;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    public Mono<BlogPost> createPost(BlogPost blogPost){
        blogPost.setCreationDate(new Date());
        return Mono.just(blogPostRepository.save(blogPost));
    }

    public Mono<BlogPost> updatePost(BlogPost blogPost, Long id){
        Optional<BlogPost> existingPost = blogPostRepository.findById(id);
        if(existingPost.isPresent()){
            BlogPost updatedPost = existingPost.get();
            updatedPost.setTitle(blogPost.getTitle());
            updatedPost.setContent(blogPost.getContent());
            return Mono.just(blogPostRepository.save(updatedPost));
        }
        return Mono.empty();
    }

    public Mono<Void> deletePost(Long id){
        blogPostRepository.deleteById(id);
        return Mono.empty();
    }

    public Mono<BlogPost> getPostById(Long id){
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.isPresent() ? Mono.just(blogPost.get()) : Mono.empty();
    }

    public Flux<BlogPost> getAllPosts(){
        return Flux.fromIterable(blogPostRepository.findAll());
    }
}
