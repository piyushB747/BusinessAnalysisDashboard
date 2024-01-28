package intelli.uno.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
		info = @Info(
				title = "Erprmwise Bussiness Intelligence",
				description = " IN THIS SERVICE WE ARE GOING TO SEND AND RECEIVED WHATSAPP MESSAGES TO WATI API ",
				version = "v1.1",
				contact = @Contact(
						name = "Singh Piyush",
						email = "singhrajpiyush@gmail.com",
						url = "https://intelli.uno/"
				),
				license = @License(
						name = "Unopoint Whatsapp Service",
						url = "https://intelli.uno/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Whatsapp Integration With Servicestable",
				url = "https://intelli.uno/"
		)
)
@Configuration
public class ConfigOpenApi {

	
}
