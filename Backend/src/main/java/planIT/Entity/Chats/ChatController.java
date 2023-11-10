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


@RestController
public class ChatController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
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
     * @param id
     * @return
     */
    @GetMapping(path = "/chats/{id}")
    public Chat getChatById(@PathVariable int id) {
        return chatService.getChatById(id);
    }

    // POST method - adds a chat to the database.
    /**
     * Accesses chatService.createChat()
     * @param chat
     * @return success
     */
    @PostMapping(path = "/chats")
    public String createChat(@RequestBody Chat chat) {
        return chatService.createChat(chat);
    }

    // POST method - adds a user to a chat
    /**
     * Accesses chatService.addUserToChat()
     * Adds a preexisting user to a preexisting chat
     * @param username
     * @param chatId
     * @return success
     */
    @PostMapping(path = "/users/{username}/chats/{chatId}")
    public String addUserToChat(@PathVariable String username, @PathVariable int chatId) {
        return chatService.addUserToChat(username, chatId);
    }
    

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
    @DeleteMapping(path = "/chats/{chatID}/users/{username}")
    public String removeUserFromChat(@PathVariable String username, int chatID) {
        return chatService.removeUserFromChat(username, chatID);
    }

}
