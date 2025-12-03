package Ecoembes.entity;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.persistence.*;

@Entity // Indica que se guarda en BD
@Table(name = "plantas")
public class Planta {
	
	@Id // Clave primaria
    private String plantaID;
	
    private String nombre;   
    private String ubicacion;
    private String tipoComunicacion; // "REST" o "SOCKET"
    private String urlComunicacion;  // "http://localhost:8081" o "localhost:9000"
    
    // JPA crea una tabla extra automática para guardar este mapa
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "planta_capacidades", joinColumns = @JoinColumn(name = "planta_id"))
    @MapKeyColumn(name = "fecha")
    @Column(name = "capacidad")
    private Map<LocalDate, Integer> capacidadDeterminada = new ConcurrentHashMap<>(); // Map<Fecha, Capacidad>

    /** Constructor para crear/inicializar una Planta **/
    public Planta(String plantaID, String nombre, String ubicacion, String tipoComunicacion, String urlComunicacion) {
        this.plantaID = plantaID;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tipoComunicacion = tipoComunicacion;
        this.urlComunicacion = urlComunicacion;
        // El mapa se inicia vacío y se rellena luego
        this.capacidadDeterminada = new ConcurrentHashMap<>();
    }
    
    /** Constructor vacío **/
    public Planta() {
	}

    // Getters
    public String getPlantaID() { 
    	return plantaID; 
    }
    
    public String getNombre() { 
    	return nombre; 
    }
    
    public String getUbicacion() { 
    	return ubicacion; 
    }
    
    public String getTipoComunicacion() {
    	return tipoComunicacion;
    }
    
	public String getUrlComunicacion() {
		return urlComunicacion;
	}
    
    public Map<LocalDate, Integer> getCapacidadDeterminada() { 
    	return capacidadDeterminada; 
    }
    
    // Método útil para consultar la capacidad específica
    public int getCapacidadParaFecha(LocalDate fecha) {
        return capacidadDeterminada.getOrDefault(fecha, 0);
    }

    // Setters
    public void setPlantaID(String plantaID) { 
    	this.plantaID = plantaID; 
    }
    
    public void setNombre(String nombre) { 
    	this.nombre = nombre; 
    }
    
    public void setUbicacion(String ubicacion) { 
    	this.ubicacion = ubicacion; 
    }
    
	public void setTipoComunicacion(String tipoComunicacion) {
		this.tipoComunicacion = tipoComunicacion;
	}
	
	public void setUrlComunicacion(String urlComunicacion) {
		this.urlComunicacion = urlComunicacion;
	}
    
    public void setcapacidadDeterminada(Map<LocalDate, Integer> capacidadDeterminada) { 
    	this.capacidadDeterminada = capacidadDeterminada; 
    }

    // Método toString()
    @Override
    public String toString() {
        return "Planta{" +
               "plantaID='" + plantaID + '\'' +
               ", nombre='" + nombre + '\'' +
               ", ubicacion='" + ubicacion + '\'' +
               ", tipoComunicacion='" + tipoComunicacion + '\'' +
               ", urlComunicacion='" + urlComunicacion + '\'' +
               ", capacidadDeterminada=" + capacidadDeterminada.size() +
               '}';
    }
}
