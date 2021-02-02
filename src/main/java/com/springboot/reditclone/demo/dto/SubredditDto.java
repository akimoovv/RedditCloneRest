package com.springboot.reditclone.demo.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubredditDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;


    public SubredditDto() {
    }

    public SubredditDto(Long id, String name, String description, Integer numberOfPosts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfPosts = numberOfPosts;
    }
}
