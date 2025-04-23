package at.fhv.sys.hotel.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Hotel Management System - Command Side API",
				version = "1.0.0",
				description = "Command API for Hotel Management System using CQRS and Event Sourcing",
				contact = @Contact(name = "Hotel Management Team", email = "contact@hotel-management.com"),
				license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")
		),
		servers = {
				@Server(url = "http://localhost:8082", description = "Local Command Side Server")
		}
)
public class CommandSideOpenApiConfig {
}