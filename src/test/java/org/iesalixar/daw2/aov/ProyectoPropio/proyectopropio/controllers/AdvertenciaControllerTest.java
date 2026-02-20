package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.controllers;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.dtos.AdvertenciaDTO;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services.AdvertenciaService;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services.NivelCriticidadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.*;
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
    // HELPERS
    // =========================

    private Pageable defaultPageable() {
        return PageRequest.of(0, 10, Sort.by(Sort.Order.desc("fechaEnvio")));
    }

    private Page<AdvertenciaDTO> samplePage() {
        return new PageImpl<>(
                List.of(new AdvertenciaDTO(), new AdvertenciaDTO()),
                defaultPageable(),
                2
        );
    }

    @Test
    @DisplayName("listAdvertencias OK -> devuelve vista y mete page")
    void list_ok() {

        Pageable pageable = defaultPageable();
        Model model = new ExtendedModelMap();
        Locale locale = new Locale("es");

        when(advertenciaService.list(pageable))
                .thenReturn(samplePage());

        String view = controller.listAdvertencias(pageable, model, locale);

        assertEquals("views/advertencia/advertencia-list", view);
        assertTrue(model.containsAttribute("page"));
        verify(advertenciaService).list(pageable);
    }



}

