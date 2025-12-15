package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.mappers;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.*;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.Advertencia;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.NivelCriticidad;

import java.util.List;

public class AdvertenciaMapper {



    public static AdvertenciaDTO toDTO(Advertencia entity) {
        if (entity == null) return null;

        AdvertenciaDTO dto = new AdvertenciaDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setEsEmergencia(entity.isEsEmergencia());

        if (entity.getNivelCriticidad() != null) {
            dto.setNivelCriticidadNombre(entity.getNivelCriticidad().getNombre());
        }

        return dto;
    }

    public static List<AdvertenciaDTO> toDTOList(List<Advertencia> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(AdvertenciaMapper::toDTO).toList();
    }



    public static AdvertenciaDetailDTO toDetailDTO(Advertencia entity) {
        if (entity == null) return null;

        AdvertenciaDetailDTO dto = new AdvertenciaDetailDTO();

        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setEsEmergencia(entity.isEsEmergencia());
        dto.setFechaEnvio(entity.getFechaEnvio());

        if (entity.getNivelCriticidad() != null) {
            dto.setNivelCriticidadNombre(entity.getNivelCriticidad().getNombre());
            dto.setNivelCriticidadDescripcion(entity.getNivelCriticidad().getDescripcion());
        }

        return dto;
    }


    public static AdvertenciaUpdateDTO toUpdateDTO(Advertencia entity) {
        if (entity == null) return null;

        AdvertenciaUpdateDTO dto = new AdvertenciaUpdateDTO();

        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setEsEmergencia(entity.isEsEmergencia());

        if (entity.getNivelCriticidad() != null) {
            dto.setNivelCriticidadId(entity.getNivelCriticidad().getId());
        }

        return dto;
    }


    public static Advertencia fromCreateDTO(AdvertenciaCreateDTO dto, NivelCriticidad nivel) {
        if (dto == null) return null;

        Advertencia e = new Advertencia();
        e.setTitulo(dto.getTitulo());
        e.setDescripcion(dto.getDescripcion());
        e.setEsEmergencia(dto.getEsEmergencia());
        e.setNivelCriticidad(nivel);

        return e;
    }



    public static void copyUpdateToEntity(AdvertenciaUpdateDTO dto,
                                          Advertencia entity,
                                          NivelCriticidad nivel) {

        if (dto == null || entity == null) return;

        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEsEmergencia(dto.getEsEmergencia());
        entity.setNivelCriticidad(nivel);
    }
}