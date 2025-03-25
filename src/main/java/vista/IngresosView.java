package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IngresosView extends JFrame {
    private JTextField txtMonto, txtNombre, txtNota, txtFecha;
    private JComboBox<String> cbCategoria;
    private JButton btnRegistrar;

    public IngresosView() {
        setTitle("Smart Pocket - Registrar Ingresos");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblTitle = new JLabel("Registrar Ingreso");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(lblTitle, gbc);

        gbc.gridy = 1;
        JLabel lblMonto = new JLabel("Monto");
        lblMonto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblMonto, gbc);

        gbc.gridy = 2;
        txtMonto = new JTextField(20);
        txtMonto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMonto.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(txtMonto, gbc);

        gbc.gridy = 3;
        JLabel lblCategoria = new JLabel("Categoría");
        lblCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblCategoria, gbc);

        gbc.gridy = 4;
        String[] categorias = {"Sueldo", "Negocios", "Transferencias", "Otros"};
        cbCategoria = new JComboBox<>(categorias);
        cbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbCategoria.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(cbCategoria, gbc);

        gbc.gridy = 5;
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblNombre, gbc);

        gbc.gridy = 6;
        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombre.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(txtNombre, gbc);

        gbc.gridy = 7;
        JLabel lblNota = new JLabel("Nota");
        lblNota.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblNota, gbc);

        gbc.gridy = 8;
        txtNota = new JTextField(20);
        txtNota.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNota.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(txtNota, gbc);

        gbc.gridy = 9;
        JLabel lblFecha = new JLabel("Fecha (dd-MM-yyyy)");
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblFecha, gbc);

        gbc.gridy = 10;
        txtFecha = new JTextField(20);
        txtFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFecha.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(txtFecha, gbc);

        gbc.gridy = 11;
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(0, 33, 71));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        add(btnRegistrar, gbc);

        setLocationRelativeTo(null);
    }

    public double getMonto() { return Double.parseDouble(txtMonto.getText()); }
    public String getCategoria() { return (String) cbCategoria.getSelectedItem(); }
    public String getNombre() { return txtNombre.getText(); }
    public String getNota() { return txtNota.getText(); }
    public Date getFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            return new java.sql.Date(sdf.parse(txtFecha.getText()).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Fecha inválida. Use formato dd-MM-yyyy.");
        }
    }
    public void addRegistrarListener(ActionListener listener) { btnRegistrar.addActionListener(listener); }
    public void mostrarMensaje(String mensaje) { JOptionPane.showMessageDialog(this, mensaje); }
}