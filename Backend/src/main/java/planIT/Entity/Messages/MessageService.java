package planIT.Entity.Messages;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
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

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepository.findById(id);
    }

    public String createMessage(Message message) {
        messageRepository.save(message);
        return success;
    }

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

    public String deleteMessage(int id) {
        messageRepository.deleteById(id);
        return success;
    }
}
