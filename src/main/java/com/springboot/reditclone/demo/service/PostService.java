package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.PostRequest;
import com.springboot.reditclone.demo.dto.PostResponse;
import com.springboot.reditclone.demo.exceptions.PostNotFoundException;
import com.springboot.reditclone.demo.exceptions.SpringRedditException;
import com.springboot.reditclone.demo.exceptions.SubredditNotFoundException;
import com.springboot.reditclone.demo.mapper.PostMapper;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.Subreddit;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.repository.PostRepository;
import com.springboot.reditclone.demo.repository.SubredditRepository;
import com.springboot.reditclone.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PostService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public Post save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit hasn't been found"));


        User user = authService.getCurrentUser();


        Post post = postMapper.map(postRequest,user,subreddit);

        postRepository.save(post);

        return post;
    }


    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = getPostMap(id);


        return postMapper.mapToDto(post);
    }


    public List<PostResponse> getAllPosts() {

        return postRepository.findAll()
                .stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    public List<PostResponse> getPostsBySubreddit(Long id) {

        Subreddit subreddit = subredditRepository.getOne(id);

        return subreddit.getPosts().stream()
                .map(postMapper::mapToDto).collect(Collectors.toList());


    }

    public List<PostResponse> getPostsByUsername(String username) {


        return getPostMapsByUsername(username).stream()
                .map(postMapper::mapToDto).collect(Collectors.toList());

    }


    public Post getPostMap(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
    }


    public List<Post> getPostMapsByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringRedditException("user hasn't been found"));

        return postRepository.findByUser(user);

    }


}