package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

}
