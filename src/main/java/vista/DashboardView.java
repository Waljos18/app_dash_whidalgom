package vista;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import modelo.GestorFinanzas;
import modelo.Transaccion;

public class DashboardView extends JFrame {
    private JPanel sidebar;
    private JLabel lblUsuario;
    private JButton btnDashboard, btnIngresos, btnGastos, btnMetas, btnReportes, btnLogout;
    private JLabel lblPresupuestoRestante, lblGastosTotales, lblIngresosTotales;
    private JTable tblIngresos, tblGastos;
    private JButton btnEditarIngreso, btnEliminarIngreso, btnEditarGasto, btnEliminarGasto;
    private GestorFinanzas modelo;
    private int usuarioId;

    public DashboardView(GestorFinanzas modelo, String email, int usuarioId) {
        this.modelo = modelo;
        this.usuarioId = usuarioId;
        setTitle("Smart Pocket - Tablero - " + email);
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = new JPanel();
        sidebar.setBackground(new Color(0, 33, 71));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, 0));

        lblUsuario = new JLabel(email);
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(lblUsuario);

        btnDashboard = createSidebarButton("Tablero");
        btnIngresos = createSidebarButton("Ingresos");
        btnGastos = createSidebarButton("Gastos");
        btnMetas = createSidebarButton("Metas");
        btnReportes = createSidebarButton("Reportes");
        btnLogout = createSidebarButton("Cerrar Sesión");
        btnLogout.setBackground(new Color(255, 69, 0));

        sidebar.add(btnDashboard);
        sidebar.add(btnIngresos);
        sidebar.add(btnGastos);
        sidebar.add(btnMetas);
        sidebar.add(btnReportes);
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);

        // Contenido principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Resumen
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        summaryPanel.setBackground(Color.WHITE);
        lblPresupuestoRestante = createSummaryLabel("Presupuesto Restante", new Color(255, 165, 0));
        lblGastosTotales = createSummaryLabel("Gastos Totales", new Color(255, 0, 0));
        lblIngresosTotales = createSummaryLabel("Ingresos Totales", new Color(0, 128, 0));
        summaryPanel.add(lblPresupuestoRestante);
        summaryPanel.add(lblGastosTotales);
        summaryPanel.add(lblIngresosTotales);

        // Tablas y botones
        JPanel recordsPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        String[] columnasIngresos = {"ID", "Fecha Ingreso", "Nombre Ingreso", "Categoría", "Monto", "Nota"};
        String[] columnasGastos = {"ID", "Fecha Gasto", "Nombre Gasto", "Categoría", "Monto", "Nota"};
        Object[][] datosIngresos = obtenerDatosTabla("Ingreso");
        Object[][] datosGastos = obtenerDatosTabla("Egreso");

        // Configurar tablas no editables y selección de fila
        DefaultTableModel modelIngresos = new DefaultTableModel(datosIngresos, columnasIngresos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DefaultTableModel modelGastos = new DefaultTableModel(datosGastos, columnasGastos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblIngresos = new JTable(modelIngresos);
        tblIngresos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblIngresos.setRowHeight(25);
        tblIngresos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblGastos = new JTable(modelGastos);
        tblGastos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblGastos.setRowHeight(25);
        tblGastos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Añadir ListSelectionListener para manejar la selección exclusiva
        tblIngresos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblIngresos.getSelectedRow() != -1) {
                    tblGastos.clearSelection();
                }
            }
        });

        tblGastos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblGastos.getSelectedRow() != -1) {
                    tblIngresos.clearSelection();
                }
            }
        });

        JScrollPane scrollIngresos = new JScrollPane(tblIngresos);
        scrollIngresos.setPreferredSize(new Dimension(1100, 200));
        JScrollPane scrollGastos = new JScrollPane(tblGastos);
        scrollGastos.setPreferredSize(new Dimension(1100, 200));

        // Botones para Ingresos
        JPanel incomeButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnEditarIngreso = new JButton("Editar");
        btnEditarIngreso.setBackground(new Color(0, 33, 71));
        btnEditarIngreso.setForeground(Color.WHITE);
        btnEditarIngreso.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEliminarIngreso = new JButton("Eliminar");
        btnEliminarIngreso.setBackground(new Color(255, 69, 0));
        btnEliminarIngreso.setForeground(Color.WHITE);
        btnEliminarIngreso.setFont(new Font("Segoe UI", Font.BOLD, 14));
        incomeButtonsPanel.add(btnEditarIngreso);
        incomeButtonsPanel.add(btnEliminarIngreso);

        // Botones para Gastos
        JPanel expenseButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnEditarGasto = new JButton("Editar");
        btnEditarGasto.setBackground(new Color(0, 33, 71));
        btnEditarGasto.setForeground(Color.WHITE);
        btnEditarGasto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEliminarGasto = new JButton("Eliminar");
        btnEliminarGasto.setBackground(new Color(255, 69, 0));
        btnEliminarGasto.setForeground(Color.WHITE);
        btnEliminarGasto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        expenseButtonsPanel.add(btnEditarGasto);
        expenseButtonsPanel.add(btnEliminarGasto);

        JPanel incomePanel = new JPanel(new BorderLayout());
        incomePanel.add(new JLabel("Registros Recientes de Ingresos", JLabel.CENTER), BorderLayout.NORTH);
        incomePanel.add(scrollIngresos, BorderLayout.CENTER);
        incomePanel.add(incomeButtonsPanel, BorderLayout.SOUTH);

        JPanel expensePanel = new JPanel(new BorderLayout());
        expensePanel.add(new JLabel("Registros Recientes de Gastos", JLabel.CENTER), BorderLayout.NORTH);
        expensePanel.add(scrollGastos, BorderLayout.CENTER);
        expensePanel.add(expenseButtonsPanel, BorderLayout.SOUTH);

        recordsPanel.add(incomePanel);
        recordsPanel.add(expensePanel);

        mainPanel.add(summaryPanel, BorderLayout.NORTH);
        mainPanel.add(recordsPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 33, 71));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setMargin(new Insets(10, 0, 10, 0));
        return button;
    }

    private JLabel createSummaryLabel(String text, Color background) {
        JLabel label = new JLabel(text + "\nS/" + String.format("%.2f", calcularRemainingBudget()));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(background);
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private double calcularRemainingBudget() {
        return modelo.getTotalIngresos(usuarioId) - modelo.getTotalEgresos(usuarioId);
    }

    private Object[][] obtenerDatosTabla(String tipo) {
        List<Transaccion> transacciones = modelo.getTransacciones(usuarioId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Object[][] datos = new Object[transacciones.size()][6];
        int index = 0;
        for (Transaccion t : transacciones) {
            if (t.getTipo().equals(tipo)) {
                datos[index][0] = t.getId();
                datos[index][1] = sdf.format(t.getFecha());
                datos[index][2] = t.getNombre();
                datos[index][3] = t.getCategoria();
                datos[index][4] = String.format("%.2f", t.getMonto());
                datos[index][5] = t.getNota();
                index++;
            }
        }
        return datos;
    }

    public void addIngresosListener(ActionListener listener) { btnIngresos.addActionListener(listener); }
    public void addGastosListener(ActionListener listener) { btnGastos.addActionListener(listener); }
    public void addMetasListener(ActionListener listener) { btnMetas.addActionListener(listener); }
    public void addReportesListener(ActionListener listener) { btnReportes.addActionListener(listener); }
    public void addLogoutListener(ActionListener listener) { btnLogout.addActionListener(listener); }
    public void addEditIncomeListener(ActionListener listener) { btnEditarIngreso.addActionListener(listener); }
    public void addDeleteIncomeListener(ActionListener listener) { btnEliminarIngreso.addActionListener(listener); }
    public void addEditExpenseListener(ActionListener listener) { btnEditarGasto.addActionListener(listener); }
    public void addDeleteExpenseListener(ActionListener listener) { btnEliminarGasto.addActionListener(listener); }

    public void actualizarTabla() {
        tblIngresos.setModel(new DefaultTableModel(obtenerDatosTabla("Ingreso"),
            new String[]{"ID", "Fecha Ingreso", "Nombre Ingreso", "Categoría", "Monto", "Nota"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblGastos.setModel(new DefaultTableModel(obtenerDatosTabla("Egreso"),
            new String[]{"ID", "Fecha Gasto", "Nombre Gasto", "Categoría", "Monto", "Nota"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        lblPresupuestoRestante.setText("Presupuesto Restante\nS/" + String.format("%.2f", calcularRemainingBudget()));
        lblGastosTotales.setText("Gastos Totales\nS/" + String.format("%.2f", modelo.getTotalEgresos(usuarioId)));
        lblIngresosTotales.setText("Ingresos Totales\nS/" + String.format("%.2f", modelo.getTotalIngresos(usuarioId)));
    }

    public int getSelectedIncomeId() {
        int row = tblIngresos.getSelectedRow();
        if (row != -1) {
            return (int) tblIngresos.getValueAt(row, 0);
        }
        return -1;
    }

    public int getSelectedExpenseId() {
        int row = tblGastos.getSelectedRow();
        if (row != -1) {
            return (int) tblGastos.getValueAt(row, 0);
        }
        return -1;
    }

    public void mostrarMensaje(String mensaje) { JOptionPane.showMessageDialog(this, mensaje); }
}