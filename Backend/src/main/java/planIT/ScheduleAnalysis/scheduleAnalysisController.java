package planIT.ScheduleAnalysis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import planIT.Users.*;
import planIT.Events.*;

@RestController
public class scheduleAnalysisController {

    private final UserRepository userRepository;

    @Autowired
    public scheduleAnalysisController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/scheduleAnalysis/{userId}")
    public String test(@PathVariable int userId) {

        User user = userRepository.findById(userId);
        String temp = scheduleAnalysis.measureWeeklyActivity(user);
        System.out.println(user.getUsername() +" " +user.getEvents().size());
        System.out.println(temp);

        return temp;
    }
}
