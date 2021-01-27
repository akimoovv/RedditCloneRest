package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;




public interface UserRepository extends JpaRepository<User, Long> {

}
