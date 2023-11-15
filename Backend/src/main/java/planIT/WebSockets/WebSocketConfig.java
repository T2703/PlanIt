package planIT.WebSockets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Configuration class for WebSocket setup in the PlanIT application.
 *
 * <p>This class is annotated with {@code @Configuration} to indicate that it contains
 * bean definitions for the application context. It declares a {@link ServerEndpointExporter}
 * bean with the {@code @Bean} annotation, which is required for WebSocket support in
 * a Spring Boot application.</p>
 *
 * <p>The {@link ServerEndpointExporter} is responsible for automatically registering
 * WebSocket endpoints with the container when running in a servlet environment.</p>
 *
 * @see ServerEndpointExporter
 * @see Bean
 * @see Configuration
 *
 * @author Melani Hodge
 *
 */
@Configuration
public class WebSocketConfig {

    /**
     * Creates a new instance of {@link ServerEndpointExporter}.
     *
     * <p>This method is annotated with {@code @Bean}, indicating that it produces a bean
     * to be managed by the Spring container. The returned {@link ServerEndpointExporter}
     * is responsible for detecting and registering WebSocket endpoints.</p>
     *
     * @return A new instance of {@link ServerEndpointExporter}.
     * @see ServerEndpointExporter
     * @see Bean
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
