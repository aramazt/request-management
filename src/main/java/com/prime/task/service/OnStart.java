package com.prime.task.service;

import com.prime.task.model.Request;
import com.prime.task.model.User;
import com.prime.task.utils.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * This component will only execute (and get instantiated) if the
 * property [database.recreate] is set to true in the
 * application.properties file
 */

@Component
@ConditionalOnProperty(name = "database.recreate", havingValue = "true")
public class OnStart implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private RequestService requestService;


    @Override
    public void run(String... args) throws Exception {

        // Populating database on application startup with random values

        //users
        User user1 = userService.save(new User("jack", "Jack Smith", UserStatus.ONLINE));
        User user2 = userService.save(new User("styopa", "Stepan Styop", UserStatus.ONLINE));
        User user3 = userService.save(new User("kevin", "Kevin Mortins", UserStatus.ONLINE));
        User user4 = userService.save(new User("ketty", "Ketty Bennington", UserStatus.ONLINE));
        User user5 = userService.save(new User("astrid77", "Astrid Stivens", UserStatus.ONLINE));
        User user6 = userService.save(new User("roman", "Roman Simonov", UserStatus.OFFLINE));
        User user7 = userService.save(new User("joseph", "Joseph Levitt", UserStatus.OFFLINE));

        //requests
        requestService.save(new Request("AAS-1", "Description for AAS-1...", user1));
        requestService.save(new Request("DDS-33", "Description for DDS-33...", user1));
        requestService.save(new Request("VSE-22", "Description for VSE-22...", user1));
        requestService.save(new Request("FF-233", "Description for FF-233...", user2));
        requestService.save(new Request("STAT-200", "Description for STAT-200...", user3));
        requestService.save(new Request("AAS-44", "Description for AAS-44...", user4));
        requestService.save(new Request("VSE-56", "Description for VSE-56...", user4));
        requestService.save(new Request("PICS-56", "Description for PICS-56...", user4));
        requestService.save(new Request("DTS-8", "Description for DTS-8...", user4));
        requestService.save(new Request("CCC-1", "Description for CCC-1...", user5));
    }
}
