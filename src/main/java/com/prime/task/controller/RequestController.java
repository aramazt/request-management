package com.prime.task.controller;

import com.prime.task.model.Request;
import com.prime.task.service.Mapper;
import com.prime.task.service.RequestService;
import com.prime.task.utils.RequestStatus;
import com.prime.task.view.model.RequestViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private Mapper mapper;

    @GetMapping
    List<Request> getAll(@RequestParam RequestStatus status) {
        if (status != null)
            return requestService.getAllByStatus(status);

        return requestService.getAllRequests();
    }

    @GetMapping("/forUser/{id}")
    List<Request> getRequestsForUser(@PathVariable Long id) {

        return requestService.getRequestsByResponsibleId(id);
    }

    @PostMapping("/save")
    Request saveRequest(@RequestBody @Valid RequestViewModel viewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Invalid request data");
        }

        Request request = mapper.getRequestFrom(viewModel);

        requestService.save(request);

        return request;
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id) {

        Request request = requestService.getRequest(id);

        requestService.delete(request);
    }
}
