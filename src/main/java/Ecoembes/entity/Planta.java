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
    
    // JPA crea una tabla extra automática para guardar este mapa
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "planta_capacidades", joinColumns = @JoinColumn(name = "planta_id"))
    @MapKeyColumn(name = "fecha")
    @Column(name = "capacidad")
    private Map<LocalDate, Integer> capacidadDeterminada = new ConcurrentHashMap<>(); // Map<Fecha, Capacidad>

    /** Constructor para crear/inicializar una Planta **/
    public Planta(String plantaID, String nombre, String ubicacion, Map<LocalDate, Integer> capacidadDeterminada) {
        this.plantaID = plantaID;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidadDeterminada = capacidadDeterminada;
    }
    
	// Constructor sin capacidadDeterminada (Útil antes de ser guardado en base de datos)
    public Planta(String plantaID, String nombre, String ubicacion) {
		this.plantaID = plantaID;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
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
               ", capacidadDeterminada=" + capacidadDeterminada.size() +
               '}';
    }
}
