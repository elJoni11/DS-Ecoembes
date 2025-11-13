package Ecoembes.service;

import Ecoembes.dto.ContenedorDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Servicio para la gestión de contenedores
 */
@Service
public class ContenedorService {
    
    // Simulación de base de datos
    private List<ContenedorDTO> contenedores = new ArrayList<>();
    
    public ContenedorService() {
        // Datos de ejemplo
        inicializarDatos();
    }
    
    private void inicializarDatos() {
        // Añadir algunos contenedores de ejemplo
        ContenedorDTO c1 = new ContenedorDTO();
        c1.setContenedorID("CONT-001");
        c1.setUbicacion("Calle Mayor 10");
        c1.setCodPostal(28001);
        c1.setCapacidad(1000);
        c1.setEnvasesEstimados(450);
        c1.setFechaActualizada(LocalDate.now());
        contenedores.add(c1);
    }
    
    /**
     * Crea un nuevo contenedor
     */
    public ContenedorDTO crearContenedor(ContenedorDTO contenedor) {
        // Generar ID si no existe
        if (contenedor.getContenedorID() == null || contenedor.getContenedorID().isEmpty()) {
            contenedor.setContenedorID("CONT-" + (contenedores.size() + 1));
        }
        
        // Establecer fecha de actualización
        contenedor.setFechaActualizada(LocalDate.now());
        
        // Guardar en la "base de datos"
        contenedores.add(contenedor);
        
        return contenedor;
    }
    
    /**
     * Actualiza un contenedor existente
     */
    public ContenedorDTO actualizarContenedor(ContenedorDTO contenedor) {
        for (int i = 0; i < contenedores.size(); i++) {
            if (contenedores.get(i).getContenedorID().equals(contenedor.getContenedorID())) {
                contenedor.setFechaActualizada(LocalDate.now());
                contenedores.set(i, contenedor);
                return contenedor;
            }
        }
        throw new IllegalArgumentException("Contenedor no encontrado: " + contenedor.getContenedorID());
    }
    
    /**
     * Obtiene contenedores por código postal (zona)
     */
    public List<ContenedorDTO> getContenedoresByZona(int codPostal) {
        return contenedores.stream()
            .filter(c -> c.getCodPostal() == codPostal)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene contenedores actualizados en una fecha específica
     */
    public List<ContenedorDTO> getContenedorByFecha(LocalDate fecha) {
        return contenedores.stream()
            .filter(c -> c.getFechaActualizada() != null && c.getFechaActualizada().equals(fecha))
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un contenedor por ID
     */
    public ContenedorDTO getContenedorById(String id) {
        return contenedores.stream()
            .filter(c -> c.getContenedorID().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Obtiene todos los contenedores
     */
    public List<ContenedorDTO> getAllContenedores() {
        return new ArrayList<>(contenedores);
    }
}
