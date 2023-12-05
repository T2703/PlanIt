package planIT.Entity.Tags;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import planIT.Entity.Users.User;
import planIT.Entity.Users.UserService;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/tags/{id}")
    @Operation(summary = "Get a Tag by Id", description = "Gets a tag from the repository based on id number")
    public Tag getTagById(@PathVariable int id){
        return tagService.getTagById(id);
    }

    // POST method - adds a tag to the database.
    @PostMapping(path = "/users/{username}/tags")
    @Operation(summary = "Create a new Tag for a user", description = "Adds a tag to the database")
    public String createTag(@PathVariable String username, @RequestBody Tag tag) {
        return tagService.createTag(username, tag);
    }

    // GET method - gets tags from a specific user.
    @GetMapping(path = "/users/{username}/tags")
    @Operation(summary = "Get tags for a specific user", description = "Gets all tags of a user and returns them as a List")
    public List<Tag> getUserTags(@PathVariable String username) {
        return tagService.getTagByUser(username);
    }

    @PutMapping(path = "/tags/{id}")
    @Operation(summary = "Update an existing Tag", description = "Updates a tag in the repository")
    public String updateTag(@PathVariable int id, @RequestBody Tag tag){
        return tagService.updateTag(id, tag);
    }

    @DeleteMapping(path = "users/{username}/tags/{id}")
    @Operation(summary = "Delete a Tag by Id", description = "Deletes a tag from the repository")
    public String deleteTag(@PathVariable String username, @PathVariable int id){
        User user = userService.findUserByUsername(username);
        user.getTags().remove(tagService.getTagById(id));
        return tagService.deleteTag(id);
    }
}
