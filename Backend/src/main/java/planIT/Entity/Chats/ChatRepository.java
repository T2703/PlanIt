package planIT.Entity.Chats;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for chat entities
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {
    /**
     * Finds a chat from a given id number
     * @param id id number of target chat
     * @return chat
     */
    Chat findById(int id);

    /**
     * Deletes a chat from the repository
     * @param id id number of target chat
     */
    void deleteById(int id);

}
