package planIT;

import java.util.Date;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

import planIT.Assignments.*;
import planIT.ToDos.*;
import planIT.Tags.*;
import planIT.Events.*;
import planIT.Users.*;
import planIT.Teams.*;
import planIT.Messages.*;
import planIT.Notifications.*;

@SpringBootApplication
@EnableTransactionManagement
public class PlanITApplication {

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(PlanITApplication.class, args);
    }

    @Bean
    CommandLineRunner initUser(TeamRepository teamRepository, EventRepository eventRepository, MessageRepository messageRepository, UserRepository userRepository, AssignmentRepository assignmentRepository, ToDoRepository toDoRepository, TagRepository tagRepository, NotificationRepository notificationRepository) {
        return args -> {

            Assignment assignment1 = new Assignment("Title1", "Desc1", "Course1", new Date());
            Assignment assignment2 = new Assignment("Title2", "Desc2", "Course2", new Date());
            Assignment assignment3 = new Assignment("Title3", "Desc3", "Course3", new Date());
            
            ToDo toDo1 = new ToDo("name1", "desc1", new Date(), new Date());
            ToDo toDo2 = new ToDo("name2", "desc2", new Date(), new Date());
            ToDo toDo3 = new ToDo("name3", "desc3", new Date(), new Date());

            Tag tag1 = new Tag("name1", "desc1");
            Tag tag2 = new Tag("name2", "desc2");
            Tag tag3 = new Tag("name3", "desc3");

            User user1 = new User("John", "password", "john@somemail.com");
            User user2 = new User("Jane", "password", "jane@somemail.com");
            User user3 = new User("Justin", "password", "justin@somemail.com");

            Event event1 = new Event("Event 1", "Description 1", "Location1", "Private", new Date(), new Date(), 1);
            Event event2 = new Event("Event 2", "Description 2", "Location2", "Private", new Date(), new Date(), 2);
            Event event3 = new Event("Event 3", "Description 3", "Location3", "Private", new Date(), new Date(), 1);

            Message message1 = new Message("Message 1", new Date(), new Date());
            Message message2 = new Message("Message 2", new Date(), new Date());
            Message message3 = new Message("Message 3", new Date(), new Date());

            Team team1 = new Team("Group 1", "Description 1");
            Team team2 = new Team("Group 2", "Description 2");
            Team team3 = new Team("Group 3", "Description 3");

            Notification notification1 = new Notification("Notification 1", "Description 1");
            Notification notification2 = new Notification("Notification 2", "Description 2");
            Notification notification3 = new Notification("Notification 3", "Description 3");

            assignmentRepository.save(assignment1);
            assignmentRepository.save(assignment2);
            assignmentRepository.save(assignment3);

            toDoRepository.save(toDo1);
            toDoRepository.save(toDo2);
            toDoRepository.save(toDo3);

            tagRepository.save(tag1);
            tagRepository.save(tag2);
            tagRepository.save(tag3);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);

            messageRepository.save(message1);
            messageRepository.save(message2);
            messageRepository.save(message3);

            teamRepository.save(team1);
            teamRepository.save(team2);
            teamRepository.save(team3);

            notificationRepository.save(notification1);
            notificationRepository.save(notification2);
            notificationRepository.save(notification3);
        };
    }
}
