package com.springboot.reditclone.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SubredditDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;


    public SubredditDto() {
    }

}
