package ru.amironnikov.order.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    private final Environment environment;

    public OpenAPIConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI defineOpenAPI () {
        Server server = new Server();
        String serverUrl = environment.getProperty("api.server.url");
        server.setUrl(serverUrl);
        server.setDescription("Order service");

        Info info = new Info()
                .title("Order-service API")
                .version("1.0.0")
                .description("This API provides endpoints for managing orders.");

        return new OpenAPI().info(info).servers(List.of(server));
    }
}