package planIT.Entity.Teams;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import planIT.Entity.Chats.ChatRepository;
import planIT.Entity.Chats.ChatService;
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

    @Autowired
    private ChatService chatService;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

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
    public String createTeam(String username, Team team) {
        User user = userRepository.findByUsername(username);
        team.setAdmin(user);
        team.getUsers().add(user);
        user.getTeams().add(team);

        teamRepository.save(team);
        userRepository.save(user);

        return "{\"message\":\"success_" +team.getId()  +"\"}";
        //return success;
    }

    /**
     * Updates an existing Team in the repository based on the provided ID.
     *
     * @param id      The unique identifier of the Team to be updated.
     * @param request The updated Team entity.
     * @return The updated Team entity or null if the Team with the provided ID is not found.
     */
    public String updateTeam(int id, Team request) {
        Team team = teamRepository.findById(id);
        if (team == null)
            return failure;

        team.setName(request.getName());
        team.setDescription(request.getDescription());

        teamRepository.save(team);
        return success;
    }

    /**
     * Deletes a Team from the repository based on the provided ID.
     *
     * @param id The unique identifier of the Team to be deleted.
     * @return A success or failure message as a JSON string.
     */
    public String deleteTeam(int id) {
        if(teamRepository.findById(id).getChat() != null) {
            chatService.deleteChat(teamRepository.findById(id).getChat().getId()); //?
        }
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
        user.getTeams().add(team);

        if(team.getChat() != null){
            chatService.addUserToChat(username, team.getChat().getId());
        }

        teamRepository.save(team);
        userRepository.save(user);

        return success;
    }

    /**
     * Removes a user from a specific team
     *
     * @param id The unique identifier of the Team.
     * @param username The username of the user to be removed.
     * @return A success or failure message as a JSON string.
     */
    public String removeUserFromTeam(int id, String username){

        User user = userRepository.findByUsername(username);
        Team team = teamRepository.findById(id);
        team.getUsers().remove(user);
        user.getTeams().remove(team);

        if(team.getChat() != null){
            chatService.removeUserFromChat(username, team.getChat().getId());
        }

        teamRepository.save(team);
        userRepository.save(user);

        return success;
    }
}
