package Ecoembes.entity;

import java.time.LocalDate;
import java.util.List;

public class Asignacion {

	private String asignacionID;
    private String plantaID;
    private LocalDate fechaPrevista;
    private List<Contenedor> listaContenedores;
    private int totalEnvasesEstimados;
    private String asignador;
    private boolean notificacion;

    /** Constructor para registrar una asignación */
    public Asignacion(String asignacionID, String plantaID, LocalDate fechaPrevista, List<Contenedor> listaContenedores, 
    		int totalEnvasesEstimados, String asignador, boolean notificacion) {
        this.asignacionID = asignacionID;
        this.plantaID = plantaID;
        this.fechaPrevista = fechaPrevista;
        this.listaContenedores = listaContenedores;
        this.totalEnvasesEstimados = totalEnvasesEstimados;
        this.asignador = asignador;
        this.notificacion = notificacion;
    }

    // Getters (Métodos de acceso)
    public String getAsignacionID() { 
    	return asignacionID; 
    }
    
    public String getPlantaID() { 
    	return plantaID; 
    }
    
    public LocalDate getFechaPrevista() { 
    	return fechaPrevista; 
    }
    
    public List<Contenedor> getListaContenedores() {
    	return listaContenedores;
    }
    
	public int getTotalEnvasesEstimados() {
		return totalEnvasesEstimados;
	}
    
	public String getAsignador() {
		return asignador;
	}
	
	public boolean getNotificacion() {
		return notificacion;
	}

    // Setters (Métodos de modificación)
    public void setAsignacionID(String asignacionID) { 
    	this.asignacionID = asignacionID; 
    }
    
    public void setPlantaID(String plantaID) {
    	this.plantaID = plantaID; 
    }

	public void setFechaPrevista(LocalDate fechaPrevista) {
		this.fechaPrevista = fechaPrevista;
	}

    // Método toString
    @Override
    public String toString() {
        return "Asignacion{" +
               "asignacionID='" + asignacionID + '\'' +
               ", plantaID='" + plantaID + '\'' +
               ", fecha=" + fechaPrevista + 
               ", listaContenedores=" + listaContenedores.size() +
               ", totalEnvasesEstimados=" + totalEnvasesEstimados +
               ", asignador='" + asignador + '\'' +
               ", notificacion=" + notificacion +
               '}';
    }
}
