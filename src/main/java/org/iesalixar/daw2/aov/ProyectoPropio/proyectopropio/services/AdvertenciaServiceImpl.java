package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.daos.AdvertenciaDAO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaCreateDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaDetailDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaUpdateDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.Advertencia;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.NivelCriticidad;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.exceptions.ResourceNotFoundException;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.mappers.AdvertenciaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdvertenciaServiceImpl implements AdvertenciaService {

    private final AdvertenciaDAO advertenciaDAO;
    private final NivelCriticidadService nivelCriticidadService;

    public AdvertenciaServiceImpl(
            AdvertenciaDAO advertenciaDAO,
            NivelCriticidadService nivelCriticidadService) {

        this.advertenciaDAO = advertenciaDAO;
        this.nivelCriticidadService = nivelCriticidadService;
    }

    @Override
    public List<AdvertenciaDTO> findAll() {
        try {
            return AdvertenciaMapper.toDTOList(advertenciaDAO.getAll());
        } catch (SQLException e) {
            throw new RuntimeException("Error listando advertencias", e);
        }
    }

    @Override
    public AdvertenciaDetailDTO getDetail(Long id) {
        Advertencia advertencia = getEntityById(id);
        return AdvertenciaMapper.toDetailDTO(advertencia);
    }

    @Override
    public AdvertenciaUpdateDTO getForEdit(Long id) {
        Advertencia advertencia = getEntityById(id);
        return AdvertenciaMapper.toUpdateDTO(advertencia);
    }

    @Override
    public void create(AdvertenciaCreateDTO dto) {
        NivelCriticidad nivel = nivelCriticidadService.getById(dto.getNivelCriticidadId());

        Advertencia entity = AdvertenciaMapper.fromCreateDTO(dto, nivel);
        entity.setFechaEnvio(LocalDateTime.now());

        try {
            advertenciaDAO.insert(entity);
        } catch (SQLException e) {
            throw new RuntimeException("Error creando advertencia", e);
        }
    }

    @Override
    public void update(Long id, AdvertenciaUpdateDTO dto) {
        Advertencia entity = getEntityById(id);
        NivelCriticidad nivel = nivelCriticidadService.getById(dto.getNivelCriticidadId());

        AdvertenciaMapper.copyUpdateToEntity(dto, entity, nivel);

        try {
            advertenciaDAO.update(entity);
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando advertencia", e);
        }
    }

    @Override
    public void delete(Long id) {
        getEntityById(id);

        try {
            advertenciaDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando advertencia", e);
        }
    }


    private Advertencia getEntityById(Long id) {
        try {
            Advertencia advertencia = advertenciaDAO.getById(id);
            if (advertencia == null) {
                throw new ResourceNotFoundException("Advertencia", "id", id);
            }
            return advertencia;
        } catch (SQLException e) {
            throw new RuntimeException("Error accediendo a advertencia", e);
        }
    }
}
