package Ecoembes.gateway;

import org.springframework.stereotype.Component;

@Component
public class PlantaGatewayFactory {

    public PlantaGatewayInterface createGateway(String tipoComunicacion) {
    	if (tipoComunicacion == null) {
            throw new IllegalArgumentException("El tipo de comunicación es nulo");
        }

        // Selección simple (Switch-Case)
        switch (tipoComunicacion.toUpperCase()) {
            case "REST":
                return new PlasSBGateway();
            case "SOCKET":
                return new ContSocketGateway();
            default:
                throw new IllegalArgumentException("Tipo de comunicación desconocido: " + tipoComunicacion);
        }
    }
}
