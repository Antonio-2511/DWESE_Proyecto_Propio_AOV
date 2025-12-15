package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nivel_criticidad")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class NivelCriticidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(length = 255)
    private String descripcion;
}
