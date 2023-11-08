package planIT.Entity.Tags;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findById(int id);
    void deleteById(int id);
}
