package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.VoteDto;
import com.springboot.reditclone.demo.exceptions.SpringRedditException;
import com.springboot.reditclone.demo.mapper.PostMapper;
import com.springboot.reditclone.demo.mapper.VoteMapper;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.Vote;
import com.springboot.reditclone.demo.model.VoteType;
import com.springboot.reditclone.demo.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final PostService postService;

    private final AuthService authService;

    private final VoteRepository voteRepository;

    private final PostMapper postMapper;

    private final VoteMapper voteMapper;






    public void vote(VoteDto voteDto) {

        Post post = postService.getPostMap(voteDto.getPostId());

        Optional<Vote> vote =
                voteRepository.findTopByPostAndUser(post, post.getUser());

        if (vote.isPresent() &&
                vote.get().getVoteType()
                        .equals(voteDto.getVoteType())) {

            throw  new SpringRedditException("");
        }

        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {

            post.setVoteCount(post.getVoteCount() + 1);

        } else if (VoteType.DOWNVOTE.equals(voteDto.getVoteType())) {

            post.setVoteCount(post.getVoteCount() - 1);
        }

        postService.saveMap(post);
        voteRepository.save(voteMapper.map(voteDto));
    }


}
