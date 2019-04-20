package com.prime.task.service;

import com.prime.task.model.Request;
import com.prime.task.model.User;
import com.prime.task.repository.RequestRepository;
import com.prime.task.repository.UserRepository;
import com.prime.task.viewModel.RequestViewModel;
import com.prime.task.viewModel.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This component converts frontend model into server-side model
 */
@Component
public class Mapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    public User getUserFrom(UserViewModel viewModel) {

        User user;
        if (viewModel.id == null) {
            // on create operation
            user = new User(viewModel.login, viewModel.fullname, viewModel.status);
        } else {
            // on update operation
            user = userRepository.findById(viewModel.id).get();
            user.setFullname(viewModel.fullname);
            user.setLogin(viewModel.login);
            user.setStatus(viewModel.status);
        }

        return user;
    }

    public Request getRequestFrom(RequestViewModel viewModel) {

        Request request;
        if (viewModel.id == null) {
            // on create operation
            User responsible = userRepository.findById(viewModel.responsibleId).get();
            request = new Request(viewModel.name, viewModel.description, responsible);

        } else {
            // on update operation
            request = requestRepository.findById(viewModel.id).get();
            request.setName(viewModel.name);
            request.setDescription(viewModel.description);
            request.setStatus(viewModel.status);
            request.setPrevResponsible(viewModel.prevResponsible);
        }

        return request;
    }

}
