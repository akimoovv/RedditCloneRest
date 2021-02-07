package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUser(Post post, User user);
}
