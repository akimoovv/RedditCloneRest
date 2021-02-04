package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findByUsername(String username);
}
