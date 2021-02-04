package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.PostRequest;
import com.springboot.reditclone.demo.exceptions.SpringRedditException;
import com.springboot.reditclone.demo.exceptions.SubredditNotFoundException;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.Subreddit;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.repository.PostRepository;
import com.springboot.reditclone.demo.repository.SubredditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostRepository postRepository;

    public PostService(SubredditRepository subredditRepository, AuthService authService, PostRepository postRepository) {
        this.subredditRepository = subredditRepository;
        this.authService = authService;
        this.postRepository = postRepository;
    }

    public Post save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByUsername(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit hasn't been found"));


        User user = authService.getCurrentUser();


        Post post = Post.builder().postName(postRequest.getPostName())
                .url(postRequest.getUrl())
                .description(postRequest.getDescription())
                .subreddit(subreddit)
                .user(user).build();

        postRepository.save(post);

        return post;
    }



}
