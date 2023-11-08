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
 *
 * @author Melani Hodge
 *
 */

@RestController
public class MessageController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private MessageService messageService;

    // GET method - retreives all messages from the database.
    @GetMapping(path = "/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    // GET method - retreives a message from the database.
    @GetMapping(path = "/messages/{id}")
    public Message getMessageById(@PathVariable int id) {
        return messageService.getMessageById(id);
    }

    // POST method - adds a message to the database.
    @PostMapping(path = "/messages")
    public String createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    // PUT method - updates a message in the database.
    @PutMapping(path = "/messages/{id}")
    public Message updateMessage(@PathVariable int id, @RequestBody Message message) {
        return messageService.updateMessage(id, message);
    }

    // DELETE method - deletes a message from the database.
    @DeleteMapping(path = "/messages/{id}")
    public String deleteMessage(@PathVariable int id) {
        return messageService.deleteMessage(id);
    }
}

