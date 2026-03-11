package com.example.be12th.domain.user.domain.repository;

import com.example.be12th.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
