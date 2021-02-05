package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

}
