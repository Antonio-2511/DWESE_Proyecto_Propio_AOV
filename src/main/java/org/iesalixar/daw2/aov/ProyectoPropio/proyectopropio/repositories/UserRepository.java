package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.repositories;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);


    /**
     * Localiza un usuario por email (ignorando mayúsculas/minúsculas) y asegura que sus roles
     * queden cargados en la misma consulta.
     *
     * @param email email del usuario (usado como identificador/username del sistema).
     * @return {@link java.util.Optional} con el usuario y sus roles; {@code Optional.empty()} si no existe.
     */
    Optional<User> findByEmailIgnoreCase(String email);

}
