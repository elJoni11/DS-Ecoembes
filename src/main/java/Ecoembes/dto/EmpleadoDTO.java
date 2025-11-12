package Ecoembes.dto;

public class EmpleadoDTO {

    // Aunque no está en tu DTO del diagrama, es común incluirlo para la respuesta.
    private Long id; 
    
    // Atributos según tu diagrama UML
    private String nombre;
    private String email;
    private String password;

    // --- Constructor vacío ---
    // Obligatorio para la deserialización de JSON (Spring Boot/Jackson)
    public EmpleadoDTO() {
    }

    // --- Constructor completo (útil para la capa de Assembler) ---
    public EmpleadoDTO(Long id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // --- Getters y Setters ---
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // ¡Cuidado! Es la única propiedad que NUNCA debería devolverse.
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "EmpleadoDTO{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", email='" + email + '\'' +
               ", password='[OCULTO]'" + // Ocultar password en logs
               '}';
    }
}