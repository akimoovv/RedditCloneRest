package com.springboot.reditclone.demo.rest;


import com.springboot.reditclone.demo.dto.PostRequest;
import com.springboot.reditclone.demo.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/posts")
public class PostRestController {


    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    public ResponseEntity createPost(@RequestBody PostRequest postRequest) {



        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.save(postRequest));
    }

}
