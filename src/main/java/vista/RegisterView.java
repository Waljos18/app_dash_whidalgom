package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister;

    public RegisterView() {
        setTitle("Smart Pocket - Sign Up");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblSignUp = new JLabel("Sign Up");
        lblSignUp.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(lblSignUp, gbc);

        gbc.gridy = 1;
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblEmail, gbc);

        gbc.gridy = 2;
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(txtEmail, gbc);

        gbc.gridy = 3;
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lblPassword, gbc);

        gbc.gridy = 4;
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        add(txtPassword, gbc);

        gbc.gridy = 5;
        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(0, 33, 71));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setFocusPainted(false);
        add(btnRegister, gbc);

        setLocationRelativeTo(null);
    }

    public String getEmail() { return txtEmail.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public void addRegistrarListener(ActionListener listener) { btnRegister.addActionListener(listener); }
    public void mostrarMensaje(String mensaje) { JOptionPane.showMessageDialog(this, mensaje); }
}