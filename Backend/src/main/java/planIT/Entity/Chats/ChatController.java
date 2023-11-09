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


@RestController
public class ChatController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private ChatService chatService;

    // GET method - retreives all chats from the database.
    @GetMapping(path = "/chats")
    public List<Chat> getAllChats() {
        return chatService.getAllChats();
    }

    // GET method - retreives a chat from the database.
    @GetMapping(path = "/chats/{id}")
    public Chat getChatById(@PathVariable int id) {
        return chatService.getChatById(id);
    }

    // POST method - adds a chat to the database.
    @PostMapping(path = "/chats")
    public String createChat(@RequestBody Chat chat) {
        return chatService.createChat(chat);
    }

    //POST method - creates a chat from a team entity
    @PostMapping(path = "/chats/{teamId}/chat")
    public String createTeamChat(@PathVariable int teamId, @RequestBody Chat chat){
        return chatService.createTeamChat(teamId, chat);
    }

    @PostMapping(path = "chats/{id}/messages")
    public String createMessageInChat(@PathVariable int id, @RequestBody Message message){
        return chatService.createMessageInChat(id, message);
    }

    // PUT method - updates a chat in the database.
    @PutMapping(path = "/chats/{id}")
    public Chat updateChat(@PathVariable int id, @RequestBody Chat chat) {
        return chatService.updateChat(id, chat);
    }

    // PUT method - adds a user to a chat
    @PutMapping(path = "/chats/{chatId}/users/{username}")
    public String addUserToChat(@PathVariable String username, @PathVariable int chatId) {
        return chatService.addUserToChat(username, chatId);
    }

    // PUT method - adds a user to a chat
    @PutMapping(path = "/chats/{chatId}/messages/{messageId}")
    public String addMessageToChat(@PathVariable int chatId, @PathVariable int messageId) {
        return chatService.addMessageToChat(chatId, messageId);
    }

    // DELETE method - deletes a chat from the database.
    @DeleteMapping(path = "/chats/{id}")
    public String deleteChat(@PathVariable int id) {
        return chatService.deleteChat(id);
    }

    // DELETE method - deletes a user from a chat.
    @DeleteMapping(path = "/chats/{chatId}/users/{userId}")
    public String removeUserFromChat(@PathVariable int chatId, int userId) {
        return chatService.removeUserFromChat(userId, chatId);
    }

}
