package Ecoembes.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Contenedor {

    private String contenedorId;
    private String ubicacion;
    private int capacidad;
    private int codPostal;
    private LocalDateTime fechaConsulta; // Simulada a las 3:00
    private int envasesEstimados;
    private NivelLlenado nivelLlenado;
    private Map<LocalDate, NivelLlenado> historico = new ConcurrentHashMap<>();

    /** Constructor para crear/inicializar un Contenedor **/
    public Contenedor(String contenedorId, String ubicacion, int capacidad, int codPostal,
                      LocalDateTime fechaConsulta, int envasesEstimados, NivelLlenado nivelLlenado, Map<LocalDate, NivelLlenado> historico) {
        this.contenedorId = contenedorId;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.codPostal = codPostal;
        this.fechaConsulta = fechaConsulta;
        this.envasesEstimados = envasesEstimados;
        this.nivelLlenado = nivelLlenado;
        this.historico = historico;
    }

    // Getters
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

    public LocalDateTime getFechaConsulta() {
        return fechaConsulta;
    }

    public int getEnvasesEstimados() {
        return envasesEstimados;
    }

    public NivelLlenado getNivelLlenado() {
        return nivelLlenado;
    }
    
	public Map<LocalDate, NivelLlenado> getHistorico() {
		return historico;
	}

    // Setters para datos dinámicos
    // Usado para la actualización del sensor diario.
    public void setFechaConsulta(LocalDateTime fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
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
    
	public void setHistorico(Map<LocalDate, NivelLlenado> historico) {
		this.historico = historico;
	}

    // Método toString
    @Override
    public String toString() {
        return "Contenedor{" +
               "id='" + contenedorId + '\'' +
               ", ubicacion='" + ubicacion + '\'' +
               ", capacidad=" + capacidad +
               ", codPostal=" + codPostal +
               ", fechaConsulta=" + fechaConsulta +
               ", envasesEstimados=" + envasesEstimados +
               ", nivelLlenado=" + nivelLlenado +
               ", historico=" + historico +
               '}';
    }
}
