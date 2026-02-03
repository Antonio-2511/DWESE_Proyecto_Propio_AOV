package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.controllers;

import jakarta.validation.Valid;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaCreateDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaDetailDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaUpdateDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services.AdvertenciaService;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services.NivelCriticidadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/advertencias")
public class AdvertenciaController {

    private static final Logger logger = LoggerFactory.getLogger(AdvertenciaController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AdvertenciaService advertenciaService;

    @Autowired
    private NivelCriticidadService nivelCriticidadService;

    // ======================================================
    // MODELO COMÚN: NIVELES DE CRITICIDAD
    // ======================================================
    @ModelAttribute("niveles")
    public Object cargarNiveles() {
        return nivelCriticidadService.findAll();
    }

    // ======================================================
    // LISTADO
    // ======================================================
    @GetMapping
    public String listAdvertencias(Model model, Locale locale) {

        logger.info("Listando advertencias");

        try {
            model.addAttribute("advertencias", advertenciaService.findAll());
        } catch (Exception e) {
            logger.error("Error al listar advertencias", e);
            String msg = messageSource.getMessage("msg.advertencia.list.error", null, locale);
            model.addAttribute("errorMessage", msg);
        }

        return "views/advertencias/advertencia-list";
    }

    // ======================================================
    // FORMULARIO: CREAR
    // ======================================================
    @GetMapping("/new")
    public String showNewForm(Model model) {

        logger.info("Mostrando formulario para crear advertencia");

        model.addAttribute("advertencia", new AdvertenciaCreateDTO());
        model.addAttribute("isEdit", false);

        return "views/advertencias/advertencia-form";
    }

    // ======================================================
    // INSERTAR
    // ======================================================
    @PostMapping("/insert")
    public String insertAdvertencia(
            @Valid @ModelAttribute("advertencia") AdvertenciaCreateDTO advertenciaDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            Locale locale) {

        logger.info("Insertando advertencia {}", advertenciaDTO.getTitulo());

        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "views/advertencias/advertencia-form";
        }

        try {
            advertenciaService.create(advertenciaDTO);
        } catch (Exception e) {
            logger.error("Error insertando advertencia", e);
            String msg = messageSource.getMessage("msg.advertencia.insert.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
        }

        return "redirect:/advertencias";
    }

    // ======================================================
    // FORMULARIO: EDITAR
    // ======================================================
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {

        logger.info("Mostrando formulario edición advertencia {}", id);

        try {
            AdvertenciaUpdateDTO dto = advertenciaService.getForEdit(id);
            model.addAttribute("advertencia", dto);
            model.addAttribute("isEdit", true);

        } catch (Exception e) {
            logger.error("Error cargando advertencia", e);
            String msg = messageSource.getMessage("msg.advertencia.edit.notfound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/advertencias";
        }

        return "views/advertencias/advertencia-form";
    }

    // ======================================================
    // ACTUALIZAR
    // ======================================================
    @PostMapping("/update")
    public String updateAdvertencia(
            @Valid @ModelAttribute("advertencia") AdvertenciaUpdateDTO advertenciaDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            Locale locale) {

        logger.info("Actualizando advertencia {}", advertenciaDTO.getId());

        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "views/advertencias/advertencia-form";
        }

        try {
            advertenciaService.update(advertenciaDTO.getId(), advertenciaDTO);
        } catch (Exception e) {
            logger.error("Error actualizando advertencia", e);
            String msg = messageSource.getMessage("msg.advertencia.update.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
        }

        return "redirect:/advertencias";
    }

    // ======================================================
    // ELIMINAR
    // ======================================================
    @PostMapping("/delete")
    public String deleteAdvertencia(@RequestParam("id") Long id,
                                    RedirectAttributes redirectAttributes,
                                    Locale locale) {

        logger.info("Eliminando advertencia {}", id);

        try {
            advertenciaService.delete(id);
        } catch (Exception e) {
            logger.error("Error eliminando advertencia", e);
            String msg = messageSource.getMessage("msg.advertencia.delete.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
        }

        return "redirect:/advertencias";
    }

    // ======================================================
    // DETALLE
    // ======================================================
    @GetMapping("/detail")
    public String showDetail(@RequestParam("id") Long id,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             Locale locale) {

        logger.info("Mostrando detalle advertencia {}", id);

        try {
            AdvertenciaDetailDTO dto = advertenciaService.getDetail(id);
            model.addAttribute("advertencia", dto);
            return "views/advertencias/advertencia-detail";

        } catch (Exception e) {
            logger.error("Error cargando detalle", e);
            String msg = messageSource.getMessage("msg.advertencia.detail.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/advertencias";
        }
    }
}
