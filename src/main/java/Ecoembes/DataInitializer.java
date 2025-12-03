package Ecoembes;

import Ecoembes.entity.*;
import Ecoembes.repository.ContenedorRepository;
import Ecoembes.repository.EmpleadoRepository;
import Ecoembes.repository.PlantaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DataInitializer implements CommandLineRunner {

	private final ContenedorRepository contenedorRepository;
    private final PlantaRepository plantaRepository;
    private final EmpleadoRepository empleadoRepository;

    public DataInitializer(ContenedorRepository contenedorRepository,
            PlantaRepository plantaRepository,
            EmpleadoRepository empleadoRepository) {
		this.contenedorRepository = contenedorRepository;
		this.plantaRepository = plantaRepository;
		this.empleadoRepository = empleadoRepository;
		}

    @Override
    public void run(String... args) throws Exception {
        // --- Cargar Empleados ---
    	if (empleadoRepository.count() == 0) {
            Empleado emp1 = new Empleado("emp-001", "Admin Ecoembes", "admin@ecoembes.com", "password123");
            empleadoRepository.save(emp1);
        }

        // --- Cargar Plantas ---
    	if (plantaRepository.count() == 0) {
    		
    		// 1. Crear Planta PlasSB (REST)
            Planta p1 = new Planta(); // Usamos constructor vacío
            p1.setPlantaID("planta-01");
            p1.setNombre("PlasSB Ltd.");
            p1.setUbicacion("Badajoz");
            p1.setTipoComunicacion("REST");
            p1.setUrlComunicacion("http://localhost:8081");
	    	
            // 2. Crear Planta ContSocket (Socket)
            Planta p2 = new Planta(); // Usamos constructor vacío
            p2.setPlantaID("planta-02");
            p2.setNombre("ContSocket Ltd.");
            p2.setUbicacion("Cáceres");
            p2.setTipoComunicacion("SOCKET");
            p2.setUrlComunicacion("localhost:9000");
	    	
	        // NUEVA LÓGICA: Usamos el formato ISO para garantizar la igualdad del objeto
	        LocalDate baseDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE; 
	
	        for (int i = 0; i < 10; i++) {
	            // 1. Obtenemos la fecha (ej. 2025-11-19)
	            LocalDate dateObj = baseDate.plusDays(i);
	            
	            // 2. Convertimos a String canónico (ej. "2025-11-19")
	            String dateString = dateObj.format(formatter); 
	            
	            // 3. Re-parseamos el String para crear la clave del mapa (mapKey) 
	            // Esto garantiza que el objeto sea idéntico al que crea el controlador desde la URL.
	            LocalDate mapKey = LocalDate.parse(dateString, formatter);
	            
	            p1.getCapacidadDeterminada().put(mapKey, 100);
	            p2.getCapacidadDeterminada().put(mapKey, 75);
	        }
        
	        plantaRepository.save(p1);
            plantaRepository.save(p2);
    	}

        // --- Cargar Contenedores ---
    	if (contenedorRepository.count() == 0) {
	        Contenedor c1 = new Contenedor("cont-A001", "Calle Mayor, 1, Madrid", 10, 48380,
	                LocalDateTime.now().minusDays(1), 500, NivelLlenado.NARANJA, new ConcurrentHashMap<>());
	        c1.getHistorico().put(LocalDate.now().minusDays(2), NivelLlenado.VERDE);
	        c1.getHistorico().put(LocalDate.now().minusDays(1), NivelLlenado.NARANJA);
	
	
	        Contenedor c2 = new Contenedor("cont-B002", "Plaza del Sol, 5, Madrid", 12, 48420,
	                LocalDateTime.now().minusDays(1), 950, NivelLlenado.ROJO, new ConcurrentHashMap<>());
	        c2.getHistorico().put(LocalDate.now().minusDays(2), NivelLlenado.NARANJA);
	        c2.getHistorico().put(LocalDate.now().minusDays(1), NivelLlenado.ROJO);
	
	        Contenedor c3 = new Contenedor("cont-C003", "Avenida del Puerto, 20, Valencia", 10, 48850,
	                LocalDateTime.now().minusDays(1), 150, NivelLlenado.VERDE, new ConcurrentHashMap<>());
	        c3.getHistorico().put(LocalDate.now().minusDays(1), NivelLlenado.VERDE);
	
	        contenedorRepository.save(c1);
            contenedorRepository.save(c2);
            contenedorRepository.save(c3);
    	}
    }
}