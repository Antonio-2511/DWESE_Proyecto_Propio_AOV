package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services;

public interface PasswordResetService {
    void requestPasswordReset(String email, String requestIp, String userAgent);
    void resetPassword(String rawToken, String newPassword);
}
