package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.daos;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.NivelCriticidad;

import java.sql.SQLException;
import java.util.List;


public interface NivelCriticidadDAO {

    List<NivelCriticidad> getAll() throws SQLException;

    NivelCriticidad getById(Long id) throws SQLException;

    boolean insert(NivelCriticidad nivel) throws SQLException;

    boolean update(NivelCriticidad nivel) throws SQLException;

    boolean delete(Long id) throws SQLException;

    boolean existsByName(String name) throws SQLException;
}