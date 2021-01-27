package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<Comment, Long> {

}
