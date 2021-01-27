package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PostRepository extends JpaRepository<Post, Long> {

}
