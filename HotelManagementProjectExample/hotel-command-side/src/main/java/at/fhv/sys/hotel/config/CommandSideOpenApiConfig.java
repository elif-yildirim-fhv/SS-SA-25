package at.fhv.sys.hotel.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

@OpenAPIDefinition(
		info = @Info(
				title = "Hotel Management System - Command Side API",
				version = "1.0.0",
				description = "Command API for Hotel Management System using CQRS and Event Sourcing. Use this interface to test and execute all available commands.",
				contact = @Contact(name = "Hotel Management Team", email = "contact@hotel-management.com"),
				license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")
		),
		servers = {
				@Server(url = "/", description = "Local Development Server"),
				@Server(url = "http://localhost:8080", description = "Local Development with explicit port")
		},
		tags = {
				@Tag(name = "Room Commands", description = "Operations for managing hotel rooms"),
				@Tag(name = "Booking Commands", description = "Operations for managing bookings"),
				@Tag(name = "Customer Commands", description = "Operations for managing customers"),
				@Tag(name = "Payment Commands", description = "Operations for handling payments")
		}
)
public class CommandSideOpenApiConfig {
}