package planIT.Entity.Messages;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Message entities
 * @author Melani Hodge
 *
 */

// Initializes the Message repository.
public interface MessageRepository extends JpaRepository<Message, Long> {
    /**
     * returns a Message by its id number
     * @param id id number of target message
     * @return Message
     */
    Message findById(int id);

    /**
     * Delete a message from repository
     * @param id id number of message to be deleted
     */
    void deleteById(int id);

}
