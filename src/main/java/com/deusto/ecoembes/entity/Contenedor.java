package com.deusto.ecoembes.entity;

import java.time.LocalDate;

public class Contenedor {

    // Atributos de identificación y ubicación
    private String contenedorId; // Identificador único del contenedor
    private String ubicacion;    // Dirección del contenedor
    private int capacidad;     // Capacidad inicial
    private int codPostal;      // Necesario para la consulta por zona

    // Atributos dinámicos (estado actual reportado por el sensor)
    private LocalDate fechaActualizada; // Fecha de la última actualización del sensor (simulada a las 3:00)
    private int envasesEstimados;        // Número estimado de envases que contiene
    private NivelLlenado nivelLlenado; // Estado actual (Verde, Naranja o Rojo)


    // --- Constructor Completo ---
    /**
     * Constructor para crear/inicializar un Contenedor.
     */
    public Contenedor(String contenedorId, String ubicacion, int capacidad, int codPostal,
                      LocalDate fechaActualizada, int envasesEstimados, NivelLlenado nivelLlenado) {
        this.contenedorId = contenedorId;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.codPostal = codPostal;
        this.fechaActualizada = fechaActualizada;
        this.envasesEstimados = envasesEstimados;
        this.nivelLlenado = nivelLlenado;
    }


    // --- Getters (Métodos de acceso) ---
    public String getContenedorId() {
        return contenedorId;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public LocalDate getFechaActualizada() {
        return fechaActualizada;
    }

    public int getEnvasesEstimados() {
        return envasesEstimados;
    }

    public NivelLlenado getNivelLlenado() {
        return nivelLlenado;
    }

    // --- Setters (Métodos de modificación) ---
    // Usado para la actualización del sensor diario.
    public void setFechaActualizada(LocalDate fechaActualizada) {
        this.fechaActualizada = fechaActualizada;
    }

    // Usado para la actualización del sensor diario.
    public void setEnvasesEstimados(int envasesEstimados) {
        this.envasesEstimados = envasesEstimados;
    }

    // Usado para la actualización del sensor diario.
    public void setNivelLlenado(NivelLlenado nivelLlenado) {
        this.nivelLlenado = nivelLlenado;
    }
    
    // Setters para datos estáticos (por si acaso se necesitan actualizar)
    public void setContenedorId(String contenedorId) {
        this.contenedorId = contenedorId;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }


    // --- Método toString (Útil para imprimir el objeto) ---
    @Override
    public String toString() {
        return "Contenedor{" +
               "id='" + contenedorId + '\'' +
               ", ubicacion='" + ubicacion + '\'' +
               ", capacidad=" + capacidad +
               ", codPostal=" + codPostal +
               ", fechaActualizada=" + fechaActualizada +
               ", envasesEstimados=" + envasesEstimados +
               ", nivelLlenado=" + nivelLlenado +
               '}';
    }
}


