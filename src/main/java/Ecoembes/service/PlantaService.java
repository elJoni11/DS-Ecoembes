package Ecoembes.service;

import Ecoembes.dto.AssemblerMethods;
import Ecoembes.dto.PlantaDTO;
import Ecoembes.entity.Planta;
import Ecoembes.repository.PlantaRepository;
import Ecoembes.gateway.PlantaGatewayFactory;
import Ecoembes.gateway.PlantaGatewayInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantaService {

	private final PlantaRepository plantaRepository;
	private final PlantaGatewayFactory gatewayFactory; // Inyectamos el patrón factory

    public PlantaService(PlantaRepository plantaRepository, PlantaGatewayFactory gatewayFactory) {
    	this.plantaRepository = plantaRepository;
    	this.gatewayFactory = gatewayFactory;
    }

    public List<PlantaDTO> getAllPlantas() {
        return plantaRepository.findAll().stream()
                .map(AssemblerMethods::toPlantaDTO)
                .collect(Collectors.toList());
    }

    public Integer getCapacidadPlanta(String id, String fechaString) {
        Planta p = plantaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Planta no encontrada " + id)); // 404
        
        LocalDate fecha = LocalDate.parse(fechaString, DateTimeFormatter.ISO_DATE);
        
        // --- 🔍 ZONA DE DEPURACIÓN (MIRA LA CONSOLA DE ECLIPSE) ---
        System.out.println("\n========== DEBUG PLANTA SERVICE ==========");
        System.out.println("1. Planta encontrada: " + p.getNombre() + " (ID: " + id + ")");
        System.out.println("2. Fecha que tú pides: " + fecha);
        System.out.println("3. Contenido de la base de datos para esta planta:");
        System.out.println("🔄 [PlantaService] Consultando capacidad externa para: " + p.getNombre());
        System.out.println("   -> Tipo: " + p.getTipoComunicacion());
        System.out.println("   -> URL: " + p.getUrlComunicacion());
        
        // 2. PATRÓN FACTORY: Obtener el Gateway correcto (REST o SOCKET)
        PlantaGatewayInterface gateway = gatewayFactory.createGateway(p.getTipoComunicacion());
        
        // 3. PATRÓN GATEWAY: Llamar al sistema externo
        try {
            return gateway.getCapacidad(p.getUrlComunicacion(), fecha);
        } catch (Exception e) {
            // Si falla la conexión, lanzamos error 500 o 400
            System.err.println("❌ Error en comunicación externa: " + e.getMessage());
            throw new RuntimeException("Error de comunicación con la planta externa (" + p.getNombre() + "): " + e.getMessage());
        }
    }
}