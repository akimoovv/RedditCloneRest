package com.springboot.reditclone.demo.rest;


import com.springboot.reditclone.demo.dto.PostRequest;
import com.springboot.reditclone.demo.dto.PostResponse;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class PostRestController {


    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {



        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.save(postRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPost(id));
    }



}
