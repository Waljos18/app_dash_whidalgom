package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnSignIn, btnClear, btnSignUp;
    private JLabel lblSignUpLink;

    public LoginView() {
        setTitle("Smart Pocket - Sign In");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel lateral izquierdo (fondo azul oscuro)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 33, 71)); // Azul oscuro similar al de la imagen
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Ícono de billetera (con manejo de error)
        JLabel lblIcon = new JLabel();
        java.net.URL imgURL = getClass().getResource("/icons/wallet.png");
        if (imgURL != null) {
            lblIcon.setIcon(new ImageIcon(imgURL));
        } else {
            lblIcon.setText("Wallet Icon"); // Placeholder si el ícono no se encuentra
            lblIcon.setForeground(Color.WHITE);
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(lblIcon, gbc);

        // Título "Smart Pocket"
        JLabel lblTitle = new JLabel("Gestor de Dinero");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 1;
        leftPanel.add(lblTitle, gbc);

        add(leftPanel, BorderLayout.WEST);

        // Panel derecho (formulario)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5, 1, 10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblSignIn = new JLabel("Sign In");
        lblSignIn.setFont(new Font("Arial", Font.BOLD, 20));
        rightPanel.add(lblSignIn);

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailPanel.add(new JLabel("Usuario"));
        txtEmail = new JTextField(20);
        emailPanel.add(txtEmail);
        rightPanel.add(emailPanel);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.add(new JLabel("Contraseña"));
        txtPassword = new JPasswordField(20);
        passwordPanel.add(txtPassword);
        rightPanel.add(passwordPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSignIn = new JButton("Sign In");
        btnSignIn.setBackground(new Color(0, 33, 71));
        btnSignIn.setForeground(Color.WHITE);
        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(0, 33, 71));
        btnClear.setForeground(Color.WHITE);
        buttonPanel.add(btnSignIn);
        buttonPanel.add(btnClear);
        rightPanel.add(buttonPanel);

        lblSignUpLink = new JLabel("<html><u>Don't have an account? Sign up here.</u></html>");
        lblSignUpLink.setForeground(new Color(0, 33, 71));
        lblSignUpLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(lblSignUpLink);

        add(rightPanel, BorderLayout.CENTER);
    }

    public String getEmail() { return txtEmail.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public void addSignInListener(ActionListener listener) { btnSignIn.addActionListener(listener); }
    public void addClearListener(ActionListener listener) { btnClear.addActionListener(listener); }
    public void addSignUpListener(ActionListener listener) { lblSignUpLink.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { listener.actionPerformed(new ActionEvent(evt.getSource(), evt.getID(), "signup")); } }); }
    public void mostrarMensaje(String mensaje) { JOptionPane.showMessageDialog(this, mensaje); }
    public void clearFields() {
        txtEmail.setText("");
        txtPassword.setText("");
    }
}