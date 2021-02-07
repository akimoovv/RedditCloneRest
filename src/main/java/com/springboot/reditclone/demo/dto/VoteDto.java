package com.springboot.reditclone.demo.dto;


import com.springboot.reditclone.demo.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VoteDto {

    private VoteType voteType;
    private Long postId;

    public VoteDto(VoteDto voteDto) {

    }
}
