package planIT.Entity.Chats;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import planIT.Entity.Assignments.Assignment;
import planIT.Entity.Messages.Message;
import planIT.Entity.Teams.Team;

/**
 * Controller class for the chat entity
 */
@RestController
public class ChatController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    /**
     * Chat service to be utilized by calls
     */
    @Autowired
    private ChatService chatService;

    // GET method - retreives all chats from the database.
    /**
     * Accesses the chatService.getAllChats() method
     * Returns all chats from a repository as a set
     * @return chats
     */
    @GetMapping(path = "/chats")
    public List<Chat> getAllChats() {
        return chatService.getAllChats();
    }

    // GET method - retreives a chat from the database.
    /**
     * Accesses chatService.getChatById();
     * Returns a chat from its id number
     * @param id the id number of the desired chat
     * @return success
     */
    @GetMapping(path = "/chats/{id}")
    public Chat getChatById(@PathVariable int id) {
        return chatService.getChatById(id);
    }

    // POST method - adds a chat to the database.
    /**
     * Accesses chatService.createChat()
     * @param chat newly created chat entity
     * @return success
     */
    @PostMapping(path = "/chats")
    public String createChat(@RequestBody Chat chat) {
        return chatService.createChat(chat);
    }


    //POST method - creates a chat from a team entity

    /**
     * Accesses the chatService.createTeamChat method.
     * Creates a new chat entity and immediately adds all users from a given team as
     * members of the new chat.
     * @param teamId id number of the team
     * @param chat newly created chat entity
     * @return success
     */
    @PostMapping(path = "/chats/{teamId}/chat")
    public String createTeamChat(@PathVariable int teamId, @RequestBody Chat chat){
        return chatService.createTeamChat(teamId, chat);
    }

    /**
     * Accesses the chatService.createMessageInChat() method.
     * Posts a new message and simultaneously attaches it to a chat entity.
     * @param id id number of the desired chat
     * @param message newly created message entity
     * @return success
     */
    @PostMapping(path = "chats/{id}/messages")
    public String createMessageInChat(@PathVariable int id, @RequestBody Message message){
        return chatService.createMessageInChat(id, message);
    }

    // PUT method - updates a chat in the database.

    /**
     * Accesses the chatService.updateChat() method.
     * @param id id number of the chat to be updated
     * @param chat chat entity with the info to be updated
     * @return success
     */
    @PutMapping(path = "/chats/{id}")
    public Chat updateChat(@PathVariable int id, @RequestBody Chat chat) {
        return chatService.updateChat(id, chat);
    }

    // PUT method - adds a user to a chat
    /**
     * Accesses chatService.addUserToChat()
     * Adds a preexisting user to a preexisting chat
     * @param username username of the user to be added
     * @param chatId id number of the target chat entity
     * @return success
     */
    @PutMapping(path = "/chats/{chatId}/users/{username}")
    public String addUserToChat(@PathVariable String username, @PathVariable int chatId) {
        return chatService.addUserToChat(username, chatId);
    }

    // PUT method - adds a user to a chat
    /**
     * Acccesses chatService.addMessageToChat() method.
     * Adds a preexisting message entity to a preexisting chat entity
     * @param chatId id number of the target chat
     * @param messageId id number of the target message
     * @return success
     */
    @PutMapping(path = "/chats/{chatId}/messages/{messageId}")
    public String addMessageToChat(@PathVariable int chatId, @PathVariable int messageId) {
        return chatService.addMessageToChat(chatId, messageId);
    }

    // DELETE method - deletes a chat from the database.
    /**
     * Accesses chatService.deleteChat() method.
     * Deletes a chat from the database
     * @param id id number of the target chat
     * @return success
     */
    @DeleteMapping(path = "/chats/{id}")
    public String deleteChat(@PathVariable int id) {
        return chatService.deleteChat(id);
    }

    // DELETE method - deletes a user from a chat.
    /**
     * Accesses chatService.removeUserFromChat() method.
     * Removes a user from a chat
     * @param username username of target user
     * @param chatID id number of the chat
     * @return success
     */
    @DeleteMapping(path = "/chats/{chatID}/users/{username}")
    public String removeUserFromChat(@PathVariable String username, int chatID) {
        return chatService.removeUserFromChat(username, chatID);
    }
}
