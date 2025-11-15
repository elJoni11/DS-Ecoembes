package Ecoembes.dto;

import Ecoembes.entity.*;
import java.util.stream.Collectors;

public class AssemblerMethods {

    // La clase no tiene constructor público ya que todos sus métodos son estáticos.
    private AssemblerMethods() {
        // Bloquea la instanciación.
    }

    // Método estático
    public static ContenedorDTO toContenedorDTO(Contenedor entity) {
        ContenedorDTO dto = new ContenedorDTO();
        dto.setContenedorID(entity.getContenedorId());
        dto.setUbicacion(entity.getUbicacion());
        dto.setCapacidad(entity.getCapacidad());
        dto.setFechaConsulta(entity.getFechaConsulta());
        dto.setEnvasesEstimados(entity.getEnvasesEstimados());
        dto.setNivelLlenado(entity.getNivelLlenado());
        return dto;
    }

    // Método estático
    public static PlantaDTO toPlantaDTO(Planta entity) {
        PlantaDTO dto = new PlantaDTO();
        dto.setPlantaID(entity.getPlantaID());
        dto.setNombre(entity.getNombre());
        dto.setUbicacion(entity.getUbicacion());
        dto.setCapacidadDeterminada(entity.getCapacidadDeterminada());
        return dto;
    }

    // Método estático
    public static AsignacionDTO toAsignacionDTO(Asignacion entity) {
        AsignacionDTO dto = new AsignacionDTO();
        dto.setAsignacionID(entity.getAsignacionID());
        dto.setPlantaID(entity.getPlantaID());
        dto.setFechaPrevista(entity.getFechaPrevista());
        dto.setTotalEnvasesEstimados(entity.getTotalEnvasesEstimados());
        dto.setAsignador(entity.getAsignador());
        dto.setNotificacion(entity.getNotificacion());
        dto.setListaContenedores(entity.getListaContenedores().stream()
                .map(Contenedor::getContenedorId)
                .collect(Collectors.toList()));
        return dto;
    }
}