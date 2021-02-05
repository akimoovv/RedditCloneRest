package com.springboot.reditclone.demo.mapper;

import com.springboot.reditclone.demo.dto.PostRequest;
import com.springboot.reditclone.demo.dto.PostResponse;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.Subreddit;
import com.springboot.reditclone.demo.model.User;

public class PostMapper  {



    public Post map(PostRequest postRequest, User user, Subreddit subreddit) {
        return  Post.builder().postName(postRequest.getPostName())
                .url(postRequest.getUrl())
                .description(postRequest.getDescription())
                .subreddit(subreddit)
                .user(user).build();
    }

    public PostResponse mapToDto(Post post) {
        return PostResponse.builder()
                .id(post.getPostId())
                .postName(post.getPostName())
                .url(post.getUrl())
                .description(post.getDescription())
                .userName(post.getUser().getUsername())
                .subredditName(post.getSubreddit().getName())
                .voteCount(post.getVoteCount())
                .upVote(true)
                .downVote(true)
                .build();

    }

}
