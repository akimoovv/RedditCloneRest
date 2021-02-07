package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.CommentsDto;
import com.springboot.reditclone.demo.exceptions.SpringRedditException;
import com.springboot.reditclone.demo.mapper.CommentMapper;
import com.springboot.reditclone.demo.mapper.PostMapper;
import com.springboot.reditclone.demo.model.Comment;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final PostService postService;

    private final PostMapper postMapper;

    private final AuthService authService;


    public Comment save(CommentsDto commentsDto) {

        return commentRepository.save(commentMapper.map(commentsDto));

    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {

        Post post = postService.getPostMap(postId);

        List<Comment> comments = commentRepository.findByPost(post)
                .orElseThrow(() -> new SpringRedditException("Post hasn't been found"));


        return comments.stream().map(commentMapper::mapToDto)
                .collect(Collectors.toList());

    }

    public List<CommentsDto> getAllCommentsForUser(String username) {

        User user = authService.getUserByName(username);


        List<Comment> comments = commentRepository.findByUser(user)
                .orElseThrow(() -> new SpringRedditException("comments hasn't been found"));

        return comments.stream().map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }


}
