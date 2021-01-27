package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;




public interface VoteRepository extends JpaRepository<Vote, Long> {

}
