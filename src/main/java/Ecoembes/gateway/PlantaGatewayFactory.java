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
        if ("REST".equalsIgnoreCase(tipoComunicacion)) {
            return plasSBGateway;
        } else if ("SOCKET".equalsIgnoreCase(tipoComunicacion)) {
            return contSocketGateway;
        }
        throw new IllegalArgumentException("Tipo de comunicación desconocido: " + tipoComunicacion);
    }
}
