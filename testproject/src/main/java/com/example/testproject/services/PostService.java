package com.example.testproject.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.testproject.entities.PostDto;
import com.example.testproject.entities.PostEntity;
import com.example.testproject.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto createPost(PostDto postDto) {
        PostEntity post = new PostEntity();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post = postRepository.save(post);
        postDto.setId(post.getId());
        return postDto;
    }

    public List<PostEntity> findAllPosts() {
        return postRepository.findAll();
    }

    public Optional<PostEntity> findPostById(Long id) {
        return postRepository.findById(id);
    }

    public PostDto updatePost(Long id, PostDto postDto) {
        PostEntity post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post = postRepository.save(post);
        return postDto;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
