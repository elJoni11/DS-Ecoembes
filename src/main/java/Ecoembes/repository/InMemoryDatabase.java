package Ecoembes.repository;

import Ecoembes.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryDatabase {

    // Almacenes de datos
    public final Map<String, Contenedor> contenedores = new ConcurrentHashMap<>();
    public final Map<String, Planta> plantas = new ConcurrentHashMap<>();
    public final Map<String, Empleado> empleados = new ConcurrentHashMap<>();
    public final List<Asignacion> asignaciones = new CopyOnWriteArrayList<>();

    // Almacén de estado de sesión (Token -> Email del Empleado)
    public final Map<String, String> activeTokens = new ConcurrentHashMap<>();
}