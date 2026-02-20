package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.UserProfileFormDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {

    UserProfileFormDTO getFormByEmail(String email);

    void updateProfile(String email, UserProfileFormDTO profileDto, MultipartFile multipartFile);
}