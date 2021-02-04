package com.springboot.reditclone.demo.mapper;


import com.springboot.reditclone.demo.dto.SubredditDto;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {


    @Mapping(source = "posts", target = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto subRedditToSubredditDto(Subreddit subreddit);


    default Integer mapPosts(List<Post> numberOfPosts) {return numberOfPosts.size();}


    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit subredditDtoToSubreddit(SubredditDto subredditDto);

}
