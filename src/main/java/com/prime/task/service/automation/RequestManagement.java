package com.prime.task.service.automation;

import com.prime.task.model.Request;
import com.prime.task.model.User;
import com.prime.task.service.RequestService;
import com.prime.task.service.UserService;
import com.prime.task.utils.RequestStatus;
import com.prime.task.utils.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestManagement {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    /**
     * This method distributes all opened requests of certain User who changed his status
     * from ONLINE to OFFLINE
     * Also this method will work on user DELETE operation
     */
    public void distributeOpenedRequestsOf(User user) {
        List<Request> allRequestsOfUser = requestService.getRequestsByResponsible(user);
        if (allRequestsOfUser.size() == 0) return;

        List<User> allOnlineUsers = userService.getUsersByStatus(UserStatus.ONLINE);
        allOnlineUsers.remove(user);

        for (Request r : allRequestsOfUser) {
            if (r.getStatus().equals(RequestStatus.OPEN)) {
                distributeOneRequest(r, allOnlineUsers, user);
            }
        }
    }

    private void distributeOneRequest(Request request, List<User> availableUsers, User previousUser) {

        // finding user with minimum number of requests

        int size = Integer.MAX_VALUE;
        User candidate = null;

        for (User user : availableUsers) {
            int currentUserRequestsSize = user.getRequests().size();

            if (currentUserRequestsSize < size) {
                size = currentUserRequestsSize;
                candidate = user;
            }
        }

        // adding request into candidate
        if (candidate != null) {
            request.setPrevResponsible(previousUser.getLogin());
            request.setResponsible(candidate);
            candidate.getRequests().add(request);
            userService.save(candidate);
        }

    }


    public void deleteCompletedRequests(User user) {
        List<Request> allRequests = requestService.getRequestsByResponsible(user);

        for (Request request : allRequests) {
            if (!request.getStatus().equals(RequestStatus.OPEN)){
                requestService.deleteById(request.getId());
            }
        }
    }
}
