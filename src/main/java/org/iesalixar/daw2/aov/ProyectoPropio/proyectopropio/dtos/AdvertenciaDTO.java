package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertenciaDTO {

    private Long id;
    private String titulo;
    private boolean esEmergencia;

    private String nivelCriticidadNombre;
}