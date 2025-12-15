package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "advertencia")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Advertencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "es_emergencia", nullable = false)
    private boolean esEmergencia;


    @ManyToOne
    @JoinColumn(name = "nivel_criticidad_id", nullable = false)
    private NivelCriticidad nivelCriticidad;
}
