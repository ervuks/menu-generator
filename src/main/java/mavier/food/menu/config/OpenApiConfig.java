package mavier.food.menu.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Family food API",
        version = "1.0.0",
        contact = @Contact(name = "Ervins Patmalnieks",
                email = "ervins.patmalnieks@gmail.com")))
public class OpenApiConfig {
}
