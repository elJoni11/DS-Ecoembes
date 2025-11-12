package com.deusto.ecoembes.DTO;

import java.time.LocalDate;
import java.util.List;

public class AsignacionDTO {
    
    private String asignacionID;
    private LocalDate fecha; // Podría ser LocalDateTime, pero Date/LocalDate es más simple para DTO
    private List<String> contenedorID; // Lista de contenedores asignados
    private String plantaID;
    private long envasesEstimadosTotal; 
    private String notificacion;

    // --- Constructor vacío y con parámetros (omito para brevedad) ---

    // --- Getters y Setters ---
    public String getAsignacionID() { return asignacionID; }
    public void setAsignacionID(String asignacionID) { this.asignacionID = asignacionID; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public List<String> getContenedorID() { return contenedorID; }
    public void setContenedorID(List<String> contenedorID) { this.contenedorID = contenedorID; }
    
    public String getPlantaID() { return plantaID; }
    public void setPlantaID(String plantaID) { this.plantaID = plantaID; }
    
    public long getEnvasesEstimadosTotal() { return envasesEstimadosTotal; }
    public void setEnvasesEstimadosTotal(long envasesEstimadosTotal) { this.envasesEstimadosTotal = envasesEstimadosTotal; }
    
    public String getNotificacion() { return notificacion; }
    public void setNotificacion(String notificacion) { this.notificacion = notificacion; }
}