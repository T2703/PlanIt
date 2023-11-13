package planIT.Entity.Messages;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service class for the Message entity
 * @author Melani Hodge
 *
 */

// @Service - Used to denote a service.
// @Transactional - Used to allow transactional actions on the server.
@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Returns all messages from the repository as a List object
     * @return Message List
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Returns a message from the repository based on id number
     * @param id id number of the target message
     * @return Message
     */
    public Message getMessageById(int id) {
        return messageRepository.findById(id);
    }

    /**
     * Saves a message to the repository
     * @param message message entity to be saved
     * @return success
     */
    public String createMessage(Message message) {
        messageRepository.save(message);
        return success;
    }

    /**
     * Updates a message in the repository
     * @param id id number of the target message
     * @param request Message object with the change information
     * @return updated Message
     */
    public Message updateMessage(int id, Message request) {
        Message message = messageRepository.findById(id);
        if (message == null)
            return null;

        message.setBody(request.getBody());
        message.setSendTime(request.getSendTime());
        message.setReceiveTime(request.getReceiveTime());

        messageRepository.save(message);
        return messageRepository.findById(id);
    }

    /**
     * Deletes a message from the repository
     * @param id id number of the target message
     * @return success
     */
    public String deleteMessage(int id) {
        messageRepository.deleteById(id);
        return success;
    }
}
