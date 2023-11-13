package planIT.Entity.Messages;

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
 * Controller class for the Message entity
 * @author Melani Hodge
 *
 */
@RestController
public class MessageController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private MessageService messageService;

    // GET method - retreives all messages from the database.

    /**
     * Returns all messages in the repository as a List object
     * @return Message List
     */
    @GetMapping(path = "/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    // GET method - retreives a message from the database.
    /**
     * Gets a message from repository based on id number
     * @param id id number of the target message
     * @return Message
     */
    @GetMapping(path = "/messages/{id}")
    public Message getMessageById(@PathVariable int id) {
        return messageService.getMessageById(id);
    }

    // POST method - adds a message to the database.
    /**
     * Posts a new method in the repository
     * @param message new message to be saved
     * @return success
     */
    @PostMapping(path = "/messages")
    public String createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    // PUT method - updates a message in the database.
    /**
     * Updates a message in the repository
     * @param id id number of the target message
     * @param message Message object with details to change
     * @return success
     */
    @PutMapping(path = "/messages/{id}")
    public Message updateMessage(@PathVariable int id, @RequestBody Message message) {
        return messageService.updateMessage(id, message);
    }

    // DELETE method - deletes a message from the database.
    /**
     * Deletes a message from the repository
     * @param id id number of the target message
     * @return success
     */
    @DeleteMapping(path = "/messages/{id}")
    public String deleteMessage(@PathVariable int id) {
        return messageService.deleteMessage(id);
    }
}

