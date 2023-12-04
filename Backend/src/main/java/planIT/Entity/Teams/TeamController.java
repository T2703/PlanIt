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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import planIT.Entity.Users.User;
import planIT.Entity.Users.UserService;

/**
 * RESTful controller for managing Team-related operations.
 * This controller handles HTTP requests related to Team entities, such as retrieval, creation, update, and deletion.
 * Requests are processed using the corresponding methods provided by the TeamService.
 *
 * @author Melani Hodge
 *
 */
@RestController
@Tag(name = "Team Management System", description = "Operations pertaining to Team management")
public class TeamController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    /**
     * Retrieves all Teams from the service.
     *
     * @return List of Team entities.
     */
    @GetMapping(path = "/teams")
    @Operation(summary = "Get all Teams", description = "Returns all teams from the repository as a List object")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    /**
     * Retrieves a specific Team from the service based on the provided ID.
     *
     * @param id The unique identifier of the Team.
     * @return The Team entity corresponding to the provided ID.
     */
    @GetMapping(path = "/teams/{id}")
    @Operation(summary = "Get a Team by Id", description = "Gets a team from the repository based on id number")
    public Team getTeamById(@PathVariable int id) {
        return teamService.getTeamById(id);
    }

    /**
     * Creates a new Team in the service.
     *
     * @param team The Team entity to be created.
     * @return A success or failure message as a JSON string.
     */
    @PostMapping(path = "users/{username}/teams")
    @Operation(summary = "Create a new Team", description = "Adds a team to the database")
    public String createTeam(@PathVariable String username, @RequestBody Team team) {
        return teamService.createTeam(username, team);
    }

    /**
     * Adds a user to a specific Team in the service.
     *
     * @param id       The unique identifier of the Team.
     * @param username The username of the user to be added.
     * @return A success or failure message as a JSON string.
     */
    @PutMapping(path = "teams/{id}/add-user/{username}")
    @Operation(summary = "Add a user to a Team", description = "Adds a user to a team in the repository")
    public String addUserToTeam(@PathVariable int id, @PathVariable String username){
        return teamService.addUserToTeam(id, username);
    }

    /**
     * Updates an existing Team in the service based on the provided ID.
     *
     * @param id   The unique identifier of the Team to be updated.
     * @param team The updated Team entity.
     * @return The updated Team entity.
     */
    @PutMapping(path = "/teams/{id}")
    @Operation(summary = "Update an existing Team", description = "Updates a team in the repository")
    public String updateTeam(@PathVariable int id, @RequestBody Team team) {
        return teamService.updateTeam(id, team);
    }

    /**
     * Deletes a Team from the service based on the provided ID.
     *
     * @param id The unique identifier of the Team to be deleted.
     * @return A success or failure message as a JSON string.
     */
    @DeleteMapping(path = "users/{username}/teams/{id}")
    @Operation(summary = "Delete a Team by Id", description = "Deletes a team from the repository")
    public String deleteTeam(@PathVariable String username, @PathVariable int id) {
        teamService.getTeamById(id).getUsers().clear();
        User user = userService.findUserByUsername(username);
        user.getAdministrates().remove(teamService.getTeamById(id));
        return teamService.deleteTeam(id);
    }

    /**
     * Adds a user to a specific Team in the service.
     *
     * @param id       The unique identifier of the Team.
     * @param username The username of the user to be added.
     * @return A success or failure message as a JSON string.
     */
    @PutMapping(path = "teams/{id}/remove-user/{username}")
    @Operation(summary = "Add a user to a Team", description = "Adds a user to a team in the repository")
    public String removeUserFromTeam(@PathVariable int id, @PathVariable String username){
        return teamService.removeUserFromTeam(id, username);
    }
}

