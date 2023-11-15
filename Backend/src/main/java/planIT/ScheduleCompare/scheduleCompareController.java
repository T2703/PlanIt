package planIT.ScheduleCompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import planIT.Entity.Events.Event;
import planIT.Entity.Teams.Team;
import planIT.Entity.Teams.TeamRepository;

import java.util.Date;
import java.util.List;

@RestController
public class scheduleCompareController {

    private final TeamRepository teamRepository;

    @Autowired
    public scheduleCompareController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping(path = "/compareSchedule/{teamId}/{startDate}/{endDate}")
    public List<Event> compareSchedule(@PathVariable int teamId, @PathVariable long startDate, @PathVariable long endDate) {

        Date start = new Date(startDate);
        Date end = new Date(endDate);

        Team target = teamRepository.findById(teamId);
        List<Event> returnList = scheduleCompare.compareSchedule(target, start, end);

        return returnList;
    }

    @GetMapping(path = "/compareStandard/{teamId}/{startDate}/{endDate}")
    public String compareStandard(@PathVariable int teamId, @PathVariable long startDate, @PathVariable long endDate) {

        Date start = new Date(startDate);
        Date end = new Date(endDate);

        Team target = teamRepository.findById(teamId);
        String returnString = scheduleCompare.compareStandard(target, start, end);

        return returnString;
    }

    @GetMapping(path = "/compareBy30/{teamId}/{startDate}/{endDate}")
    public String compareBy30(@PathVariable int teamId, @PathVariable long startDate, @PathVariable long endDate) {

        Date start = new Date(startDate);
        Date end = new Date(endDate);

        Team target = teamRepository.findById(teamId);
        String returnString = scheduleCompare.compareBy30(target, start, end);

        return returnString;
    }


}