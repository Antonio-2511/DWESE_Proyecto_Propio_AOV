package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AdvertenciaUpdateDTO {

    @NotNull(message = "{msg.advertencia.id.notEmpty}")
    private Long id;

    @NotBlank(message = "{msg.advertencia.titulo.notEmpty}")
    @Size(max = 100, message = "{msg.advertencia.titulo.size}")
    private String titulo;

    @NotBlank(message = "{msg.advertencia.descripcion.notEmpty}")
    private String descripcion;

    @NotNull(message = "{msg.advertencia.nivel.notEmpty}")
    private Long nivelCriticidadId;

    @NotNull(message = "{msg.advertencia.esEmergencia.notEmpty}")
    private Boolean esEmergencia;
}
