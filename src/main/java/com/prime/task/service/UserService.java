package com.prime.task.service;

import com.prime.task.model.User;
import com.prime.task.repository.UserRepository;
import com.prime.task.utils.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(Long id) {
        if (id == null)
            return null;

        return repository.findById(id).get();
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public List<User> getUsersByStatus(UserStatus status) {
        if (status == null)
            return null;

        return repository.findAllByStatus(status);
    }

    public User save(User user) {

        return repository.save(user);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
