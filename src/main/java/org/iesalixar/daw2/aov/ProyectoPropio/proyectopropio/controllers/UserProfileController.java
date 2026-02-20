package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.controllers;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.User;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private UserRepository userRepository;



    @GetMapping
    public String showProfile(Model model, Principal principal) {

        String email = principal.getName();

        logger.info("Mostrando perfil del usuario {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        model.addAttribute("user", user);

        return "views/profile/profile-detail";
    }



    @PostMapping("/update")
    public String updateProfile(
            @RequestParam String email,
            @RequestParam boolean active,
            RedirectAttributes redirectAttributes,
            Principal principal) {

        String loggedEmail = principal.getName();

        logger.info("Intentando actualizar perfil de {}", loggedEmail);

        User user = userRepository.findByEmail(loggedEmail)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        user.setActive(active);

        userRepository.save(user);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Perfil actualizado correctamente"
        );

        return "redirect:/profile";
    }
}
