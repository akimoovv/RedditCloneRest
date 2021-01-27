package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

}
