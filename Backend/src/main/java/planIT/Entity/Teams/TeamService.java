package planIT.Entity.Teams;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import planIT.Entity.Users.User;
import planIT.Entity.Users.UserRepository;

/**
 * Service class for managing Team entities.
 * This class handles business logic related to Team operations, interacting with the TeamRepository and UserRepository.
 * It is annotated with @Service to denote it as a service component, and @Transactional to enable transactional actions.
 *
 * @author Melani Hodge
 *
 */
@Service
@Transactional
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Retrieves all Teams from the repository.
     *
     * @return List of Team entities.
     */
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * Retrieves a specific Team from the repository based on the provided ID.
     *
     * @param id The unique identifier of the Team.
     * @return The Team entity corresponding to the provided ID.
     */
    public Team getTeamById(int id) {
        return teamRepository.findById(id);
    }

    /**
     * Creates a new Team in the repository.
     *
     * @param team The Team entity to be created.
     * @return A success or failure message as a JSON string.
     */
    public String createTeam(Team team) {
        teamRepository.save(team);
        return success;
    }

    /**
     * Updates an existing Team in the repository based on the provided ID.
     *
     * @param id      The unique identifier of the Team to be updated.
     * @param request The updated Team entity.
     * @return The updated Team entity or null if the Team with the provided ID is not found.
     */
    public Team updateTeam(int id, Team request) {
        Team team = teamRepository.findById(id);
        if (team == null)
            return null;

        team.setName(request.getName());
        team.setDescription(request.getDescription());

        teamRepository.save(team);
        return teamRepository.findById(id);
    }

    /**
     * Deletes a Team from the repository based on the provided ID.
     *
     * @param id The unique identifier of the Team to be deleted.
     * @return A success or failure message as a JSON string.
     */
    public String deleteTeam(int id) {
        teamRepository.deleteById(id);
        return success;
    }

    /**
     * Adds a user to a specific Team.
     *
     * @param id       The unique identifier of the Team.
     * @param username The username of the user to be added.
     * @return A success or failure message as a JSON string.
     */
    public String addUserToTeam(int id, String username){

        User user = userRepository.findByUsername(username);
        Team team = teamRepository.findById(id);
        team.getUsers().add(user);

        return success;
    }
}
