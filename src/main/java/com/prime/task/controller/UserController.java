package com.prime.task.controller;

import com.prime.task.model.User;
import com.prime.task.service.Mapper;
import com.prime.task.service.automation.RequestManagement;
import com.prime.task.service.UserService;
import com.prime.task.utils.UserStatus;
import com.prime.task.view.model.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestManagement requestManagement;

    @Autowired
    private Mapper mapper;

    @GetMapping
    List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    User createUser(@RequestBody @Valid UserViewModel viewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Invalid request data");
        }

        User user = mapper.getUserFrom(viewModel);

        userService.save(user);

        return user;
    }

    @PutMapping("/update")
    User updateUser(@RequestBody @Valid UserViewModel viewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Invalid request data");
        }

        User updatedUser = mapper.getUserFrom(viewModel);

        userService.save(updatedUser);

        // user goes OFFLINE
        if (updatedUser.getStatus().equals(UserStatus.OFFLINE)) {
            requestManagement.distributeOpenedRequestsOf(updatedUser);
        }

        return updatedUser;
    }

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable Long id) {
        User user = userService.getUser(id);

        requestManagement.deleteCompletedRequests(user);

        requestManagement.distributeOpenedRequestsOf(user);

        userService.deleteById(id);
    }

}
