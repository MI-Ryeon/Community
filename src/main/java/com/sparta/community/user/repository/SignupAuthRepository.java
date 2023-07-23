package com.sparta.community.user.repository;

import com.sparta.community.user.entity.SignupAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignupAuthRepository extends JpaRepository<SignupAuth, Long> {
    Optional<SignupAuth> findByEmail(String email);

    Optional<SignupAuth> findById(Long id);
}
