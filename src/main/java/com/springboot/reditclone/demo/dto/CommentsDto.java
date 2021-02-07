package com.springboot.reditclone.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentsDto {
    private Long id;
    private Long postId;
    private String text;
    private String userName;

    public CommentsDto() {
    }
}
