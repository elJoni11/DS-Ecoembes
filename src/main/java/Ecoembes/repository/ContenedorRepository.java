package Ecoembes.repository;

import Ecoembes.entity.Contenedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenedorRepository extends JpaRepository<Contenedor, String> {
}
