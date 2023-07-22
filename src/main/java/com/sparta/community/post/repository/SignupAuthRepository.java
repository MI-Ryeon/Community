package com.sparta.community.repository;

import com.sparta.community.entity.SignupAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignupAuthRepository extends JpaRepository<SignupAuth, Long> {
    Optional<SignupAuth> findByEmail(String email);
}
