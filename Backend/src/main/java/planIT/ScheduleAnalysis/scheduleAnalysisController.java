package planIT.ScheduleAnalysis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import planIT.Users.*;

@RestController
public class scheduleAnalysisController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/scheduleAnalysis/{userId}")
    public String test(@PathVariable int userId) {

        //UserRepository userRepository;
        User user =userRepository.findById(userId);

        return scheduleAnalysis.measureWeeklyActivity(user);
    }
}
