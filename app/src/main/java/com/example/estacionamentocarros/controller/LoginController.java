package com.example.estacionamentocarros.controller;

public class LoginController {

    private final String EMAIL_CORRETO = "emailAleatorio@gmail.com";
    private final String SENHA_CORRETA = "7575";

    public boolean validarLogin(String emailDigitado, String senhaDigitada) {
        // Remove espaços extras
        emailDigitado = emailDigitado.trim();
        senhaDigitada = senhaDigitada.trim();

        // Valida campos vazios
        if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
            return false;
        }

        // Compara com dados simulados
        return emailDigitado.equalsIgnoreCase(EMAIL_CORRETO)
                && senhaDigitada.equals(SENHA_CORRETA);
    }

}
