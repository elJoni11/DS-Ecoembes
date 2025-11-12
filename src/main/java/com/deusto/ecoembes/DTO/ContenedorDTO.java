package com.deusto.ecoembes.DTO;

	import java.time.LocalDate;
	// Importamos NivelLlenado (o NivelDeLlenado) según el nombre que hayas usado en tu entidad
	import com.deusto.ecoembes.entity.NivelLlenado; 

	public class ContenedorDTO {
	    
	    private String contenedorID;
	    private String ubicacion;
	    private int capacidad;
	    private int codPostal;
	    private LocalDate fechaActualizada;
	    private int envasesEstimados;
	    private NivelLlenado nivelLlenado; // Asumiendo que has usado NivelLlenado en tu entidad

	    // --- Constructor vacío y con parámetros (omito para brevedad, pero son necesarios) ---

	    // --- Getters y Setters ---
	    public String getContenedorID() { return contenedorID; }
	    public void setContenedorID(String contenedorID) { this.contenedorID = contenedorID; }
	    
	    public String getUbicacion() { return ubicacion; }
	    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

	    public int getCapacidad() { return capacidad; }
	    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

	    public int getCodPostal() { return codPostal; }
	    public void setCodPostal(int codPostal) { this.codPostal = codPostal; }
	    
	    public LocalDate getFechaActualizada() { return fechaActualizada; }
	    public void setFechaActualizada(LocalDate fechaActualizada) { this.fechaActualizada = fechaActualizada; }

	    public int getEnvasesEstimados() { return envasesEstimados; }
	    public void setEnvasesEstimados(int envasesEstimados) { this.envasesEstimados = envasesEstimados; }

	    public NivelLlenado getNivelLlenado() { return nivelLlenado; }
	    public void setNivelLlenado(NivelLlenado nivelLlenado) { this.nivelLlenado = nivelLlenado; }
	}


