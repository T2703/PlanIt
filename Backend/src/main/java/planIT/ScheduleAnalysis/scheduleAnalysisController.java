package planIT.ScheduleAnalysis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import planIT.Entity.Users.*;

/**
 * Controller class for handling schedule analysis requests.
 * It provides an endpoint for measuring and displaying the weekly activity of a user based on their schedule.
 *
 * @author Chris Smith
 *
 */
@RestController
public class scheduleAnalysisController {

    private final UserRepository userRepository;

    /**
     * Constructor for scheduleAnalysisController.
     *
     * @param userRepository The repository for accessing user data.
     */
    @Autowired
    public scheduleAnalysisController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Endpoint for measuring and displaying the weekly activity of a user based on their schedule.
     *
     * @param userId The unique identifier of the user for whom to measure weekly activity.
     * @return A formatted string representing the weekly activity.
     */
    @GetMapping(path = "/scheduleAnalysis/{userId}")
    public String test(@PathVariable int userId) {

        User user = userRepository.findById(userId);
        String temp = scheduleAnalysis.measureWeeklyActivity(user);
        System.out.println(user.getUsername() +" " +user.getEvents().size());
        System.out.println(temp);

        return temp;
    }
}
