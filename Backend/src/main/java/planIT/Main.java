package planIT;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import planIT.Events.Event;
import planIT.Events.EventRepository;
import planIT.Users.User;
import planIT.Users.UserRepository;

@SpringBootApplication
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initUser(UserRepository userRepository, EventRepository eventRepository) {
        return args -> {
            User user1 = new User("John", "password", "john@somemail.com");
            User user2 = new User("Jane", "password", "jane@somemail.com");
            User user3 = new User("Justin", "password", "justin@somemail.com");
            Event event1 = new Event("Event 1", "Description 1", new Date(), new Date());
            Event event2 = new Event("Event 2", "Description 2", new Date(), new Date());
            Event event3 = new Event("Event 3", "Description 3", new Date(), new Date());
            List<Event> list1 = new ArrayList<Event>();
            List<Event> list2 = new ArrayList<Event>();
            List<Event> list3 = new ArrayList<Event>();
            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);
            user1.addEvents(event1);
            user1.addEvents(event2);
            user2.addEvents(event3);
            user3.addEvents(event1);
            user3.addEvents(event2);
            user3.addEvents(event3);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        };
    }

}
