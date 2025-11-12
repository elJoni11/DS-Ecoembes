# DS-Ecoembes - Patrón Facade

## 📂 Estructura del Proyecto

```
DS-Ecoembes/
└── src/main/java/Ecoembes/
    ├── dto/                           # Data Transfer Objects
    │   ├── AsignacionDTO.java        ✅ Existente
    │   ├── AssemblerMethods.java     ✅ Existente
    │   ├── ContenedorDTO.java        ✅ Existente
    │   └── EmpleadoDTO.java          ✅ Existente
    │
    ├── entity/                        # Entidades del dominio
    │   └── NivelLlenado.java         🆕 Nuevo (enum)
    │
    ├── service/                       # Servicios de aplicación
    │   ├── AsignacionService.java    🆕 Nuevo
    │   ├── ContenedorService.java    🆕 Nuevo
    │   ├── EmpleadoService.java      🆕 Nuevo
    │   ├── LoginService.java         🆕 Nuevo
    │   └── PlantaService.java        🆕 Nuevo
    │
    ├── facade/                        # Controllers (Patrón Facade)
    │   ├── AsignacionController.java 🆕 Nuevo
    │   ├── ContenedorController.java 🆕 Nuevo
    │   ├── EmpleadoController.java   🆕 Nuevo
    │   └── LoginController.java      🆕 Nuevo
    │
    └── EjemploUsoFacade.java         🆕 Nuevo (clase de demostración)
```

## 🎯 ¿Qué se ha implementado?

### ✅ DTOs (Ya existían)
- **AsignacionDTO**: Transferencia de datos de asignaciones
- **ContenedorDTO**: Transferencia de datos de contenedores
- **EmpleadoDTO**: Transferencia de datos de empleados
- **AssemblerMethods**: Métodos de conversión entre entidades y DTOs

### 🆕 Entity
- **NivelLlenado**: Enumeración para niveles de llenado (VACIO, BAJO, MEDIO, ALTO, LLENO)

### 🆕 Services (Lógica de negocio)
- **ContenedorService**: CRUD de contenedores, búsquedas por zona y fecha
- **EmpleadoService**: Autenticación y gestión de empleados
- **LoginService**: Generación y validación de tokens de sesión
- **PlantaService**: Gestión de capacidad de plantas
- **AsignacionService**: Asignación de contenedores a plantas y notificaciones

### 🆕 Facade (Controllers)
- **ContenedorController**: Interfaz simplificada para gestión de contenedores
- **EmpleadoController**: Coordina autenticación (EmpleadoService + LoginService)
- **AsignacionController**: Coordina asignaciones (AsignacionService + PlantaService + LoginService)
- **LoginController**: Gestión de tokens de sesión

## 🚀 Cómo usar el Facade

### 1. Inicio de Sesión Completo

```java
EmpleadoController empleadoController = new EmpleadoController();

// Una sola llamada coordina EmpleadoService + LoginService
String token = empleadoController.IniciarSesion("juan@ecoembes.com", "admin123");
// ✓ Autentica empleado
// ✓ Genera token automáticamente
```

### 2. Crear Contenedor

```java
ContenedorController contenedorController = new ContenedorController();

ContenedorDTO contenedor = new ContenedorDTO();
contenedor.setUbicacion("Calle Mayor 15");
contenedor.setCodPostal(28013);
contenedor.setCapacidad(1000);
contenedor.setNivelLlenado(NivelLlenado.MEDIO);

ContenedorDTO creado = contenedorController.CrearContenedor(contenedor);
// ✓ Valida datos
// ✓ Establece fecha de actualización
// ✓ Genera ID automáticamente
```

### 3. Asignar Contenedor a Planta (Operación compleja)

```java
AsignacionController asignacionController = new AsignacionController();

AsignacionDTO asignacion = asignacionController.AsignarContenedor(
    "CONT-001",      // ID del contenedor
    "PLANTA-001",    // ID de la planta
    token            // Token de sesión
);

// El AsignacionController coordina automáticamente:
// 1. ✅ Validar token (LoginService)
// 2. ✅ Verificar capacidad de planta (PlantaService)
// 3. ✅ Asignar contenedor (AsignacionService)
// 4. ✅ Reducir capacidad de planta (PlantaService)
// 5. ✅ Enviar notificación (AsignacionService)
```

### 4. Consultar Contenedores por Zona

```java
List<ContenedorDTO> contenedores = contenedorController.getContenedoresByZona(28013);

for (ContenedorDTO c : contenedores) {
    System.out.println(c.getContenedorID() + " - " + c.getUbicacion());
}
```

### 5. Cerrar Sesión

```java
empleadoController.CerrarSesion(token);
// ✓ Valida token
// ✓ Cierra sesión en EmpleadoService
// ✓ Invalida token en LoginService
```

## 🎮 Ejecutar el Ejemplo

Para probar todo el sistema:

```bash
# Compilar
./gradlew build

# Ejecutar ejemplo
java -cp build/classes/java/main Ecoembes.EjemploUsoFacade
```

O desde tu IDE:
1. Abrir `Ecoembes/EjemploUsoFacade.java`
2. Click derecho → Run

## 📋 Características de los Controllers

