package com.deusto.ecoembes;

import Ecoembes.dto.ContenedorDTO;
import Ecoembes.dto.EmpleadoDTO;
import Ecoembes.entity.NivelLlenado;
import Ecoembes.facade.*;
import Ecoembes.service.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
  
	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
	@Bean
	CommandLineRunner initData(
            EmpleadoController empleadoController, 
            ContenedorController contenedorController,
            PlantaService plantaService
    ) {
        return args -> {	
            logger.info("=================================================");
            logger.info("🌱 INICIALIZANDO DATOS DE PRUEBA ECOEMBES...");
            logger.info("=================================================");
       
            inicializarContenedores(contenedorController);
            simularLoginLogout(empleadoController);
            
            logger.info("=================================================");
            logger.info("✅ DATOS DE PRUEBA CARGADOS. API lista.");
            logger.info("=================================================");
        };
	}

    private void inicializarContenedores(ContenedorController contenedorController) {
        logger.info("   > Creando contenedores de prueba...");
        
        ContenedorDTO c1 = crearContenedor("C/ Sol, 12", 28001, 1000, 300, NivelLlenado.BAJO);
        ContenedorDTO c2 = crearContenedor("Av. America, 5", 28002, 1500, 700, NivelLlenado.MEDIO);
        contenedorController.CrearContenedor(c1);
        contenedorController.CrearContenedor(c2);
    }
    
    private ContenedorDTO crearContenedor(String ubicacion, int codPostal, int capacidad, int estimados, NivelLlenado nivel) {
        ContenedorDTO c = new ContenedorDTO();
        c.setUbicacion(ubicacion);
        c.setCodPostal(codPostal);
        c.setCapacidad(capacidad);
        c.setEnvasesEstimados(estimados);
        c.setNivelLlenado(nivel);
        return c;
    }

    /**
     * Simula el flujo de login y logout para verificar la llamada al Controller.
     */
    private void simularLoginLogout(EmpleadoController empleadoController) {
        
        logger.info("   > Simulación: Login y Logout de prueba.");
        String email = "juan@ecoembes.com";
        String password = "admin123";
        String token = null;

        try {
            // CORRECCIÓN APLICADA: Creamos el objeto Credenciales explícitamente.
            EmpleadoController.EmpleadoCredenciales credenciales = new EmpleadoController.EmpleadoCredenciales();
            credenciales.setEmail(email);
            credenciales.setPassword(password);

            token = empleadoController.IniciarSesion(credenciales);
            logger.info("   > Login exitoso (Token: {}...).", token.substring(0, 10));
            
            empleadoController.CerrarSesion(token);
            logger.info("   > Logout exitoso.");

        } catch (Exception e) {
            logger.error("❌ Fallo en la simulación de autenticación: {}", e.getMessage());
        }
    }
}