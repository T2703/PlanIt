package planIT.Entity.Messages;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Melani Hodge
 *
 */

// Initializes the Message repository.
public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findById(int id);
    void deleteById(int id);

}
