package planIT.Entity.Teams;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Melani Hodge
 *
 */

// @Service - Used to denote a service.
// @Transactional - Used to allow transactional actions on the server.
@Service
@Transactional
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(int id) {
        return teamRepository.findById(id);
    }

    public String createTeam(Team team) {
        teamRepository.save(team);
        return success;
    }

    public Team updateTeam(int id, Team request) {
        Team team = teamRepository.findById(id);
        if (team == null)
            return null;

        team.setName(request.getName());
        team.setDescription(request.getDescription());

        teamRepository.save(team);
        return teamRepository.findById(id);
    }

    public String deleteTeam(int id) {
        teamRepository.deleteById(id);
        return success;
    }
}
