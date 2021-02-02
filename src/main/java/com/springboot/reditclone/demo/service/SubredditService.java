package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.SubredditDto;
import com.springboot.reditclone.demo.model.Subreddit;
import com.springboot.reditclone.demo.repository.SubredditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;

    public SubredditService(SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }

    @Transactional
    public Subreddit save(SubredditDto subredditDto) {

        Subreddit subreddit =  Subreddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription()).build();

        subredditRepository.save(subreddit);

        return subreddit;

    }

    @Transactional
    public List<SubredditDto> getAll() {
      List<SubredditDto> list =  subredditRepository.findAll().stream()
               .map(el ->
                       SubredditDto.builder().id(el.getId())
                               .numberOfPosts(el.getPosts().size())
                               .description(el.getDescription())
                               .name(el.getName()).build()
               ).collect(Collectors.toList());

      return list;
    }



}
