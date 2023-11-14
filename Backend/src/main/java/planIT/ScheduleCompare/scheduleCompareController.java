package planIT.ScheduleCompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import planIT.Entity.Teams.Team;
import planIT.Entity.Teams.TeamRepository;

import java.util.Date;

@RestController
public class scheduleCompareController {

    private final TeamRepository teamRepository;

    @Autowired
    public scheduleCompareController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping(path = "/scheduleCompare/{teamId}/{startDate}/{endDate}")
    public String compareStandard(@PathVariable int teamId, @PathVariable long startDate, @PathVariable long endDate) {

        Date start = new Date(startDate);
        Date end = new Date(endDate);

        Team target = teamRepository.findById(teamId);
        String returnString = scheduleCompare.compareStandard(target, start, end);

        return returnString;
    }
}