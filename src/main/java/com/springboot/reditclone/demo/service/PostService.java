package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.PostRequest;
import com.springboot.reditclone.demo.dto.PostResponse;
import com.springboot.reditclone.demo.exceptions.PostNotFoundException;
import com.springboot.reditclone.demo.exceptions.SubredditNotFoundException;
import com.springboot.reditclone.demo.mapper.PostMapper;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.Subreddit;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.repository.PostRepository;
import com.springboot.reditclone.demo.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PostService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Post save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByUsername(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit hasn't been found"));


        User user = authService.getCurrentUser();


        Post post = postMapper.map(postRequest,user,subreddit);

        postRepository.save(post);

        return post;
    }


    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));


        return postMapper.mapToDto(post);
    }



}
