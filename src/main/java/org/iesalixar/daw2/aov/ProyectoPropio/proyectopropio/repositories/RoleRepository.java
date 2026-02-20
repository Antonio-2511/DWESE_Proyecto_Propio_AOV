package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.repositories;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}


