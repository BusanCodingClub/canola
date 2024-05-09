package com.example.testproject.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.testproject.entities.PostDto;
import com.example.testproject.entities.PostEntity;
import com.example.testproject.services.PostService;

@Controller
public class BoardController {

    @Autowired
    private PostService postService;
    
    @GetMapping("/board/list")
    public String listPost(Model model) {
        // List<PostDto> postDtoList = new ArrayList<PostDto>();
        // postDtoList.add(new PostDto(1L, "첫 번째 게시글", "첫 번째 게시글 내용입니다."));
        // postDtoList.add(new PostDto(2L, "두 번째 게시글", "두 번째 게시글 내용입니다."));
        // postDtoList.add(new PostDto(3L, "세 번째 게시글", "세 번째 게시글 내용입니다."));

        List<PostEntity> postDtoList = postService.findAllPosts();

        model.addAttribute("posts", postDtoList);
        return "board/list.html";
    }

    @GetMapping("/board/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        PostDto postDto = postService.findPostById(id)
            .map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent()))
            .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        model.addAttribute("post", postDto);
        return "board/view.html";
    }

    @GetMapping("/board/new")
    public String newPost(Model model) {
        model.addAttribute("post", new PostDto());
        return "board/new.html";
    }

    @PostMapping("/board/new")
    public String newPost(@Validated PostDto postDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/new.html";
        }
        postService.createPost(postDto);
        return "redirect:/board/list";
    }

    @GetMapping("/board/{id}/edit")
    public String editPost(@PathVariable Long id, Model model) {
        PostDto postDto = postService.findPostById(id)
            .map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent()))
            .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        model.addAttribute("post", postDto);
        return "board/edit.html";
    }

    @PostMapping("/board/{id}/edit")
    public String editPost(@PathVariable Long id, @Validated PostDto postDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/edit.html";
        }
        postService.updatePost(id, postDto);
        return "redirect:/board/list";
    }

    @GetMapping("/board/{id}/delete")
    public String deletePost(@PathVariable Long id, Model model) {
        PostDto postDto = postService.findPostById(id)
            .map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent()))
            .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        model.addAttribute("post", postDto);
        return "board/delete.html";
    }

    @PostMapping("/board/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/board/list";
    }


}
