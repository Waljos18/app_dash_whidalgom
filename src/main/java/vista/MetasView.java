package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MetasView extends JFrame {
    private JTextField txtNombre, txtMontoObjetivo;
    private JButton btnRegistrar;

    public MetasView() {
        setTitle("Registrar Metas");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Monto Objetivo:"));
        txtMontoObjetivo = new JTextField();
        add(txtMontoObjetivo);

        btnRegistrar = new JButton("Registrar");
        add(btnRegistrar);

        setLocationRelativeTo(null);
    }

    public String getNombre() { return txtNombre.getText(); }
    public double getMontoObjetivo() { return Double.parseDouble(txtMontoObjetivo.getText()); }
    public void addRegistrarListener(ActionListener listener) { btnRegistrar.addActionListener(listener); }
    public void mostrarMensaje(String mensaje) { JOptionPane.showMessageDialog(this, mensaje); }
}