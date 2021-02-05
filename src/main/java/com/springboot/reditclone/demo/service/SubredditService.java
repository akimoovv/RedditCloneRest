package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.SubredditDto;
import com.springboot.reditclone.demo.mapper.SubredditMapper;
import com.springboot.reditclone.demo.model.Subreddit;
import com.springboot.reditclone.demo.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SubredditService {

    private final SubredditMapper subredditMapper;

    private final SubredditRepository subredditRepository;


    @Transactional
    public Subreddit save(SubredditDto subredditDto) {

        Subreddit subreddit =  subredditMapper.map(subredditDto);

        subredditRepository.save(subreddit);

        return subreddit;

    }

    @Transactional
    public List<SubredditDto> getAll() {
      return subredditRepository.findAll().stream()
               .map(el -> subredditMapper.mapToDto(el)).collect(Collectors.toList());

    }



}