### ContenedorController
| Método | Descripción |
|--------|-------------|
| `CrearContenedor(contenedor)` | Crea un contenedor con validaciones |
| `ActualizarContenedor(contenedor)` | Actualiza un contenedor existente |
| `getContenedoresByZona(codPostal)` | Busca por código postal |
| `getContenedorByFecha(fecha)` | Busca por fecha de actualización |
| `getContenedorById(id)` | Obtiene un contenedor específico |

### EmpleadoController
| Método | Descripción |
|--------|-------------|
| `IniciarSesion(email, password)` | Login completo (autentica + genera token) |
| `CerrarSesion(token)` | Cierra sesión y elimina token |
| `getEmpleadoByEmail(email, token)` | Obtiene empleado (requiere autenticación) |
| `getEmpleadoById(id, token)` | Obtiene empleado por ID |

### AsignacionController
| Método | Descripción |
|--------|-------------|
| `AsignarContenedor(contenedorID, plantaID, token)` | Asignación simple |
| `AsignarContenedores(lista, plantaID, token)` | Asignación múltiple |
| `EnviarNotificacion(asignacion, token)` | Reenvía notificación |
| `getAsignacionesByPlanta(plantaID, token)` | Consulta por planta |
| `getAsignacionesByFecha(fecha, token)` | Consulta por fecha |

### LoginController
| Método | Descripción |
|--------|-------------|
| `generarToken(empleado)` | Genera token de sesión |
| `validarToken(token)` | Verifica si token es válido |
| `invalidarToken(token)` | Elimina token del sistema |
| `getEmpleadoIdFromToken(token)` | Obtiene ID del empleado del token |

## 🔒 Seguridad

Todos los métodos de los Controllers (excepto login) validan el token:

```java
if (!loginService.validarToken(token)) {
    throw new SecurityException("Token inválido o expirado");
}
```

Los tokens expiran después de **60 minutos** de inactividad.

## 💡 Ventajas del Patrón Facade

### Antes del Facade (sin Controllers)
```java
// Cliente necesita conocer y coordinar 3 servicios
EmpleadoService empleadoService = new EmpleadoService();
LoginService loginService = new LoginService();
PlantaService plantaService = new PlantaService();
AsignacionService asignacionService = new AsignacionService();

// 1. Autenticar
EmpleadoDTO empleado = empleadoService.iniciarSesion(email, pass);
if (empleado == null) throw new Exception("Error");

// 2. Generar token
String token = loginService.generarToken(empleado);

// 3. Verificar capacidad
int capacidad = plantaService.getCapacidad(plantaID);
if (capacidad <= 0) throw new Exception("Sin capacidad");

// 4. Asignar
AsignacionDTO asignacion = asignacionService.asignarContenedor(...);

// 5. Reducir capacidad
plantaService.reducirCapacidad(plantaID, 1);

// 6. Notificar
asignacionService.enviarNotificacion(asignacion);
```

### Con Facade (Controllers)
```java
// Cliente solo necesita conocer el Controller
AsignacionController controller = new AsignacionController();

// Todo se coordina automáticamente
AsignacionDTO asignacion = controller.AsignarContenedor(
    contenedorID, 
    plantaID, 
    token
);
```

## 🧪 Datos de Prueba

El sistema viene con datos precargados:

### Empleados
- **Email**: juan@ecoembes.com | **Password**: admin123
- **Email**: maria@ecoembes.com | **Password**: user123

### Plantas
- PLANTA-001: Capacidad 500
- PLANTA-002: Capacidad 750
- PLANTA-003: Capacidad 1000
- PLANTA-004: Capacidad 300

## 📚 Documentación Adicional

- Los **DTOs** ya existían en tu proyecto y se mantienen sin cambios
- Los **Services** implementan la lógica de negocio con datos en memoria
- Los **Controllers** (Facade) coordinan los servicios y simplifican la API
- El **EjemploUsoFacade** muestra 10 casos de uso completos

## 🔧 Próximos Pasos

1. ✅ Compilar el proyecto: `./gradlew build`
2. ✅ Ejecutar el ejemplo: `EjemploUsoFacade.java`
3. ✅ Integrar con tu interfaz de usuario
4. 🔄 Reemplazar almacenamiento en memoria por base de datos real
5. 🔄 Añadir más validaciones según requisitos
6. 🔄 Implementar persistencia JPA/Hibernate

## ❓ Preguntas Frecuentes

**P: ¿Los Controllers reemplazan a los Services?**  
R: No, los Controllers **usan** los Services. Son complementarios.

**P: ¿Por qué AsignacionController necesita 3 servicios?**  
R: Porque coordina: validación de token (Login), verificación de capacidad (Planta), y asignación (Asignacion).

**P: ¿Puedo añadir más métodos a los Controllers?**  
R: ¡Sí! Extiende las clases o añade nuevos métodos según tus necesidades.

**P: ¿Los Services tienen acceso a base de datos?**  
R: Actualmente usan datos en memoria. Debes implementar acceso a BD con JPA/JDBC según tu arquitectura.

---

**Proyecto**: DS-Ecoembes  
**Patrón**: Facade (Controllers)  
**Estado**: ✅ Implementado y funcional
