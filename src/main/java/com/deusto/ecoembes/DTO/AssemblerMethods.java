package com.deusto.ecoembes.DTO;


import com.deusto.ecoembes.entity.*; // O el paquete donde estén tus Entidades de Dominio

public class AssemblerMethods {

    // La clase no tiene constructor público ya que todos sus métodos son estáticos.
    private AssemblerMethods() {
        // Bloquea la instanciación.
    }

    // =================================================================
    // Conversiones de Entidad (Domain) a DTO (Communication)
    // =================================================================

    public static ContenedorDTO toDTO(Contenedor contenedor) {
        ContenedorDTO dto = new ContenedorDTO();
        dto.setContenedorID(contenedor.getContenedorId());
        dto.setUbicacion(contenedor.getUbicacion());
        dto.setCapacidad(contenedor.getCapacidad());
        dto.setCodPostal(contenedor.getCodPostal());
        dto.setFechaActualizada(contenedor.getFechaActualizada());
        dto.setEnvasesEstimados(contenedor.getEnvasesEstimados());
        // Se asume que get y set de NivelLlenado manejan la conversión de tipos si fuera necesario
        dto.setNivelLlenado(contenedor.getNivelLlenado()); 
        return dto;
    }

    public static EmpleadoDTO toDTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setNombre(empleado.getNombre());
        dto.setEmail(empleado.getEmail());
        // NO mapear la password por seguridad, aunque la entidad la tenga.
        // dto.setPassword(empleado.getPassword()); 
        return dto;
    }

    public static PlantaDTO toDTO(Planta planta) {
        PlantaDTO dto = new PlantaDTO();
        dto.setPlantaID(planta.getPlantaID());
        dto.setNombre(planta.getNombre());
        dto.setUbicacion(planta.getUbicacion());
        // Mapea el Map de capacidad tal como está
        dto.setCapacidadDeterminada(planta.getCapacidadDiaria()); 
        return dto;
    }

    public static AsignacionDTO toDTO(Asignacion asignacion) {
        AsignacionDTO dto = new AsignacionDTO();
        dto.setAsignacionID(asignacion.getAsignacionID());
        // En tu entidad usaste LocalDateTime, pero el DTO pide solo Date/LocalDate
        dto.setFecha(asignacion.getFechaAsignacion().toLocalDate()); 
        
        // El ContenedorID es un String con comas en la entidad, se convierte a List<String> para el DTO
        dto.setContenedorID(java.util.Arrays.asList(asignacion.getContenedorID().split(",")));
        
        dto.setPlantaID(asignacion.getPlantaID());
        dto.setEnvasesEstimadosTotal(asignacion.getEnvasesEstimadosTotal());
        dto.setNotificacion(asignacion.getNotificacion());
        return dto;
    }

    // =================================================================
    // Conversiones de DTO (Communication) a Entidad (Domain)
    // =================================================================

    public static Contenedor toEntity(ContenedorDTO contenedorDTO) {
        // Usamos el constructor de Entidad, o setters si no hay un constructor completo
        Contenedor entity = new Contenedor(
            contenedorDTO.getContenedorID(),
            contenedorDTO.getUbicacion(),
            contenedorDTO.getCapacidad(),
            contenedorDTO.getCodPostal(),
            contenedorDTO.getFechaActualizada(),
            contenedorDTO.getEnvasesEstimados(),
            contenedorDTO.getNivelLlenado()
        );
        return entity;
    }

    public static Empleado toEntity(EmpleadoDTO empleadoDTO) {
        // Se incluye el ID, nombre, email y password.
        Empleado entity = new Empleado(
            empleadoDTO.getId(),
            empleadoDTO.getNombre(),
            empleadoDTO.getEmail(),
            empleadoDTO.getPassword()
        );
        return entity;
    }

    public static Planta toEntity(PlantaDTO plantaDTO) {
        Planta entity = new Planta(
            plantaDTO.getPlantaID(),
            plantaDTO.getNombre(),
            plantaDTO.getUbicacion(),
            plantaDTO.getCapacidadDeterminada()
        );
        return entity;
    }

    public static Asignacion toEntity(AsignacionDTO asignacionDTO) {
        // NOTA: Para este método se necesita el ID del Empleado, que NO está en el AsignacionDTO.
        // Asumimos que la lógica de negocio (Façade) lo inyectará.
        
        // Convertimos la List<String> de contenedores a un solo String separado por comas
        String contenedoresString = String.join(",", asignacionDTO.getContenedorID());
        
        Asignacion entity = new Asignacion(
            asignacionDTO.getAsignacionID(),
            // Se necesita un LocalDateTime, asumimos que se toma la hora actual al convertir
            asignacionDTO.getFecha().atStartOfDay(), 
            contenedoresString,
            asignacionDTO.getPlantaID(),
            0L, // Placeholder: El ID del Empleado DEBE ser inyectado por la Façade.
            asignacionDTO.getEnvasesEstimadosTotal(),
            asignacionDTO.getNotificacion()
        );
        return entity;
    }
}