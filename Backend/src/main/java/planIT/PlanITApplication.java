package planIT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * The main class for the PlanIT application.
 * This class configures and launches the Spring Boot application.
 * It excludes security auto-configuration and enables transaction management.
 * The application can be started by running the {@link #main(String[])} method.
 *
 * @author Melani Hodge
 * @author Chris Smith
 *
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableTransactionManagement
public class PlanITApplication {

    /**
     * The main entry point for the PlanIT application.
     * It starts the Spring Boot application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(PlanITApplication.class, args);
    }

}
