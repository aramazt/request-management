package com.prime.task.repository;

import com.prime.task.model.Request;
import com.prime.task.model.User;
import com.prime.task.utils.RequestStatus;
import com.prime.task.utils.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByResponsibleId(Long id);

    List<Request> findAllByResponsible(User user);

    List<Request> findAllByStatus(RequestStatus status);

}
