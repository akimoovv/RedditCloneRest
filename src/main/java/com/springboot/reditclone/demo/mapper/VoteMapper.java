package com.springboot.reditclone.demo.mapper;


import com.springboot.reditclone.demo.dto.VoteDto;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.model.Vote;
import com.springboot.reditclone.demo.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class VoteMapper {

    private final PostService postService;


    public Vote map(VoteDto voteDto) {

        Post post = postService.getPostMap(voteDto.getPostId());

        User user = post.getUser();

        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(user)
                .build();
    }


    public VoteDto mapToDto(Vote vote) {

        return VoteDto.builder()
                .postId(vote.getPost().getPostId())
                .voteType(vote.getVoteType())
                .build();
    }
}
