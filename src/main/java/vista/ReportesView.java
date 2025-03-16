package vista;

import javax.swing.*;
import java.awt.*;

public class ReportesView extends JFrame {
    private JTextArea txtReporte;

    public ReportesView() {
        setTitle("Smart Pocket - Reportes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Reporte Financiero");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTitle, BorderLayout.NORTH);

        txtReporte = new JTextArea();
        txtReporte.setFont(new Font("Courier New", Font.PLAIN, 14)); // Fuente monoespaciada para alineación
        txtReporte.setEditable(false);
        txtReporte.setBorder(BorderFactory.createLineBorder(new Color(0, 33, 71)));
        panel.add(new JScrollPane(txtReporte), BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    public void setReporte(String reporte) { txtReporte.setText(reporte); }
}