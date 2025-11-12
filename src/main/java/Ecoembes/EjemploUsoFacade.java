package Ecoembes;

import Ecoembes.facade.*;
import Ecoembes.dto.*;
import Ecoembes.entity.NivelLlenado;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase de ejemplo que demuestra el uso de los Controllers (Patrón Facade)
 * adaptado al proyecto DS-Ecoembes
 */
public class EjemploUsoFacade {
    
    public static void main(String[] args) {
        
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE GESTIÓN ECOEMBES - DEMO FACADE        ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");
        
        try {
            // ====================================================
            // INICIALIZAR CONTROLLERS
            // ====================================================
            System.out.println("📦 Inicializando Controllers (Facade Pattern)...");
            
            EmpleadoController empleadoController = new EmpleadoController();
            LoginController loginController = new LoginController();
            ContenedorController contenedorController = new ContenedorController();
            AsignacionController asignacionController = new AsignacionController();
            
            System.out.println("✅ Controllers inicializados correctamente\n");
            
            // ====================================================
            // EJEMPLO 1: Inicio de sesión
            // ====================================================
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 1. INICIO DE SESIÓN                             │");
            System.out.println("└─────────────────────────────────────────────────┘");
            
            String email = "juan@ecoembes.com";
            String password = "admin123";
            
            System.out.println("Intentando login con:");
            System.out.println("  Email: " + email);
            System.out.println("  Password: ********");
            
            // El EmpleadoController coordina EmpleadoService + LoginService
            String token = empleadoController.IniciarSesion(email, password);
            
            System.out.println("✅ Sesión iniciada correctamente");
            System.out.println("   Token: " + token);
            System.out.println();
            
            // ====================================================
            // EJEMPLO 2: Validar token
            // ====================================================
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 2. VALIDAR TOKEN                                │");
            System.out.println("└─────────────────────────────────────────────────┘");
            
            boolean tokenValido = loginController.validarToken(token);
            System.out.println("✅ Token válido: " + (tokenValido ? "SÍ ✓" : "NO ✗"));
            System.out.println();
            
            // ====================================================
            // EJEMPLO 3: Crear contenedores
            // ====================================================
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 3. CREAR CONTENEDORES                           │");
            System.out.println("└─────────────────────────────────────────────────┘");
            
            // Contenedor 1
            ContenedorDTO contenedor1 = new ContenedorDTO();
            contenedor1.setUbicacion("Calle Mayor 15, Madrid");
            contenedor1.setCodPostal(28013);
            contenedor1.setCapacidad(1000);
            contenedor1.setEnvasesEstimados(450);
            contenedor1.setNivelLlenado(NivelLlenado.MEDIO);
            
            ContenedorDTO creado1 = contenedorController.CrearContenedor(contenedor1);
            System.out.println("✅ Contenedor creado:");
            System.out.println("   ID: " + creado1.getContenedorID());
            System.out.println("   Ubicación: " + creado1.getUbicacion());
            System.out.println("   Nivel: " + creado1.getNivelLlenado());
            
            // Contenedor 2
            ContenedorDTO contenedor2 = new ContenedorDTO();
            contenedor2.setUbicacion("Avenida de la Paz 45, Madrid");
            contenedor2.setCodPostal(28013);
            contenedor2.setCapacidad(1000);
            contenedor2.setEnvasesEstimados(850);
            contenedor2.setNivelLlenado(NivelLlenado.LLENO);
            
            ContenedorDTO creado2 = contenedorController.CrearContenedor(contenedor2);
            System.out.println("✅ Contenedor creado:");
            System.out.println("   ID: " + creado2.getContenedorID());
            System.out.println("   Ubicación: " + creado2.getUbicacion());
            System.out.println("   Nivel: " + creado2.getNivelLlenado());
            System.out.println();
            
            // ====================================================
            // EJEMPLO 4: Consultar contenedores por zona
            // ====================================================
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 4. CONSULTAR CONTENEDORES POR ZONA              │");
            System.out.println("└─────────────────────────────────────────────────┘");
            
            List<ContenedorDTO> contenedoresZona = contenedorController.getContenedoresByZona(28013);
            System.out.println("✅ Contenedores en código postal 28013: " + contenedoresZona.size());
            for (ContenedorDTO c : contenedoresZona) {
                System.out.println("   - " + c.getContenedorID() + " | " + c.getUbicacion());
            }
            System.out.println();
            
            // ====================================================
            // EJEMPLO 5: Actualizar contenedor
            // ====================================================
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 5. ACTUALIZAR CONTENEDOR                        │");
            System.out.println("└─────────────────────────────────────────────────┘");
            
            creado1.setEnvasesEstimados(900);
            creado1.setNivelLlenado(NivelLlenado.LLENO);
            
            ContenedorDTO actualizado = contenedorController.ActualizarContenedor(creado1);
            System.out.println("✅ Contenedor actualizado:");
            System.out.println("   ID: " + actualizado.getContenedorID());
            System.out.println("   Envases estimados: " + actualizado.getEnvasesEstimados());
            System.out.println("   Nivel: " + actualizado.getNivelLlenado());
            System.out.println("   Fecha actualización: " + actualizado.getFechaActualizada());
            System.out.println();
            
            // ====================================================
            // EJEMPLO 6: Asignar contenedor a planta
            // ====================================================
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 6. ASIGNAR CONTENEDOR A PLANTA                  │");
            System.out.println("└─────────────────────────────────────────────────┘");
            
            System.out.println("Asignando contenedor lleno a planta...");
            
            // AsignacionController coordina:
            // - Validación de token (LoginService)
            // - Verificación de capacidad (PlantaService)
            // - Asignación (AsignacionService)
            // - Notificación automática (AsignacionService)
            AsignacionDTO asignacion = asignacionController.AsignarContenedor(
                creado2.getContenedorID(), 
                "PLANTA-001", 
                token
            );
            
            System.out.println("✅ Asignación completada:");
            System.out.println("   ID Asignación: " + asignacion.getAsignacionID());
            System.out.println("   Fecha: " + asignacion.getFecha());
            System.out.println("   Contenedores: " + asignacion.getContenedorID());
            System.out.println("   Planta: " + asignacion.getPlantaID());
            System.out.println();
            
            // ====================================================
            // EJEMPLO 7: Consultar asignaciones
            // ====================================================
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 7. CONSULTAR ASIGNACIONES                       │");
            System.out.println("└─────────────────────────────────────────────────┘");
            
            List<AsignacionDTO> asignacionesHoy = asignacionController.getAsignacionesByFecha(
                LocalDate.now(), 
                token
            );
            
            System.out.println("✅ Asignaciones de hoy: " + asignacionesHoy.size());
            for (AsignacionDTO a : asignacionesHoy) {
                System.out.println("   - " + a.getAsignacionID() + " | Planta: " + a.getPlantaID());
            }
            System.out.println();
            
            // ====================================================
            // EJEMPLO 8: Enviar notificación adicional
            // ====================================================
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 8. ENVIAR NOTIFICACIÓN ADICIONAL                │");
            System.out.println("└─────────────────────────────────────────────────┘");
            
            asignacion.setNotificacion("URGENTE: Contenedor lleno requiere recogida inmediata");
            asignacionController.EnviarNotificacion(asignacion, token);
            
            // ====================================================
            // EJEMPLO 9: Consultar todos los contenedores
            // ====================================================
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 9. LISTAR TODOS LOS CONTENEDORES                │");
            System.out.println("└─────────────────────────────────────────────────┘");
            
            List<ContenedorDTO> todos = contenedorController.getAllContenedores();
            System.out.println("✅ Total de contenedores en el sistema: " + todos.size());
            System.out.println();
            
            // ====================================================
            // EJEMPLO 10: Cerrar sesión
            // ====================================================
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 10. CERRAR SESIÓN                               │");
            System.out.println("└─────────────────────────────────────────────────┘");
            
            empleadoController.CerrarSesion(token);
            System.out.println("✅ Sesión cerrada correctamente");
            
            // Verificar que el token ya no es válido
            boolean tokenValidoDespues = loginController.validarToken(token);
            System.out.println("   Token válido después de cerrar: " + (tokenValidoDespues ? "SÍ" : "NO ✓"));
            System.out.println();
            
            // ====================================================
            // RESUMEN FINAL
            // ====================================================
            System.out.println("\n╔═══════════════════════════════════════════════════════╗");
            System.out.println("║              VENTAJAS DEL PATRÓN FACADE               ║");
            System.out.println("╠═══════════════════════════════════════════════════════╣");
            System.out.println("║ ✅ Interfaz simplificada para operaciones complejas   ║");
            System.out.println("║ ✅ Coordinación automática entre servicios            ║");
            System.out.println("║ ✅ Validaciones centralizadas (seguridad, datos)      ║");
            System.out.println("║ ✅ Lógica de negocio encapsulada                      ║");
            System.out.println("║ ✅ Fácil mantenimiento y extensión                    ║");
            System.out.println("║ ✅ Desacoplamiento entre clientes y servicios         ║");
            System.out.println("╚═══════════════════════════════════════════════════════╝\n");
            
        } catch (SecurityException e) {
            System.err.println("❌ Error de seguridad: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("❌ Error de estado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error de argumento: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
