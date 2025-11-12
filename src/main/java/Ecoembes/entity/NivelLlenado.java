package Ecoembes.entity;

/**
 * Enumeración que representa los niveles de llenado de un contenedor
 */
public enum NivelLlenado {
    VACIO("Vacío", 0, 20),
    BAJO("Bajo", 21, 40),
    MEDIO("Medio", 41, 60),
    ALTO("Alto", 61, 80),
    LLENO("Lleno", 81, 100);
    
    private final String descripcion;
    private final int porcentajeMin;
    private final int porcentajeMax;
    
    NivelLlenado(String descripcion, int porcentajeMin, int porcentajeMax) {
        this.descripcion = descripcion;
        this.porcentajeMin = porcentajeMin;
        this.porcentajeMax = porcentajeMax;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public int getPorcentajeMin() {
        return porcentajeMin;
    }
    
    public int getPorcentajeMax() {
        return porcentajeMax;
    }
    
    /**
     * Obtiene el nivel de llenado según un porcentaje
     */
    public static NivelLlenado fromPorcentaje(int porcentaje) {
        if (porcentaje <= 20) return VACIO;
        if (porcentaje <= 40) return BAJO;
        if (porcentaje <= 60) return MEDIO;
        if (porcentaje <= 80) return ALTO;
        return LLENO;
    }
}
