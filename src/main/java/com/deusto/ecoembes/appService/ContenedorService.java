package com.deusto.ecoembes.appService;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList; // Necesario para devolver listas

// Importamos las clases de dominio que ya definimos
	import com.deusto.ecoembes.entity.Contenedor; 
	import com.deusto.ecoembes.entity.NivelLlenado;
	import com.deusto.ecoembes.entity.Empleado; 

public class ContenedorService {

    // --- (1) Simulación de la "Base de Datos" en memoria (Prototipo 1) ---
    // Clave: Contenedor ID (String), Valor: Objeto Contenedor
    private final Map<String, Contenedor> repositorioContenedores;
    
    // --- Constructor ---
    public ContenedorService() {
        this.repositorioContenedores = new HashMap<>();
        // Inicializar algunos contenedores para la demo
        inicializarDatosDemo(); 
    }
    
    // Método de inicialización (Solo para Prototipo 1)
    private void inicializarDatosDemo() {
        // Contenedor 1 (Lleno/Rojo)
        Contenedor c1 = new Contenedor("C1001", "C/ Falsa 123", 1000, 28001, 
                                       LocalDate.now(), 950, NivelLlenado.ROJO);
        // Contenedor 2 (Medio/Naranja)
        Contenedor c2 = new Contenedor("C1002", "Av. Principal 45", 800, 28002, 
                                       LocalDate.now(), 600, NivelLlenado.NARANJA);
        // Contenedor 3 (Vacío/Verde)
        Contenedor c3 = new Contenedor("C1003", "Pza. España 1", 900, 28001, 
                                       LocalDate.now(), 100, NivelLlenado.VERDE);
        
        repositorioContenedores.put(c1.getContenedorId(), c1);
        repositorioContenedores.put(c2.getContenedorId(), c2);
        repositorioContenedores.put(c3.getContenedorId(), c3);
    }


    // --- (2) Funcionalidad de Actualización del Sensor (Diaria) ---
    /**
     * Actualiza la información dinámica de un contenedor (simulando la lectura del sensor).
     * @param contenedorID El ID del contenedor a actualizar.
     * @param envasesEstimados Nuevo número de envases.
     * @param nivelLlenado Nuevo nivel de llenado (VERDE, NARANJA, ROJO).
     * @return true si se actualizó, false si no se encontró el contenedor.
     */
    public boolean actualizarContenedor(String contenedorID, int envasesEstimados, NivelLlenado nivelLlenado) {
        Contenedor contenedor = repositorioContenedores.get(contenedorID);
        
        if (contenedor != null) {
            // Se actualizan los 3 atributos dinámicos
            contenedor.setEnvasesEstimados(envasesEstimados);
            contenedor.setNivelLlenado(nivelLlenado);
            contenedor.setFechaActualizada(LocalDate.now()); // Se actualiza a la fecha de hoy
            return true;
        }
        return false;
    }


    // --- (3) Funcionalidad de Creación (Personal Ecoembes) ---
    /**
     * Crea un nuevo contenedor y lo añade al repositorio. 
     * @param creador El objeto Empleado que realiza la creación (para fines de auditoría/autorización).
     * @param contenedorID ID único (se podría generar automáticamente).
     * @param ubicacion La dirección.
     * @param capacidad La capacidad máxima en envases.
     * @return El objeto Contenedor creado.
     */
    public Contenedor crearContenedor(Empleado creador, String contenedorID, String ubicacion, int capacidad) {
        // En un sistema real, se verificaría que el 'creador' tiene permisos.
        // Se inicializa con estado vacío/verde.
        Contenedor nuevoContenedor = new Contenedor(
            contenedorID, 
            ubicacion, 
            capacidad, 
            0, // Aquí se podría requerir el codPostal. Lo inicializamos a 0 por ahora.
            LocalDate.now(), 
            0, 
            NivelLlenado.VERDE
        );
        
        // Asumiendo que el ID es único (verificación a implementar)
        repositorioContenedores.put(contenedorID, nuevoContenedor);
        System.out.println("Contenedor " + contenedorID + " creado por empleado " + creador.getId());
        return nuevoContenedor;
    }


    // --- (4) Funcionalidad de Consulta por Historial/Estado ---
    // NOTA: Con la estructura actual de 'Contenedor' (solo guarda el estado actual),
    // la consulta por historial ('fechaActualizada') debe simularse o requeriría la clase 'RegistroSensor'.
    /**
     * Obtiene un contenedor por su ID y verifica si su estado coincide con los parámetros.
     * En el Prototipo 1, esto solo devuelve el estado actual.
     * @param contenedorID El ID del contenedor.
     * @param fecha La fecha de la última actualización (en P1 solo coincide si es la actual).
     * @param nivelLlenado El nivel de llenado.
     * @return El Contenedor si coincide con el estado actual, o null.
     */
    public Contenedor getContenedorByFecha(String contenedorID, LocalDate fecha, NivelLlenado nivelLlenado) {
        Contenedor contenedor = repositorioContenedores.get(contenedorID);
        
        if (contenedor != null) {
            // Simplificación para P1: solo compara la última actualización y el nivel.
            if (contenedor.getFechaActualizada().isEqual(fecha) && contenedor.getNivelLlenado() == nivelLlenado) {
                return contenedor;
            }
        }
        return null; 
    }
    
    // --- (5) Funcionalidad de Consulta por Zona (Asignación) ---
    /**
     * Obtiene una lista de contenedores que cumplen con un código postal y un nivel de llenado.
     * @param codPostal El código postal de la zona.
     * @param nivelLlenado El nivel de llenado (ej: ROJO o NARANJA).
     * @return Una lista de contenedores encontrados.
     */
    public List<Contenedor> getContenedoresByZona(int codPostal, NivelLlenado nivelLlenado) {
        // Filtramos todos los contenedores por el Cod Postal y el Nivel de Llenado
        return repositorioContenedores.values().stream()
                .filter(c -> c.getCodPostal() == codPostal)
                .filter(c -> c.getNivelLlenado() == nivelLlenado)
                .collect(Collectors.toList());
    }

    // --- Método de utilidad para la AsignacionService ---
    public Contenedor getContenedorById(String id) {
        return repositorioContenedores.get(id);
    }
    
    public List<Contenedor> getAllContenedores() {
        return new ArrayList<>(repositorioContenedores.values());
    }
}

