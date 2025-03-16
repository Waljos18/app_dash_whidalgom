package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditarGastoView extends JFrame {
    private JTextField txtMonto, txtNombre, txtNota, txtFecha;
    private JComboBox<String> cbCategoria;
    private JButton btnGuardar;
    private int transaccionId;

    public EditarGastoView(int transaccionId, double monto, String categoria, Date fecha, String nombre, String nota) {
        this.transaccionId = transaccionId;
        setTitle("Smart Pocket - Editar Gasto");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblTitle = new JLabel("Editar Gasto");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(lblTitle, gbc);

        gbc.gridy = 1;
        JLabel lblMonto = new JLabel("Monto");
        lblMonto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblMonto, gbc);

        gbc.gridy = 2;
        txtMonto = new JTextField(String.valueOf(monto), 20);
        txtMonto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMonto.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(txtMonto, gbc);

        gbc.gridy = 3;
        JLabel lblCategoria = new JLabel("Categoría");
        lblCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblCategoria, gbc);

        gbc.gridy = 4;
        String[] categorias = {"Alimentacion", "Salud", "Vivienda", "Educacion", "Entretenimiento", "Otros"};
        cbCategoria = new JComboBox<>(categorias);
        cbCategoria.setSelectedItem(categoria);
        cbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbCategoria.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(cbCategoria, gbc);

        gbc.gridy = 5;
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblNombre, gbc);

        gbc.gridy = 6;
        txtNombre = new JTextField(nombre, 20);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombre.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(txtNombre, gbc);

        gbc.gridy = 7;
        JLabel lblNota = new JLabel("Nota");
        lblNota.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblNota, gbc);

        gbc.gridy = 8;
        txtNota = new JTextField(nota, 20);
        txtNota.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNota.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(txtNota, gbc);

        gbc.gridy = 9;
        JLabel lblFecha = new JLabel("Fecha (dd-MM-yyyy)");
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblFecha, gbc);

        gbc.gridy = 10;
        txtFecha = new JTextField(new SimpleDateFormat("dd-MM-yyyy").format(fecha), 20);
        txtFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFecha.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(txtFecha, gbc);

        gbc.gridy = 11;
        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(0, 33, 71));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        add(btnGuardar, gbc);

        setLocationRelativeTo(null);
    }

    public int getTransaccionId() { return transaccionId; }
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
    public void addGuardarListener(ActionListener listener) { btnGuardar.addActionListener(listener); }
    public void mostrarMensaje(String mensaje) { JOptionPane.showMessageDialog(this, mensaje); }
}