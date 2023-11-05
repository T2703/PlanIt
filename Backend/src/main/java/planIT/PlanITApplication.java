package planIT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
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


import planIT.ScheduleAnalysis.scheduleAnalysis;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })

@EnableTransactionManagement
public class PlanITApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanITApplication.class, args);
    }

}
