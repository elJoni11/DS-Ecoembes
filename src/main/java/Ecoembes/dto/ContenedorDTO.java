package Ecoembes.dto;

import Ecoembes.entity.NivelLlenado;
import java.time.LocalDateTime;

public class ContenedorDTO {
    
    private String contenedorID;
    private String ubicacion;
    private int capacidad;
    private int codPostal;
    private LocalDateTime fechaConsulta;
    private int envasesEstimados;
    private NivelLlenado nivelLlenado;

    // Constructor vacío
    public ContenedorDTO() {
    }

    // --- Getters y Setters ---
    public String getContenedorID() {
        return contenedorID;
    }
    public void setContenedorID(String contenedorID) {
        this.contenedorID = contenedorID;
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
    
	public int getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(int codPostal) {
		this.codPostal = codPostal;
	}

	public LocalDateTime getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(LocalDateTime fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
    
    public int getEnvasesEstimados() {
        return envasesEstimados;
    }
    public void setEnvasesEstimados(int envasesEstimados) {
        this.envasesEstimados = envasesEstimados;
    }
    
    public NivelLlenado getNivelLlenado() {
        return nivelLlenado;
    }
    public void setNivelLlenado(NivelLlenado nivelLlenado) {
        this.nivelLlenado = nivelLlenado;
    }
}
