package planIT.Entity.Chats;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import planIT.Entity.Messages.MessageRepository;
import planIT.Entity.Teams.Team;
import planIT.Entity.Teams.TeamRepository;
import planIT.Entity.Users.User;
import planIT.Entity.Users.UserRepository;
import planIT.Entity.Messages.Message;


// @Service - Used to denote a service.
// @Transactional - Used to allow transactional actions on the server.
/**
 * Service class for the chat entity
 */
@Service
@Transactional
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TeamRepository teamRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Returns all chats from the repository as a list object
     * @return chats
     */
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    /**
     * Returns a chat from the repository with the matching id number
     * @param id id number of the target chat
     * @return chat
     */
    public Chat getChatById(int id) {
        return chatRepository.findById(id);
    }

    /**
     * Saves a chat to the repository
     * @param chat chat entity to be saved
     * @return success
     */
    public String createChat(Chat chat) {
        chatRepository.save(chat);
        return success;
    }

    /**
     * Updates a chat in the repository
     * @param id id number of the target chat
     * @param request chat entity with the updated info
     * @return chat
     */
    public Chat updateChat(int id, Chat request) {
        Chat chat= chatRepository.findById(id);
        if (chat == null)
            return null;

        chat.setName(request.getName());

        chatRepository.save(chat);
        return chatRepository.findById(id);
    }

    /**
     * Adds a preexisting user to a preexisting chat
     * @param username username of target user
     * @param chatId id number of the target chat
     * @return success
     */
    public String addUserToChat(String username, int chatId) {
        User user = userRepository.findByUsername(username);
        Chat chat = chatRepository.findById(chatId);
        user.getChats().add(chat);
        chat.getUsers().add(user);
        chatRepository.save(chat);
        return success;
    }

    /**
     * Removes a user from a chat
     * @param username username of target user
     * @param chatId id number of target chat
     * @return success
     */
    public String removeUserFromChat(String username, int chatId) {
        User user = userRepository.findByUsername(username);
        Chat chat = chatRepository.findById(chatId);
        user.getChats().remove(chat);
        chat.getUsers().remove(user);
        chatRepository.save(chat);
        return success;
    }

    /**
     * Deletes a chat from the repository
     * @param id id number of target chat
     * @return success
     */
    public String deleteChat(int id) {
        chatRepository.deleteById(id);
        return success;
    }

    /**
     * Searches both users chats looking for a chat of size two that contains both users.
     * If no such chat exists, it creates a new chat, adds both users, and saves it to the repository.
     * @param username1 username of the first user to compare
     * @param username2 username of the second user to compare
     * @return private chat
     */
    public Chat findPrivateChat(String username1, String username2){
        User user1 = userRepository.findByUsername(username1);
        User user2 = userRepository.findByUsername(username2);

        for(int i=0; i< chatRepository.findAll().size(); ++i) {
            Chat sample = chatRepository.findAll().get(i);

            if(user1.getChats().contains(sample) & user2.getChats().contains(sample) & sample.chatSize()==2){
                return sample;
            }
        }
        Chat newDM = new Chat("New Private Chat");
        addUserToChat(username1, newDM.getId());
        addUserToChat(username2, newDM.getId());
        chatRepository.save(newDM);
        return newDM;
    }

    /**
     * Simultaneously saves a new message entity and attaches it to a chat.
     * @param id id number of the target chat
     * @param message message entity to be saved
     * @return success
     */
    public String createMessageInChat(int id, Message message){
        Chat chat = chatRepository.findById(id);
        chat.getMessages().add(message);
        messageRepository.save(message);

        return success;
    }

    /**
     * Adds a preexisting message to preexisting chat
     * @param chatId id number of target chat
     * @param messageId id number of target message
     * @return success
     */
    public String addMessageToChat(int chatId, int messageId){
        Chat chat = chatRepository.findById(chatId);
        Message message = messageRepository.findById(messageId);
        chat.getMessages().add(message);

        return success;
    }

    /**
     * Creates a new chat entity and adds all members of a team to the chat
     * @param teamId id number of target team
     * @param chat chat entity supplied by request body
     * @return success
     */
    public String createTeamChat(int teamId, Chat chat){
        Team team = teamRepository.findById(teamId);
        for(User user: team.getUsers()){
            chat.getUsers().add(user);
        }
        chatRepository.save(chat);

        return success;
    }

}
