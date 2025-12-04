package Ecoembes.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.Map;

@Component
public class PlasSBGateway implements PlantaGatewayInterface {
	
	private final RestTemplate restTemplate;

    public PlasSBGateway() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public int getCapacidad(String url, LocalDate fecha) {
        // Construir URL: http://localhost:8081/api/plassb/capacidad?fecha=2025-11-20
        String urlCompleta = url + "/api/plassb/capacidad?fecha=" + fecha.toString();
        
        System.out.println("📡 [PlasSB Gateway] GET " + urlCompleta);
        
        try {
            // Hacemos la petición y esperamos un Map (JSON)
            Map<String, Object> respuesta = restTemplate.getForObject(urlCompleta, Map.class);
            
            if (respuesta != null && respuesta.containsKey("capacidadDisponible")) {
                return (int) respuesta.get("capacidadDisponible");
            }
        } catch (Exception e) {
            System.err.println("❌ Error conectando con PlasSB: " + e.getMessage());
        }
        
        throw new RuntimeException("No se pudo obtener capacidad de PlasSB");
    }
}
