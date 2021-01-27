package com.springboot.reditclone.demo.repository;

import com.springboot.reditclone.demo.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;




public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

}
