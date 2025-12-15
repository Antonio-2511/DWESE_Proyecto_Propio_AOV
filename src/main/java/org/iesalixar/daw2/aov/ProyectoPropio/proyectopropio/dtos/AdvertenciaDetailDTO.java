package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertenciaDetailDTO {

    private Long id;
    private String titulo;
    private String descripcion;

    private boolean esEmergencia;
    private LocalDateTime fechaEnvio;

    private String nivelCriticidadNombre;
    private String nivelCriticidadDescripcion;
}
