package planIT.Tags;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(path = "/tags")
    public List<Tag> getAllTags(){
        return tagService.getAllTags();     //Change?
    }

    @GetMapping(path = "/tags/{id}")
    public Tag getTagById(@PathVariable int id){
        return tagService.getTagById(id);
    }

    @PostMapping(path = "/tags")
    public String createEvent(@RequestBody Tag tag){
        return tagService.createTag(tag);
    }

    @PutMapping(path = "/tags/{id}")
    public Tag updateTag(@PathVariable int id, @RequestBody Tag tag){
        return tagService.updateTag(id, tag);
    }

    @DeleteMapping(path = "/tags/{id}")
    public String deleteTag(@PathVariable int id){
        return tagService.deleteTag(id);
    }
}
