package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services;

import java.util.List;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.NivelCriticidad;

public interface NivelCriticidadService {

    List<NivelCriticidad> findAll();

    NivelCriticidad getById(Long id);
}
