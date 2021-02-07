package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.Comment;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {


    Optional<List<Comment>> findByPost(Post post);

    Optional<List<Comment>> findByUser(User user);

}
