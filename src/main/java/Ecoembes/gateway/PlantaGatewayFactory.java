package Ecoembes.gateway;

import org.springframework.stereotype.Component;

@Component
public class PlantaGatewayFactory {

	private final PlasSBGateway plasSBGateway;
    private final ContSocketGateway contSocketGateway;

    public PlantaGatewayFactory(PlasSBGateway plasSBGateway, ContSocketGateway contSocketGateway) {
        this.plasSBGateway = plasSBGateway;
        this.contSocketGateway = contSocketGateway;
    }

    public PlantaGatewayInterface getGateway(String tipoComunicacion) {
    	if (tipoComunicacion == null) {
            throw new IllegalArgumentException("El tipo de comunicación es nulo");
        }

        // Selección simple (Switch-Case)
        switch (tipoComunicacion.toUpperCase()) {
            case "REST":
                return plasSBGateway;
            case "SOCKET":
                return contSocketGateway;
            default:
                throw new IllegalArgumentException("Tipo de comunicación desconocido: " + tipoComunicacion);
        }
    }
}
