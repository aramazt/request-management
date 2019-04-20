package com.prime.task.service;

import com.prime.task.model.Request;
import com.prime.task.model.User;
import com.prime.task.repository.RequestRepository;
import com.prime.task.repository.UserRepository;
import com.prime.task.utils.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OnStart implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestRepository requestRepository;


    @Override
    public void run(String... args) throws Exception {

        // Populating database on application startup with random values

        //users
        User user1 = userRepository.save(new User("jack", "Jack Smith", UserStatus.ONLINE));
        User user2 = userRepository.save(new User("styopa", "Stepan Styop", UserStatus.ONLINE));
        User user3 = userRepository.save(new User("kevin", "Kevin Mortins", UserStatus.ONLINE));
        User user4 = userRepository.save(new User("ketty", "Ketty Bennington", UserStatus.ONLINE));
        User user5 = userRepository.save(new User("astrid77", "Astrid Stivens", UserStatus.ONLINE));
        User user6 = userRepository.save(new User("roman", "Roman Simonov", UserStatus.OFFLINE));
        User user7 = userRepository.save(new User("joseph", "Joseph Levitt", UserStatus.OFFLINE));

        //requests
        requestRepository.save(new Request("AAS-1", "Description for AAS-1...", user1));
        requestRepository.save(new Request("DDS-33", "Description for DDS-33...", user1));
        requestRepository.save(new Request("VSE-22", "Description for VSE-22...", user1));
        requestRepository.save(new Request("FF-233", "Description for FF-233...", user2));
        requestRepository.save(new Request("STAT-200", "Description for STAT-200...", user3));
        requestRepository.save(new Request("AAS-44", "Description for AAS-44...", user4));
        requestRepository.save(new Request("VSE-56", "Description for VSE-56...", user4));
        requestRepository.save(new Request("PICS-56", "Description for PICS-56...", user4));
        requestRepository.save(new Request("DTS-8", "Description for DTS-8...", user4));
        requestRepository.save(new Request("CCC-1", "Description for CCC-1...", user5));
    }
}
