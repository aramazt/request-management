package com.prime.task.service;

import com.prime.task.model.Request;
import com.prime.task.model.User;
import com.prime.task.view.model.RequestViewModel;
import com.prime.task.view.model.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This component converts frontend model into server-side model
 */
@Component
public class Mapper {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    public User getUserFrom(UserViewModel viewModel) {

        User user;
        if (viewModel.id == null) {
            // on create operation
            user = new User(viewModel.login, viewModel.fullname, viewModel.status);
        } else {
            // on update operation
            user = userService.getUser(viewModel.id);
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
            User responsible = userService.getUser(viewModel.responsibleId);
            request = new Request(viewModel.name, viewModel.description, responsible);

        } else {
            // on update operation
            request = requestService.getRequest(viewModel.id);
            request.setName(viewModel.name);
            request.setDescription(viewModel.description);
            request.setStatus(viewModel.status);
            request.setPrevResponsible(viewModel.prevResponsible);
        }

        return request;
    }

}
