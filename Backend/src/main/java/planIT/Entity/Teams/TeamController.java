package planIT.Entity.Teams;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Melani Hodge
 *
 */

@RestController
public class TeamController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private TeamService teamService;

    // GET method - retreives all teams from the database.
    @GetMapping(path = "/teams")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    // GET method - retreives a team from the database.
    @GetMapping(path = "/teams/{id}")
    public Team getTeamById(@PathVariable int id) {
        return teamService.getTeamById(id);
    }

    // POST method - adds a team to the database.
    @PostMapping(path = "/teams")
    public String createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    // PUT method - updates a team in the database.
    @PutMapping(path = "/teams/{id}")
    public Team updateTeam(@PathVariable int id, @RequestBody Team team) {
        return teamService.updateTeam(id, team);
    }

    // DELETE method - deletes a team from the database.
    @DeleteMapping(path = "/teams/{id}")
    public String deleteTeam(@PathVariable int id) {
        return teamService.deleteTeam(id);
    }
}
