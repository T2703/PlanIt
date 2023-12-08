package planIT.Entity.Tags;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import planIT.Entity.Users.UserRepository;

@Service
@Transactional
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public Tag getTagById(int id) {
        return tagRepository.findById(id);
    }

    public String createTag(String username, Tag tag) {
        tag.setUser(userRepository.findByUsername(username));
        tagRepository.save(tag);
        return success;
    }

    public String updateTag(int id, Tag request) {
        Tag tag = tagRepository.findById(id);
        if (tag == null)
            return failure;

        tag.setName(request.getName());
        tag.setDescription(request.getDescription());

        tagRepository.save(tag);
        return success;
    }

    public String deleteTag(int id) {
        tagRepository.deleteById(id);
        return success;
    }

    public List<Tag> getTagByUser(String username) {
        return userRepository.findByUsername(username).getTags();
    }
}
