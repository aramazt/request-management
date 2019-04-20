package com.prime.task.controller;

import com.prime.task.model.Request;
import com.prime.task.model.User;
import com.prime.task.repository.UserRepository;
import com.prime.task.service.Mapper;
import com.prime.task.service.RequestManagement;
import com.prime.task.utils.UserStatus;
import com.prime.task.viewModel.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestManagement requestManagement;

    @Autowired
    private Mapper mapper;

    @GetMapping
    List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable Long id) {
        return userRepository.getOne(id);
    }

    @PostMapping("/create")
    User createUser(@RequestBody @Valid UserViewModel viewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Invalid request data");
        }

        User user = mapper.getUserFrom(viewModel);

        userRepository.save(user);

        return user;
    }

    @PutMapping("/update")
    User updateUser(@RequestBody @Valid UserViewModel viewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Invalid request data");
        }

        User updatedUser = mapper.getUserFrom(viewModel);

        userRepository.save(updatedUser);

        // user goes OFFLINE
        if (updatedUser.getStatus().equals(UserStatus.OFFLINE)) {
            requestManagement.distributeOpenedRequestsOf(updatedUser);
        }

        return updatedUser;
    }

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).get();

        requestManagement.deleteCompletedRequests(user);

        requestManagement.distributeOpenedRequestsOf(user);

        userRepository.deleteById(id);
    }

}
