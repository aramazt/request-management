package com.prime.task.service;

import com.prime.task.model.Request;
import com.prime.task.model.User;
import com.prime.task.repository.RequestRepository;
import com.prime.task.utils.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository repository;

    @Autowired
    public RequestService(RequestRepository repository) {
        this.repository = repository;
    }

    public Request getRequest(Long id) {
        if (id == null) return null;

        return repository.findById(id).get();
    }

    public List<Request> getAllRequests() {
        return repository.findAll();
    }

    public List<Request> getRequestsByResponsible(User user) {
        if (user == null)
            return null;

        return repository.findAllByResponsible(user);
    }

    public List<Request> getRequestsByResponsibleId(Long id) {
        if (id == null)
            return null;

        return repository.findAllByResponsibleId(id);
    }

    public List<Request> getAllByStatus(RequestStatus status) {
        if (status == null)
            return null;

        return repository.findAllByStatus(status);
    }

    public Request save(Request request) {
        return repository.save(request);
    }

    public void delete(Request request) {
        repository.delete(request);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
