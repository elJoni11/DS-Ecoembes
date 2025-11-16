package Ecoembes.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "TokenAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name("token") // El nombre del parámetro de consulta
                                                .type(SecurityScheme.Type.APIKEY)
                                                .in(SecurityScheme.In.QUERY) // Se pasa por URL (Query)
                                )
                )
                .info(new Info()
                        .title("API Servidor Ecoembes - Prototipo 1")
                        .version("1.0.0")
                        .description("Documentación del API REST (Prototipo 1)"));
    }
}
