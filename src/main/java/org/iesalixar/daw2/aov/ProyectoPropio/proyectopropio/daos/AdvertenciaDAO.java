package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.daos;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.Advertencia;

import java.sql.SQLException;
import java.util.List;


public interface AdvertenciaDAO {

    List<Advertencia> getAll() throws SQLException;

    Advertencia getById(Long id) throws SQLException;

    boolean insert(Advertencia advertencia) throws SQLException;

    boolean update(Advertencia advertencia) throws SQLException;

    boolean delete(Long id) throws SQLException;
}