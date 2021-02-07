package com.springboot.reditclone.demo.mapper;


import com.springboot.reditclone.demo.dto.PostRequest;
import com.springboot.reditclone.demo.dto.PostResponse;
import com.springboot.reditclone.demo.model.*;
import com.springboot.reditclone.demo.repository.VoteRepository;
import com.springboot.reditclone.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class PostMapper  {


    private final AuthService authService;
    private final VoteRepository voteRepository;


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
                .upVote(isUpvoted(post))
                .downVote(isDownvoted(post))
                .build();

    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {

            return voteRepository.findTopByPostAndUser(post,
                    authService.getCurrentUser()).filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }

        return false;
    }


    public boolean isUpvoted(Post post) {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    public boolean isDownvoted(Post post) {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }

}










