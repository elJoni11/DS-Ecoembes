package Ecoembes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    // Ya no necesitamos 'exclude' porque quitamos 'data-jpa' del gradle
    
    // Escaneamos TODOS los paquetes de tu proyecto
    scanBasePackages = {
        "com.deusto.ecoembes", // El paquete principal
        "Ecoembes.config",              // Los otros paquetes
        "Ecoembes.repository",
        "Ecoembes.entity",
        "Ecoembes.dto",
        "Ecoembes.service",
        "Ecoembes.facade"
    }
)

public class EcoembesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcoembesApplication.class, args);
	}
}
