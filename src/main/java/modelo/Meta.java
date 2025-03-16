/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class Meta {
    
    private String nombre;
    private double montoObjetivo;
    private double montoActual;

    public Meta(String nombre, double montoObjetivo) {
        this.nombre = nombre;
        this.montoObjetivo = montoObjetivo;
        this.montoActual = 0;
    }

    public String getNombre() { return nombre; }
    public double getMontoObjetivo() { return montoObjetivo; }
    public double getMontoActual() { return montoActual; }
    public void setMontoActual(double montoActual) { this.montoActual = montoActual; }
    
}
