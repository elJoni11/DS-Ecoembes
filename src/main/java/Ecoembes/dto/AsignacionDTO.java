package Ecoembes.dto;

import java.time.LocalDate;
import java.util.List;

public class AsignacionDTO {
    
    private String asignacionID;
    private String plantaID;
    private LocalDate fechaPrevista;
    private List<String> listaContenedores;
    private int totalEnvasesEstimados;
    private String asignador;
    private boolean notificacion;

    // --- Constructor vacío  -> obligatorio para la deserialización de JSON (Spring Boot)
    public AsignacionDTO() {
    }

    // Getters y Setters
    public String getAsignacionID() { 
    	return asignacionID; 
    }
    public void setAsignacionID(String asignacionID) { 
    	this.asignacionID = asignacionID; 
    }
    
    public String getPlantaID() { 
    	return plantaID; 
    }
    public void setPlantaID(String plantaID) { 
    	this.plantaID = plantaID; 
    }
    
    public LocalDate getFechaPrevista() { 
    	return fechaPrevista; 
    }
    public void setFechaPrevista(LocalDate fechaPrevista) { 
    	this.fechaPrevista = fechaPrevista; 
    }
    
    public List<String> getListaContenedores() { 
    	return listaContenedores; 
    }
    public void setListaContenedores(List<String> contenedorID) { 
    	this.listaContenedores = contenedorID; 
    }
    
	public int getTotalEnvasesEstimados() {
		return totalEnvasesEstimados;
	}
	public void setTotalEnvasesEstimados(int totalEnvasesEstimados) {
		this.totalEnvasesEstimados = totalEnvasesEstimados;
	}
	
	public String getAsignador() {
		return asignador;
	}
	public void setAsignador(String asignador) {
		this.asignador = asignador;
	}
	
	public boolean getNotificacion() {
		return notificacion;
	}
	public void setNotificacion(boolean notificacion) {
		this.notificacion = notificacion;
	}
}
