package com.deusto.ecoembes.appService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// Importamos los servicios de los que dependemos (Inyección de dependencias simulada)
	import com.deusto.ecoembes.entity.Empleado;
	import com.deusto.ecoembes.entity.Contenedor;
	import com.deusto.ecoembes.entity.Asignacion;
	import com.deusto.ecoembes.entity.NivelLlenado;
	import com.deusto.ecoembes.appService.ContenedorService;
	import com.deusto.ecoembes.appService.PlantaService;
	import com.deusto.ecoembes.appService.LoginService; // Para verificar el empleado

	public class AsignacionService {

	    // --- (1) Dependencias de otros servicios ---
	    // Usamos 'final' para garantizar que las dependencias se inyectan en el constructor
	    private final ContenedorService contenedorService;
	    private final PlantaService plantaService;
	    private final List<Asignacion> repositorioAsignaciones; // Simulación de DB
	    
	    // El constructor recibe las dependencias.
	    public AsignacionService(ContenedorService contenedorService, PlantaService plantaService) {
	        this.contenedorService = contenedorService;
	        this.plantaService = plantaService;
	        this.repositorioAsignaciones = new ArrayList<>(); 
	    }


	    // --- (2) Funcionalidad de Asignación ---
	    /**
	     * Intenta asignar una lista de contenedores a una planta específica.
	     * @param empleado El Empleado que realiza la acción (auditoría).
	     * @param contenedorIDs Lista de IDs de contenedores a asignar.
	     * @param plantaID ID de la planta de destino.
	     * @return Objeto Asignacion si fue exitoso, o null si la planta no tiene capacidad.
	     */
	    public Asignacion asignarContenedores(Empleado empleado, List<String> contenedorIDs, String plantaID) {
	        
	        // 1. Obtener los contenedores y calcular el total de envases
	        List<Contenedor> contenedoresAAsignar = contenedorIDs.stream()
	            // Obtener el objeto Contenedor
	            .map(contenedorService::getContenedorById) 
	            // Filtrar: debe existir y estar lleno (ROJO)
	            .filter(c -> c != null && c.getNivelLlenado() == NivelLlenado.ROJO) 
	            .collect(Collectors.toList());

	        if (contenedoresAAsignar.isEmpty()) {
	            System.out.println("ADVERTENCIA: No hay contenedores válidos para asignar.");
	            return null;
	        }

	        // Calcular el total de envases a asignar
	        long envasesEstimadosTotal = contenedoresAAsignar.stream()
	            .mapToLong(Contenedor::getEnvasesEstimados)
	            .sum();

	        LocalDate fechaAsignacion = LocalDate.now();
	        
	        // 2. CONSULTA Y VALIDACIÓN (Usa PlantaAppService)
	        if (!plantaService.tieneCapacidadSuficiente(plantaID, (int) envasesEstimadosTotal, fechaAsignacion)) {
	            System.out.println("FALLO: La planta " + plantaID + " no tiene capacidad para " + envasesEstimadosTotal + " envases.");
	            return null;
	        }

	        // 3. ENVÍO DE NOTIFICACIÓN
	        String notificacion = enviarNotificacion(plantaID, contenedoresAAsignar.size(), (int) envasesEstimadosTotal);

	        // 4. REGISTRO: Crear el objeto Asignacion
	        Asignacion nuevaAsignacion = new Asignacion(
	            UUID.randomUUID().toString(), // ID único para la asignación
	            LocalDateTime.now(),
	            String.join(",", contenedorIDs), // Lista de IDs de contenedores
	            plantaID,
	            empleado.getId(), // ID del empleado para auditoría
	            envasesEstimadosTotal,
	            notificacion
	        );
	        
	        // 5. Simular "guardado" en DB
	        repositorioAsignaciones.add(nuevaAsignacion);
	        
	        return nuevaAsignacion;
	    }


	    // --- (3) Funcionalidad de Notificación (Método interno) ---
	    private String enviarNotificacion(String plantaID, int numContenedores, int envasesEstimadosTotal) {
	        String mensaje = String.format(
	            "NOTIFICACIÓN: Se ha asignado un lote de %d contenedores (Total estimado: %d envases) a la planta %s.",
	            numContenedores, 
	            envasesEstimadosTotal, 
	            plantaID
	        );
	        System.out.println("NOTIFICACIÓN ENVIADA a " + plantaID + ": " + mensaje);
	        return mensaje;
	    }
	    
	    // --- (4) Funcionalidad de Utilidad (Consulta de Asignaciones) ---
	    public List<Asignacion> getAsignacionesByPlanta(String plantaID) {
	        return repositorioAsignaciones.stream()
	               .filter(a -> a.getPlantaID().equals(plantaID))
	               .collect(Collectors.toList());
	    }
	}