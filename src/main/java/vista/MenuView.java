/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author Usuario
 */
public class MenuView extends JFrame{
    
    private JButton btnIngresos, btnGastos, btnMetas, btnReportes;

    public MenuView() {
        setTitle("Menú Principal");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        btnIngresos = new JButton("Ingresos");
        btnGastos = new JButton("Gastos");
        btnMetas = new JButton("Metas");
        btnReportes = new JButton("Reportes");

        add(btnIngresos);
        add(btnGastos);
        add(btnMetas);
        add(btnReportes);

        setLocationRelativeTo(null);
    }

    public void addIngresosListener(ActionListener listener) { btnIngresos.addActionListener(listener); }
    public void addGastosListener(ActionListener listener) { btnGastos.addActionListener(listener); }
    public void addMetasListener(ActionListener listener) { btnMetas.addActionListener(listener); }
    public void addReportesListener(ActionListener listener) { btnReportes.addActionListener(listener); }
    
}
