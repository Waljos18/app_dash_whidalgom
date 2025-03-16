package modelo;

import java.util.Date;

public class Transaccion {
    private int id;
    private String tipo; // "Ingreso" o "Egreso"
    private double monto;
    private String categoria;
    private Date fecha;
    private String nombre;
    private String nota;

    public Transaccion(int id, String tipo, double monto, String categoria, Date fecha, String nombre, String nota) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.categoria = categoria;
        this.fecha = fecha;
        this.nombre = nombre;
        this.nota = nota;
    }

    // Getters
    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public String getCategoria() { return categoria; }
    public Date getFecha() { return fecha; }
    public String getNombre() { return nombre; }
    public String getNota() { return nota; }
}