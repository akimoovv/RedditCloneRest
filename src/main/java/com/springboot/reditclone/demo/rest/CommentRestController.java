package com.springboot.reditclone.demo.rest;


import com.springboot.reditclone.demo.dto.CommentsDto;
import com.springboot.reditclone.demo.model.Comment;
import com.springboot.reditclone.demo.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentRestController {

    private final CommentService commentService;


    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentsDto commentsDto) {


       Comment comment =  commentService.save(commentsDto);


        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@PathVariable String userName){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForUser(userName));
    }



}
