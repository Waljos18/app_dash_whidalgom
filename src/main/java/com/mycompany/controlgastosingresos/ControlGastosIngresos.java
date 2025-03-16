package com.mycompany.controlgastosingresos;

import controlador.ControladorFinanzas;
import modelo.GestorFinanzas;
import vista.LoginView;

public class ControlGastosIngresos {
    public static void main(String[] args) {
        GestorFinanzas modelo = new GestorFinanzas();
        LoginView loginView = new LoginView();
        new ControladorFinanzas(modelo, loginView);
        loginView.setVisible(true);
    }
}