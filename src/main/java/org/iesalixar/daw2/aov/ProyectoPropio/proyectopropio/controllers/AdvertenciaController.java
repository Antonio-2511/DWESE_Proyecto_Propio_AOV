package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.daos.AdvertenciaDAO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.daos.NivelCriticidadDAO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.*;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.Advertencia;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.NivelCriticidad;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.mappers.AdvertenciaMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/advertencias")
public class AdvertenciaController {

    private static final Logger logger = LoggerFactory.getLogger(AdvertenciaController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AdvertenciaDAO advertenciaDAO;

    @Autowired
    private NivelCriticidadDAO nivelCriticidadDAO;

    // ======================================================
    // MODELO COMÚN: NIVELES DE CRITICIDAD
    // ======================================================
    @ModelAttribute("niveles")
    public List<NivelCriticidad> cargarNiveles() throws SQLException {
        return nivelCriticidadDAO.getAll();
    }

    // ======================================================
    // LISTADO
    // ======================================================
    @GetMapping
    public String listAdvertencias(Model model, Locale locale) {
        logger.info("Listando advertencias");

        try {
            List<Advertencia> entities = advertenciaDAO.getAll();
            List<AdvertenciaDTO> dtos = AdvertenciaMapper.toDTOList(entities);
            model.addAttribute("advertencias", dtos);

        } catch (Exception e) {
            logger.error("Error al listar advertencias: {}", e.getMessage());
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

        try {
            if (result.hasErrors()) {
                model.addAttribute("isEdit", false);
                return "views/advertencias/advertencia-form";
            }

            NivelCriticidad nivel = nivelCriticidadDAO.getById(advertenciaDTO.getNivelCriticidadId());
            if (nivel == null) {
                String msg = messageSource.getMessage("msg.advertencia.nivel.notFound", null, locale);
                redirectAttributes.addFlashAttribute("errorMessage", msg);
                return "redirect:/advertencias/new";
            }

            Advertencia entity = AdvertenciaMapper.fromCreateDTO(advertenciaDTO, nivel);
            entity.setFechaEnvio(LocalDateTime.now());

            advertenciaDAO.insert(entity);

        } catch (Exception e) {
            logger.error("Error insertando advertencia: {}", e.getMessage());
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
            Advertencia entity = advertenciaDAO.getById(id);

            if (entity == null) {
                String msg = messageSource.getMessage("msg.advertencia.edit.notfound", null, locale);
                redirectAttributes.addFlashAttribute("errorMessage", msg);
                return "redirect:/advertencias";
            }

            AdvertenciaUpdateDTO dto = AdvertenciaMapper.toUpdateDTO(entity);
            model.addAttribute("advertencia", dto);
            model.addAttribute("isEdit", true);

        } catch (Exception e) {
            logger.error("Error cargando advertencia: {}", e.getMessage());
            String msg = messageSource.getMessage("msg.advertencia.edit.error", null, locale);
            model.addAttribute("errorMessage", msg);
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

        try {
            if (result.hasErrors()) {
                model.addAttribute("isEdit", true);
                return "views/advertencias/advertencia-form";
            }

            Advertencia entity = advertenciaDAO.getById(advertenciaDTO.getId());
            if (entity == null) {
                String msg = messageSource.getMessage("msg.advertencia.edit.notfound", null, locale);
                redirectAttributes.addFlashAttribute("errorMessage", msg);
                return "redirect:/advertencias";
            }

            NivelCriticidad nivel = nivelCriticidadDAO.getById(advertenciaDTO.getNivelCriticidadId());
            AdvertenciaMapper.copyUpdateToEntity(advertenciaDTO, entity, nivel);

            advertenciaDAO.update(entity);

        } catch (Exception e) {
            logger.error("Error actualizando advertencia {}", advertenciaDTO.getId());
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
            advertenciaDAO.delete(id);
        } catch (Exception e) {
            logger.error("Error eliminando advertencia {}", id);
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
            Advertencia entity = advertenciaDAO.getById(id);

            if (entity == null) {
                String msg = messageSource.getMessage("msg.advertencia.detail.notFound", null, locale);
                redirectAttributes.addFlashAttribute("errorMessage", msg);
                return "redirect:/advertencias";
            }

            AdvertenciaDetailDTO dto = AdvertenciaMapper.toDetailDTO(entity);
            model.addAttribute("advertencia", dto);

            return "views/advertencias/advertencia-detail";

        } catch (Exception e) {
            logger.error("Error cargando detalle: {}", e.getMessage());
            String msg = messageSource.getMessage("msg.advertencia.detail.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/advertencias";
        }
    }
}
