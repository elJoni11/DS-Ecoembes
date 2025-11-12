package Ecoembes.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Asignacion {

	// Atributos de Identificación y Trazabilidad
    private String asignacionID;        // Identificador único de la asignación.
    private LocalDateTime fechaAsignacion; // Momento exacto en que se realiza la asignación.

    // Atributos de Relación (Foreign Keys)
    private String contenedorID;        // ID del Contenedor asignado (o se podría usar una lista de IDs).
    private String plantaID;            // ID de la Planta de reciclaje destino.
    private Long empleadoID;            // ID del Empleado que realizó la asignación (para auditoría).

    // Atributos de Resumen/Datos Operacionales
    private long envasesEstimadosTotal; // Cantidad total de envases asignados (requerido para la notificación).
    private String notificacion;        // El contenido de la notificación enviada a la planta.


    // --- Constructor Completo ---
    /**
     * Constructor para registrar una asignación.
     */
    public Asignacion(String asignacionID, LocalDateTime fechaAsignacion, String contenedorID, String plantaID, 
                      Long empleadoID, long envasesEstimadosTotal, String notificacion) {
        this.asignacionID = asignacionID;
        this.fechaAsignacion = fechaAsignacion;
        this.contenedorID = contenedorID;
        this.plantaID = plantaID;
        this.empleadoID = empleadoID;
        this.envasesEstimadosTotal = envasesEstimadosTotal;
        this.notificacion = notificacion;
    }
    
    // --- Constructor Básico (para crear el objeto antes de tener el ID) ---
    public Asignacion(LocalDateTime fechaAsignacion, String contenedorID, String plantaID, 
                      Long empleadoID, long envasesEstimadosTotal, String notificacion) {
        this.fechaAsignacion = fechaAsignacion;
        this.contenedorID = contenedorID;
        this.plantaID = plantaID;
        this.empleadoID = empleadoID;
        this.envasesEstimadosTotal = envasesEstimadosTotal;
        this.notificacion = notificacion;
    }


    // --- Getters (Métodos de acceso) ---
    public String getAsignacionID() { return asignacionID; }
    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public String getContenedorID() { return contenedorID; }
    public String getPlantaID() { return plantaID; }
    public Long getEmpleadoID() { return empleadoID; }
    public long getEnvasesEstimadosTotal() { return envasesEstimadosTotal; }
    public String getNotificacion() { return notificacion; }


    // --- Setters (Métodos de modificación) ---
    public void setAsignacionID(String asignacionID) { this.asignacionID = asignacionID; }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public void setContenedorID(String contenedorID) { this.contenedorID = contenedorID; }
    public void setPlantaID(String plantaID) { this.plantaID = plantaID; }
    public void setEmpleadoID(Long empleadoID) { this.empleadoID = empleadoID; }
    public void setEnvasesEstimadosTotal(long envasesEstimadosTotal) { this.envasesEstimadosTotal = envasesEstimadosTotal; }
    public void setNotificacion(String notificacion) { this.notificacion = notificacion; }


    // --- Método toString (Útil para imprimir el objeto) ---
    @Override
    public String toString() {
        return "Asignacion{" +
               "asignacionID='" + asignacionID + '\'' +
               ", fecha=" + fechaAsignacion.toLocalDate() + // Solo la fecha es más limpia
               ", empleadoID=" + empleadoID +
               ", plantaID='" + plantaID + '\'' +
               ", totalEnvases=" + envasesEstimadosTotal +
               '}';
    }

}
