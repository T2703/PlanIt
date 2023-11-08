package planIT.Entity.Chats;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import planIT.Entity.Messages.MessageRepository;
import planIT.Entity.Users.User;
import planIT.Entity.Users.UserRepository;
import planIT.Entity.Messages.Message;


// @Service - Used to denote a service.
// @Transactional - Used to allow transactional actions on the server.
@Service
@Transactional
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Chat getChatById(int id) {
        return chatRepository.findById(id);
    }

    public String createChat(Chat chat) {
        chatRepository.save(chat);
        return success;
    }

    public Chat updateChat(int id, Chat request) {
        Chat chat= chatRepository.findById(id);
        if (chat == null)
            return null;

        chat.setName(request.getName());

        chatRepository.save(chat);
        return chatRepository.findById(id);
    }

    public String addUserToChat(int userId, int chatId) {
        User user = userRepository.findById(userId);
        Chat chat = chatRepository.findById(chatId);
        user.getChats().add(chat);
        chat.getUsers().add(user);
        chatRepository.save(chat);
        return success;
    }

    public String removeUserFromChat(int userId, int chatId) {
        User user = userRepository.findById(userId);
        Chat chat = chatRepository.findById(chatId);
        user.getChats().remove(chat);
        chat.getUsers().remove(user);
        chatRepository.save(chat);
        return success;
    }

    public String deleteChat(int id) {
        chatRepository.deleteById(id);
        return success;
    }

    public Chat findPrivateChat(int userID1, int userID2){
        User user1 = userRepository.findById(userID1);
        User user2 = userRepository.findById(userID2);

        for(int i=0; i< chatRepository.findAll().size(); ++i) {
            Chat sample = chatRepository.findAll().get(i);

            if(user1.getChats().contains(sample) & user2.getChats().contains(sample) & sample.chatSize()==2){
                return sample;
            }
        }
        Chat newDM = new Chat("New Private Chat");
        addUserToChat(userID1, newDM.getId());
        addUserToChat(userID2, newDM.getId());
        return newDM;
    }
}
