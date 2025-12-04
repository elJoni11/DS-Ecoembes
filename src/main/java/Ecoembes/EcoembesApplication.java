package Ecoembes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    // Escaneamos TODOS los paquetes de tu proyecto
    scanBasePackages = {
        "Ecoembes", // El paquete principal
        // Resto de paquetes
        "Ecoembes.config",
        "Ecoembes.repository",
        "Ecoembes.entity",
        "Ecoembes.dto",
        "Ecoembes.service", 
        "Ecoembes.facade"
    }
)

public class EcoembesApplication {
	
	public static void main(String[] args) {
		System.out.println("═══════════════════════════════════════════════════════════");
		System.out.println("   					SERVIDOR ECOEMBES");
		System.out.println("   		Sistema de Gestión de Reciclaje de Envases");
		System.out.println("   			Equipo DS-03 - Universidad de Deusto");
		System.out.println("═══════════════════════════════════════════════════════════");
		
		SpringApplication.run(EcoembesApplication.class, args);
	}
}
