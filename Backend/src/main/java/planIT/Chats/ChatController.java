package planIT.Chats;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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

    // POST method - adds a user to a chat
    @PostMapping(path = "/users/{userId}/chats/{chatId}")
    public String addUserToChat(@PathVariable int userId, @PathVariable int chatId) {
        return chatService.addUserToChat(userId, chatId);
    }

    // TODO POST..or PUT? method - add a message to a chat??

    // PUT method - updates a chat in the database.
    @PutMapping(path = "/chats/{id}")
    public Chat updateChat(@PathVariable int id, @RequestBody Chat chat) {
        return chatService.updateChat(id, chat);
    }

    // DELETE method - deletes a chat from the database.
    @DeleteMapping(path = "/chats/{id}")
    public String deleteChat(@PathVariable int id) {
        return chatService.deleteChat(id);
    }

    // DELETE method - deletes a user from a chat.
    @DeleteMapping(path = "/chats/{chatID}/users/{userId}")
    public String removeUserFromChat(@PathVariable int userID, int chatID) {
        return chatService.removeUserFromChat(userID, chatID);
    }

}
