package com.springboot.reditclone.demo.rest;


import com.springboot.reditclone.demo.dto.SubredditDto;
import com.springboot.reditclone.demo.model.Subreddit;
import com.springboot.reditclone.demo.service.SubredditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@Slf4j
public class SubredditRestController {


    private final SubredditService subredditService;

    public SubredditRestController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @PostMapping
    public ResponseEntity<Subreddit>  createSubreddit(@RequestBody SubredditDto subredditDto) {



        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.save(subredditDto));
    }


    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubReddits() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.getAll());
    }

}
