package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services;

import java.sql.SQLException;
import java.util.List;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.daos.NivelCriticidadDAO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.NivelCriticidad;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NivelCriticidadServiceImpl implements NivelCriticidadService {

    private final NivelCriticidadDAO nivelCriticidadDAO;

    public NivelCriticidadServiceImpl(NivelCriticidadDAO nivelCriticidadDAO) {
        this.nivelCriticidadDAO = nivelCriticidadDAO;
    }

    @Override
    public List<NivelCriticidad> findAll() {
        try {
            return nivelCriticidadDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error listando niveles de criticidad", e);
        }
    }

    @Override
    public NivelCriticidad getById(Long id) {
        try {
            NivelCriticidad nivel = nivelCriticidadDAO.getById(id);
            if (nivel == null) {
                throw new ResourceNotFoundException("NivelCriticidad", "id", id);
            }
            return nivel;
        } catch (SQLException e) {
            throw new RuntimeException("Error accediendo a nivel de criticidad", e);
        }
    }
}
