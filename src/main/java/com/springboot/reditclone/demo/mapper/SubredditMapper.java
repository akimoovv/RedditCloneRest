package com.springboot.reditclone.demo.mapper;

import com.springboot.reditclone.demo.dto.SubredditDto;
import com.springboot.reditclone.demo.model.Subreddit;
import org.springframework.stereotype.Component;

@Component
public class SubredditMapper {



    public Subreddit map(SubredditDto subredditDto) {


        return Subreddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription()).build();

    }


    public SubredditDto mapToDto(Subreddit subreddit) {

        return SubredditDto.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();


    }
}