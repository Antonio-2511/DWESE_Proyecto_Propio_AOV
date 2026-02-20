package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserProfileFormDTO {

    private Long userId;

    private String email;

    @NotBlank(message = "{msg.userProfile.firstName.notblank}")
    private String firstName;

    @NotBlank(message = "{msg.userProfile.lastName.notblank}")
    private String lastName;

    private String phoneNumber;

    private String profileImage;

    private String bio;

    private String locale;
}
