package Ecoembes.gateway;

import java.time.LocalDate;

public interface PlantaGatewayInterface {
	/**
     * Consulta la capacidad disponible en el sistema externo.
     * @param url URL o IP:Puerto de conexión.
     * @param fecha Fecha de la consulta.
     * @return Capacidad en toneladas.
     */
    int getCapacidad(String url, LocalDate fecha);
}
