package Ecoembes.repository;

import Ecoembes.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
    // Busca por email (que es el ID)
}
