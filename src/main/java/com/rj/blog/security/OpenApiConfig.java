package com.rj.blog.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name= "Rajendra",
                        email = "linkwithjs@gmail.com",
                        url= "https://rajendralungeli.info.np"
                ),
                description = "Blog API with springboot security.",
                title = "Blog API",
                version = "1.0",
                license = @License(
                        name="Licence name",
                        url= "https://rajendralungeli.info.np"
                ),
                termsOfService = "Terms of service."
        ),
        servers = {
                @Server(
                        description = "LOCAL ENV",
                        url = "http://localhost:8081"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://blog.com/api"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Authentication description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
