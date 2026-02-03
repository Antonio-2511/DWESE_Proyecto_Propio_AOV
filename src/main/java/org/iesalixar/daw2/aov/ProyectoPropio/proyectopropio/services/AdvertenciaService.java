package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services;

import java.util.List;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaCreateDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaDetailDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaUpdateDTO;

public interface AdvertenciaService {

    List<AdvertenciaDTO> findAll();

    AdvertenciaDetailDTO getDetail(Long id);

    AdvertenciaUpdateDTO getForEdit(Long id);

    void create(AdvertenciaCreateDTO advertenciaCreateDTO);

    void update(Long id, AdvertenciaUpdateDTO advertenciaUpdateDTO);

    void delete(Long id);
}
