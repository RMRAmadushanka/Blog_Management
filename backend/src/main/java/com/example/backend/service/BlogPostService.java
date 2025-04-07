package com.example.backend.service;

import com.example.backend.dto.BlogPostDTO;
import com.example.backend.model.BlogPost;
import com.example.backend.model.BlogPostCategories;
import com.example.backend.model.BlogPostTags;
import com.example.backend.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    public Mono<BlogPostDTO> createPost(BlogPost blogPost, MultipartFile imageFile) {
        blogPost.setCreationDate(new Date());
        if (imageFile != null) {
            try {
                String imageUrl = imageUploadService.uploadImage(imageFile);
                blogPost.setImageUrl(imageUrl);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        BlogPost savedBlogPost = blogPostRepository.save(blogPost);
        BlogPostDTO blogPostDTO = new BlogPostDTO(
                savedBlogPost.getId(),
                savedBlogPost.getTitle(),
                savedBlogPost.getAuthor(),
                savedBlogPost.getCreationDate(),
                savedBlogPost.getImageUrl(),
                savedBlogPost.getViews(),
                savedBlogPost.getLikes()

        );
        return Mono.just(blogPostDTO);
    }

    public Mono<BlogPostDTO> updatePost(BlogPost blogPost, Long id, MultipartFile imageFile) {
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
            BlogPost savedUpdatedPost  = blogPostRepository.save(updatedPost);
            BlogPostDTO blogPostDTO = new BlogPostDTO(
                    savedUpdatedPost.getId(),
                    savedUpdatedPost.getTitle(),
                    savedUpdatedPost.getAuthor(),
                    savedUpdatedPost.getCreationDate(),
                    savedUpdatedPost.getImageUrl(),
                    savedUpdatedPost.getViews(),
                    savedUpdatedPost.getLikes()
            );
            return Mono.just(blogPostDTO);
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

    public Flux<BlogPostDTO> getAllPosts() {
        return Flux.fromIterable(blogPostRepository.findAll())
                .map(post -> new BlogPostDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getAuthor(),
                        post.getCreationDate(),
                        post.getImageUrl(),
                        post.getViews(),
                        post.getLikes()
                ));
    }

    public Flux<BlogPostDTO> getPopularPosts(int minLikes, int minViews) {
        return Flux.fromIterable(blogPostRepository.findAll())
                .filter(post -> post.getLikes() >= minLikes && post.getViews() >= minViews)
                .map(post -> new BlogPostDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getAuthor(),
                        post.getCreationDate(),
                        post.getImageUrl(),
                        post.getViews(),
                        post.getLikes()
                ));
    }

    public Flux<BlogPostDTO> getPostByTags(Set<String> tags) {
        List<BlogPost> allPosts = blogPostRepository.findAll();
        return Flux.fromIterable(allPosts)
                .filter(post -> {
                    Set<String> postTags = post.getTags().stream()
                            .map(BlogPostTags::getTag)
                            .collect(Collectors.toSet());
                    return postTags.stream().anyMatch(tags::contains);
                })
                .map(post -> new BlogPostDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getAuthor(),
                        post.getCreationDate(),
                        post.getImageUrl(),
                        post.getViews(),
                        post.getLikes()
                ));
    }

    public Flux<BlogPostDTO> getMostCommentedPosts(int minComments){
        return Flux.fromIterable(blogPostRepository.findAll())
                .filter(post -> post.getComments().size() >= minComments)
                .map(post -> new BlogPostDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getAuthor(),
                        post.getCreationDate(),
                        post.getImageUrl(),
                        post.getViews(),
                        post.getLikes()
                ));

    }


    public Flux<BlogPostDTO> getPostsByCategory(String category){
        return Flux.fromIterable(blogPostRepository.findAll())
                .filter(post -> post.getCategories().stream()
                        .map(categoryName -> categoryName.getCategory())
                        .anyMatch(name -> name.equals(category)))
                .sort((post1,post2)-> Integer.compare(post2.getLikes(),post1.getLikes()))
                .map(post -> new BlogPostDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getAuthor(),
                        post.getCreationDate(),
                        post.getImageUrl(),
                        post.getViews(),
                        post.getLikes()
                ));
    }

    public Flux<String> deleteOldPosts(int days) {
        LocalDate todayMinusDays = LocalDate.now().minusDays(days);
        LocalDateTime thresholdDateTime = todayMinusDays.atStartOfDay();
        Date thresholdDate = Timestamp.valueOf(thresholdDateTime);

        return Flux.fromIterable(blogPostRepository.findAll())
                .filter(post -> post.getCreationDate().before(thresholdDate))
                .flatMap(post -> {
                    blogPostRepository.deleteById(post.getId());
                    return Mono.just("Deleted Post ID: " + post.getId());
                });
    }


}
