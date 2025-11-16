package Ecoembes.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class CrearContenedorRequestDTO {
    @NotEmpty
    private String contenedorId;
    @NotEmpty
    private String ubicacion;
    @Min(1)
    private int capacidad;

    public CrearContenedorRequestDTO() {
    }

    // Getters y Setters
    public String getContenedorId() {
        return contenedorId;
    }
    public void setContenedorId(String contenedorId) {
        this.contenedorId = contenedorId;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
