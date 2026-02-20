package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services;

import java.util.Map;

public interface AppUrlService {
    String buildResetUrl(String rawToken);
    String buildUrl(String path, Map<String, String> queryParams);
}

