package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.controllers;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.*;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services.AdvertenciaService;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services.NivelCriticidadService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertenciaControllerTest {

    @Mock
    private AdvertenciaService advertenciaService;

    @Mock
    private NivelCriticidadService nivelCriticidadService;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private AdvertenciaController controller;

    // =========================
    // LIST
    // =========================

    @Test
    @DisplayName("listAdvertencias OK")
    void list_ok() {

        Model model = new ExtendedModelMap();
        Locale locale = new Locale("es");

        when(advertenciaService.findAll())
                .thenReturn(List.of(new AdvertenciaDTO(), new AdvertenciaDTO()));

        String view = controller.listAdvertencias(model, locale);

        assertEquals("views/advertencias/advertencia-list", view);
        assertTrue(model.containsAttribute("advertencias"));
        verify(advertenciaService).findAll();
    }

    @Test
    @DisplayName("listAdvertencias ERROR")
    void list_error() {

        Model model = new ExtendedModelMap();
        Locale locale = new Locale("es");

        when(advertenciaService.findAll())
                .thenThrow(new RuntimeException());

        when(messageSource.getMessage(eq("msg.advertencia.list.error"), any(), eq(locale)))
                .thenReturn("Error listando advertencias");

        String view = controller.listAdvertencias(model, locale);

        assertEquals("views/advertencias/advertencia-list", view);
        assertEquals("Error listando advertencias",
                model.getAttribute("errorMessage"));
    }

    // =========================
    // NEW FORM
    // =========================

    @Test
    @DisplayName("showNewForm OK")
    void showNewForm_ok() {

        Model model = new ExtendedModelMap();

        String view = controller.showNewForm(model);

        assertEquals("views/advertencias/advertencia-form", view);
        assertTrue(model.containsAttribute("advertencia"));
        assertEquals(false, model.getAttribute("isEdit"));
    }

    // =========================
    // INSERT
    // =========================

    @Test
    @DisplayName("insert OK")
    void insert_ok() {

        AdvertenciaCreateDTO dto = mock(AdvertenciaCreateDTO.class);
        when(dto.getTitulo()).thenReturn("Test");

        BindingResult br = mock(BindingResult.class);
        when(br.hasErrors()).thenReturn(false);

        Model model = new ExtendedModelMap();
        RedirectAttributes ra = new RedirectAttributesModelMap();
        Locale locale = new Locale("es");

        String view = controller.insertAdvertencia(dto, br, model, ra, locale);

        assertEquals("redirect:/advertencias", view);
        verify(advertenciaService).create(dto);
    }

    @Test
    @DisplayName("insert VALIDATION ERROR")
    void insert_validation() {

        AdvertenciaCreateDTO dto = mock(AdvertenciaCreateDTO.class);
        BindingResult br = mock(BindingResult.class);
        when(br.hasErrors()).thenReturn(true);

        Model model = new ExtendedModelMap();
        RedirectAttributes ra = new RedirectAttributesModelMap();
        Locale locale = new Locale("es");

        String view = controller.insertAdvertencia(dto, br, model, ra, locale);

        assertEquals("views/advertencias/advertencia-form", view);
        assertEquals(false, model.getAttribute("isEdit"));
        verify(advertenciaService, never()).create(any());
    }

    // =========================
    // EDIT
    // =========================

    @Test
    @DisplayName("showEditForm OK")
    void edit_ok() {

        Model model = new ExtendedModelMap();
        RedirectAttributes ra = new RedirectAttributesModelMap();
        Locale locale = new Locale("es");

        when(advertenciaService.getForEdit(1L))
                .thenReturn(new AdvertenciaUpdateDTO());

        String view = controller.showEditForm(1L, model, ra, locale);

        assertEquals("views/advertencias/advertencia-form", view);
        assertTrue(model.containsAttribute("advertencia"));
        assertEquals(true, model.getAttribute("isEdit"));
    }

    @Test
    @DisplayName("showEditForm ERROR")
    void edit_error() {

        Model model = new ExtendedModelMap();
        RedirectAttributes ra = new RedirectAttributesModelMap();
        Locale locale = new Locale("es");

        when(advertenciaService.getForEdit(99L))
                .thenThrow(new RuntimeException());

        when(messageSource.getMessage(eq("msg.advertencia.edit.notfound"), any(), eq(locale)))
                .thenReturn("No encontrada");

        String view = controller.showEditForm(99L, model, ra, locale);

        assertEquals("redirect:/advertencias", view);
        assertEquals("No encontrada",
                ra.getFlashAttributes().get("errorMessage"));
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    @DisplayName("update OK")
    void update_ok() {

        AdvertenciaUpdateDTO dto = mock(AdvertenciaUpdateDTO.class);
        when(dto.getId()).thenReturn(1L);

        BindingResult br = mock(BindingResult.class);
        when(br.hasErrors()).thenReturn(false);

        Model model = new ExtendedModelMap();
        RedirectAttributes ra = new RedirectAttributesModelMap();
        Locale locale = new Locale("es");

        String view = controller.updateAdvertencia(dto, br, model, ra, locale);

        assertEquals("redirect:/advertencias", view);
        verify(advertenciaService).update(1L, dto);
    }

    @Test
    @DisplayName("update VALIDATION ERROR")
    void update_validation() {

        AdvertenciaUpdateDTO dto = mock(AdvertenciaUpdateDTO.class);

        BindingResult br = mock(BindingResult.class);
        when(br.hasErrors()).thenReturn(true);

        Model model = new ExtendedModelMap();
        RedirectAttributes ra = new RedirectAttributesModelMap();
        Locale locale = new Locale("es");

        String view = controller.updateAdvertencia(dto, br, model, ra, locale);

        assertEquals("views/advertencias/advertencia-form", view);
        assertEquals(true, model.getAttribute("isEdit"));
        verify(advertenciaService, never()).update(any(), any());
    }

    // =========================
    // DELETE
    // =========================

    @Test
    @DisplayName("delete OK")
    void delete_ok() {

        RedirectAttributes ra = new RedirectAttributesModelMap();
        Locale locale = new Locale("es");

        String view = controller.deleteAdvertencia(1L, ra, locale);

        assertEquals("redirect:/advertencias", view);
        verify(advertenciaService).delete(1L);
    }

    // =========================
    // DETAIL
    // =========================

    @Test
    @DisplayName("detail OK")
    void detail_ok() {

        Model model = new ExtendedModelMap();
        RedirectAttributes ra = new RedirectAttributesModelMap();
        Locale locale = new Locale("es");

        when(advertenciaService.getDetail(1L))
                .thenReturn(new AdvertenciaDetailDTO());

        String view = controller.showDetail(1L, model, ra, locale);

        assertEquals("views/advertencias/advertencia-detail", view);
        assertTrue(model.containsAttribute("advertencia"));
    }

    @Test
    @DisplayName("detail ERROR")
    void detail_error() {

        Model model = new ExtendedModelMap();
        RedirectAttributes ra = new RedirectAttributesModelMap();
        Locale locale = new Locale("es");

        when(advertenciaService.getDetail(99L))
                .thenThrow(new RuntimeException());

        when(messageSource.getMessage(eq("msg.advertencia.detail.error"), any(), eq(locale)))
                .thenReturn("Error detalle");

        String view = controller.showDetail(99L, model, ra, locale);

        assertEquals("redirect:/advertencias", view);
        assertEquals("Error detalle",
                ra.getFlashAttributes().get("errorMessage"));
    }
}