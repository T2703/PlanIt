package planIT.ScheduleCompare;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import planIT.Entity.Events.Event;
import planIT.Entity.Teams.Team;
import planIT.Entity.Teams.TeamRepository;

import java.util.Date;
import java.util.List;

/**
 * Controller to access the scheduleCompare class.  API calls use 'RequestBody Event' as a container for the start/end dates of the method call.
 */
@RestController
@Tag(name = "Schedule Compare Controller", description = "Finds the time availability for all members of a team and returns it as either an Event List or a String")
public class scheduleCompareController {

    private final TeamRepository teamRepository;

    @Autowired
    public scheduleCompareController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping(path = "/compareSchedule/{teamId}/dates")
    @Operation(summary = "Compares team schedules, returns Event List", description = "Compares the events of a team and returns the availability as an Event List")
    public List<Event> compareSchedule(@PathVariable int teamId, @RequestBody Event event) {

        Date start = event.getStartDate();
        Date end = event.getEndDate();

        Team target = teamRepository.findById(teamId);
        List<Event> returnList = scheduleCompare.compareSchedule(target, start, end);

        return returnList;
    }

    @PutMapping(path = "/compareStandard/{teamId}/dates")
    @Operation(summary = "Compares team schedules, returns a String", description = "Compares the events of a team and returns the availability as a String")
    public String compareStandard(@PathVariable int teamId, @RequestBody Event event) {

        Date start = event.getStartDate();
        Date end = event.getEndDate();

        Team target = teamRepository.findById(teamId);
        String returnString = scheduleCompare.compareStandard(target, start, end);

        System.out.println(returnString);

        String success = "{\"message\":\"" +returnString.substring(11) +"\"}";

        return success;
    }

    @GetMapping(path = "/compareBy30/{teamId}/dates")
    @Operation(summary = "Compares team schedules by 30 mins, returns a String", description = "Compares the events of a team in 30 minute increments and returns the availability as a String")
    public String compareBy30(@PathVariable int teamId, @RequestBody Event event) {

        Date start = event.getStartDate();
        Date end = event.getEndDate();

        Team target = teamRepository.findById(teamId);
        String returnString = scheduleCompare.compareBy30(target, start, end);

        return returnString;
    }
}