package com.deusto.ecoembes.entity;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class Planta {
	// 1. Atributos
    private String plantaID; 
    private String nombre;   
    private String ubicacion; 
    private Map<LocalDate, Integer> capacidadDiaria; // Map<Fecha, Capacidad>


    // 2. Constructores
    public Planta(String plantaID, String nombre, String ubicacion, Map<LocalDate, Integer> capacidadDiaria) {
        this.plantaID = plantaID;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidadDiaria = capacidadDiaria;
    }

    public Planta(String plantaID, String nombre, String ubicacion) {
        this.plantaID = plantaID;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidadDiaria = new HashMap<>(); 
    }


    // 3. Getters
    public String getPlantaID() { return plantaID; }
    public String getNombre() { return nombre; }
    public String getUbicacion() { return ubicacion; }
    public Map<LocalDate, Integer> getCapacidadDiaria() { return capacidadDiaria; }
    
    // Método útil para consultar la capacidad específica
    public int getCapacidadParaFecha(LocalDate fecha) {
        return capacidadDiaria.getOrDefault(fecha, 0);
    }


    // 4. Setters
    public void setPlantaID(String plantaID) { this.plantaID = plantaID; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setCapacidadDiaria(Map<LocalDate, Integer> capacidadDiaria) { this.capacidadDiaria = capacidadDiaria; }
    
    // Método para agregar o actualizar capacidad
    public void actualizarCapacidad(LocalDate fecha, int capacidad) {
        this.capacidadDiaria.put(fecha, capacidad);
    }


    // 5. Método toString()
    @Override
    public String toString() {
        return "Planta{" +
               "plantaID='" + plantaID + '\'' +
               ", nombre='" + nombre + '\'' +
               ", ubicacion='" + ubicacion + '\'' +
               ", numDiasCapacidad=" + capacidadDiaria.size() +
               '}';
    }
}

