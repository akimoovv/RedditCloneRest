package com.springboot.reditclone.demo.repository;


import com.springboot.reditclone.demo.model.RefreshToken;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

}
