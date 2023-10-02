package planIT;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

import planIT.Events.*;
import planIT.Users.*;
import planIT.Teams.*;
import planIT.Messages.*;

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
    CommandLineRunner initUser(UserRepository userRepository, EventRepository eventRepository, MessageRepository messageRepository, TeamRepository teamRepository) {
        return args -> {
            User user1 = new User("John", "password", "john@somemail.com");
            User user2 = new User("Jane", "password", "jane@somemail.com");
            User user3 = new User("Justin", "password", "justin@somemail.com");
            Event event1 = new Event("Event 1", "Description 1", new Date(), new Date());
            Event event2 = new Event("Event 2", "Description 2", new Date(), new Date());
            Event event3 = new Event("Event 3", "Description 3", new Date(), new Date());
            Message message1 = new Message("Message 1", new Date(), new Date());
            Message message2 = new Message("Message 2", new Date(), new Date());
            Message message3 = new Message("Message 3", new Date(), new Date());
            Team team1 = new Team("Group 1", "Description 1");
            Team team2 = new Team("Group 2", "Description 2");
            Team team3 = new Team("Group 3", "Description 3");
            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            messageRepository.save(message1);
            messageRepository.save(message2);
            messageRepository.save(message3);
            teamRepository.save(team1);
            teamRepository.save(team2);
            teamRepository.save(team3);
        };
    }
}
