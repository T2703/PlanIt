package planIT.Entity.Chats;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findById(int id);
    void deleteById(int id);

}
