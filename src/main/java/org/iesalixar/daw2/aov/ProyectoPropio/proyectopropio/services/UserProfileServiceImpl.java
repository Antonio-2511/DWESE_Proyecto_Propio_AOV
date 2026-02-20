package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services;

import jakarta.transaction.Transactional;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.UserProfileFormDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.User;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.UserProfile;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.exceptions.InvalidFileException;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.exceptions.ResourceNotFoundException;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.mappers.UserProfileMapper;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.repositories.UserProfileRepository;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private static final long MAX_IMAGE_SIZE_BYTES = 2 * 1024 * 1024; // 2MB

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final FileStorageService fileStorageService;

    public UserProfileServiceImpl(UserRepository userRepository,
                                  UserProfileRepository userProfileRepository,
                                  FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.fileStorageService = fileStorageService;
    }



    @Override
    public UserProfileFormDTO getFormByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("user", "email", email));

        Optional<UserProfile> profileOpt =
                userProfileRepository.findByUserId(user.getId());

        UserProfile profile = profileOpt.orElse(null);

        return UserProfileMapper.toFormDto(user, profile);
    }


    public void updateProfile(String email,
                              UserProfileFormDTO profileDto,
                              MultipartFile profileImageFile) {

        logger.info("Actualizando perfil para email={}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("user", "email", email));

        UserProfile profile =
                userProfileRepository.findByUserId(user.getId())
                        .orElse(null);

        boolean isNew = (profile == null);

        if (profileImageFile != null && !profileImageFile.isEmpty()) {

            validateProfileImage(profileImageFile);

            String oldImagePath = profileDto.getProfileImage();

            String newImageWebPath =
                    fileStorageService.saveFile(profileImageFile);

            if (newImageWebPath == null || newImageWebPath.isBlank()) {
                throw new InvalidFileException(
                        "userProfile",
                        "profileImageFile",
                        profileImageFile.getOriginalFilename(),
                        "No se pudo guardar la imagen de perfil."
                );
            }

            profileDto.setProfileImage(newImageWebPath);

            if (oldImagePath != null && !oldImagePath.isBlank()) {
                fileStorageService.deleteFile(oldImagePath);
            }
        }

        if (isNew) {
            profile = UserProfileMapper.toNewEntity(profileDto, user);
        } else {
            UserProfileMapper.copyToExistingEntity(profileDto, profile);
        }

        userProfileRepository.save(profile);

        logger.info("Perfil actualizado correctamente para {}", email);
    }



    private void validateProfileImage(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new InvalidFileException(
                    "userProfile",
                    "profileImageFile",
                    contentType,
                    "Tipo de archivo no permitido"
            );
        }

        if (file.getSize() > MAX_IMAGE_SIZE_BYTES) {
            throw new InvalidFileException(
                    "userProfile",
                    "profileImageFile",
                    file.getSize(),
                    "Archivo demasiado grande (máximo 2MB)"
            );
        }
    }
}
