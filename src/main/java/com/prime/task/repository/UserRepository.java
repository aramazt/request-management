package com.prime.task.repository;

import com.prime.task.model.User;
import com.prime.task.utils.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByStatus(UserStatus status);
}
