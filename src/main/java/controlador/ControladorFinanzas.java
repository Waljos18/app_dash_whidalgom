package controlador;

import modelo.GestorFinanzas;
import modelo.Transaccion;
import modelo.Meta;
import vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ControladorFinanzas {
    private GestorFinanzas modelo;
    private LoginView loginView;
    private RegisterView registerView;
    private DashboardView dashboardView;
    private IngresosView ingresosView;
    private GastosView gastosView;
    private MetasView metasView;
    private ReportesView reportesView;
    private EditarIngresoView editarIngresoView;
    private EditarGastoView editarGastoView;
    private int usuarioId;

    public ControladorFinanzas(GestorFinanzas modelo, LoginView loginView) {
        this.modelo = modelo;
        this.loginView = loginView;

        this.loginView.addSignInListener(new SignInListener());
        this.loginView.addClearListener(new ClearListener());
        this.loginView.addSignUpListener(e -> mostrarRegistro());
    }

    class SignInListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = loginView.getEmail();
            String contrasena = loginView.getPassword();
            if (modelo.validarUsuario(email, contrasena)) {
                usuarioId = modelo.getUsuarioId(email);
                loginView.dispose();
                dashboardView = new DashboardView(modelo, email, usuarioId);
                new DashboardController(dashboardView, modelo);
                dashboardView.setVisible(true);
            } else {
                loginView.mostrarMensaje("Usuario o contraseña incorrectos");
            }
        }
    }

    class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginView.clearFields();
        }
    }

    private void mostrarRegistro() {
        registerView = new RegisterView();
        registerView.addRegistrarListener(new RegisterListener());
        registerView.setVisible(true);
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = registerView.getEmail();
            String contrasena = registerView.getPassword();
            if (modelo.registrarUsuario(email, contrasena)) {
                registerView.mostrarMensaje("Usuario registrado con éxito");
                registerView.dispose();
            } else {
                registerView.mostrarMensaje("El usuario ya existe");
            }
        }
    }

    class DashboardController {
        public DashboardController(DashboardView view, GestorFinanzas model) {
            view.addIngresosListener(e -> mostrarIngresos());
            view.addGastosListener(e -> mostrarGastos());
            view.addMetasListener(e -> mostrarMetas());
            view.addReportesListener(e -> mostrarReportes());
            view.addLogoutListener(e -> {
                view.dispose();
                new LoginView().setVisible(true);
            });
            view.addEditIncomeListener(new EditIncomeListener());
            view.addDeleteIncomeListener(new DeleteIncomeListener());
            view.addEditExpenseListener(new EditExpenseListener());
            view.addDeleteExpenseListener(new DeleteExpenseListener());
        }
    }

    class EditIncomeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int incomeId = dashboardView.getSelectedIncomeId();
            int expenseId = dashboardView.getSelectedExpenseId();
            if (incomeId != -1 && expenseId == -1) {
                Transaccion transaccion = modelo.getTransaccionPorId(incomeId, usuarioId);
                if (transaccion != null) {
                    editarIngresoView = new EditarIngresoView(
                        incomeId,
                        transaccion.getMonto(),
                        transaccion.getCategoria(),
                        transaccion.getFecha(),
                        transaccion.getNombre(),
                        transaccion.getNota()
                    );
                    editarIngresoView.addGuardarListener(new GuardarIngresoListener());
                    editarIngresoView.setVisible(true);
                } else {
                    dashboardView.mostrarMensaje("No se encontró el ingreso seleccionado");
                }
            } else {
                dashboardView.mostrarMensaje("Selecciona un ingreso para editar (y asegúrate de no tener un gasto seleccionado)");
            }
        }
    }

    class GuardarIngresoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                modelo.actualizarTransaccion(editarIngresoView.getTransaccionId(),
                    "Ingreso",
                    editarIngresoView.getMonto(),
                    editarIngresoView.getCategoria(), (Date) editarIngresoView.getFecha(),
                    editarIngresoView.getNombre(),
                    editarIngresoView.getNota(),
                    usuarioId
                );
                editarIngresoView.mostrarMensaje("Ingreso actualizado con éxito");
                editarIngresoView.dispose();
                dashboardView.actualizarTabla();
            } catch (Exception ex) {
                editarIngresoView.mostrarMensaje("Error: " + ex.getMessage());
            }
        }
    }

    class DeleteIncomeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int incomeId = dashboardView.getSelectedIncomeId();
            int expenseId = dashboardView.getSelectedExpenseId();
            if (incomeId != -1 && expenseId == -1) {
                try {
                    String sql = "DELETE FROM transacciones WHERE id = ? AND usuario_id = ?";
                    try (Connection conn = modelo.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, incomeId);
                        pstmt.setInt(2, usuarioId);
                        int rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            dashboardView.mostrarMensaje("Ingreso eliminado con éxito");
                            dashboardView.actualizarTabla();
                        } else {
                            dashboardView.mostrarMensaje("No se pudo eliminar el ingreso");
                        }
                    }
                } catch (SQLException ex) {
                    dashboardView.mostrarMensaje("Error al eliminar: " + ex.getMessage());
                }
            } else {
                dashboardView.mostrarMensaje("Selecciona un ingreso para eliminar (y asegúrate de no tener un gasto seleccionado)");
            }
        }
    }

    class EditExpenseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int incomeId = dashboardView.getSelectedIncomeId();
            int expenseId = dashboardView.getSelectedExpenseId();
            if (expenseId != -1 && incomeId == -1) {
                Transaccion transaccion = modelo.getTransaccionPorId(expenseId, usuarioId);
                if (transaccion != null) {
                    editarGastoView = new EditarGastoView(
                        expenseId,
                        transaccion.getMonto(),
                        transaccion.getCategoria(),
                        transaccion.getFecha(),
                        transaccion.getNombre(),
                        transaccion.getNota()
                    );
                    editarGastoView.addGuardarListener(new GuardarGastoListener());
                    editarGastoView.setVisible(true);
                } else {
                    dashboardView.mostrarMensaje("No se encontró el gasto seleccionado");
                }
            } else {
                dashboardView.mostrarMensaje("Selecciona un gasto para editar (y asegúrate de no tener un ingreso seleccionado)");
            }
        }
    }

    class GuardarGastoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                modelo.actualizarTransaccion(editarGastoView.getTransaccionId(),
                    "Egreso",
                    editarGastoView.getMonto(),
                    editarGastoView.getCategoria(), (Date) editarGastoView.getFecha(),
                    editarGastoView.getNombre(),
                    editarGastoView.getNota(),
                    usuarioId
                );
                editarGastoView.mostrarMensaje("Gasto actualizado con éxito");
                editarGastoView.dispose();
                dashboardView.actualizarTabla();
            } catch (Exception ex) {
                editarGastoView.mostrarMensaje("Error: " + ex.getMessage());
            }
        }
    }

    class DeleteExpenseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int incomeId = dashboardView.getSelectedIncomeId();
            int expenseId = dashboardView.getSelectedExpenseId();
            if (expenseId != -1 && incomeId == -1) {
                try {
                    String sql = "DELETE FROM transacciones WHERE id = ? AND usuario_id = ?";
                    try (Connection conn = modelo.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, expenseId);
                        pstmt.setInt(2, usuarioId);
                        int rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            dashboardView.mostrarMensaje("Gasto eliminado con éxito");
                            dashboardView.actualizarTabla();
                        } else {
                            dashboardView.mostrarMensaje("No se pudo eliminar el gasto");
                        }
                    }
                } catch (SQLException ex) {
                    dashboardView.mostrarMensaje("Error al eliminar: " + ex.getMessage());
                }
            } else {
                dashboardView.mostrarMensaje("Selecciona un gasto para eliminar (y asegúrate de no tener un ingreso seleccionado)");
            }
        }
    }

    private void mostrarIngresos() {
        ingresosView = new IngresosView();
        ingresosView.addRegistrarListener(new IngresosListener());
        ingresosView.setVisible(true);
    }

    class IngresosListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                modelo.registrarTransaccion("Ingreso", ingresosView.getMonto(),
                    ingresosView.getCategoria(), (Date) ingresosView.getFecha(),
                    ingresosView.getNombre(), ingresosView.getNota(), usuarioId);
                ingresosView.mostrarMensaje("Ingreso registrado con éxito");
                if (dashboardView != null) dashboardView.actualizarTabla();
                ingresosView.dispose();
            } catch (Exception ex) {
                ingresosView.mostrarMensaje("Error: " + ex.getMessage());
            }
        }
    }

    private void mostrarGastos() {
        gastosView = new GastosView();
        gastosView.addRegistrarListener(new GastosListener());
        gastosView.setVisible(true);
    }

    class GastosListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                modelo.registrarTransaccion("Egreso", gastosView.getMonto(),
                    gastosView.getCategoria(), (Date) gastosView.getFecha(),
                    gastosView.getNombre(), gastosView.getNota(), usuarioId);
                gastosView.mostrarMensaje("Gasto registrado con éxito");
                if (dashboardView != null) dashboardView.actualizarTabla();
                gastosView.dispose();
            } catch (Exception ex) {
                gastosView.mostrarMensaje("Error: " + ex.getMessage());
            }
        }
    }

    private void mostrarMetas() {
        metasView = new MetasView();
        metasView.addRegistrarListener(new MetasListener());
        metasView.setVisible(true);
    }

    class MetasListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                modelo.registrarMeta(metasView.getNombre(), metasView.getMontoObjetivo(), usuarioId);
                metasView.mostrarMensaje("Meta registrada con éxito");
                if (dashboardView != null) dashboardView.actualizarTabla();
                metasView.dispose();
            } catch (Exception ex) {
                metasView.mostrarMensaje("Error: " + ex.getMessage());
            }
        }
    }

    private void mostrarReportes() {
        reportesView = new ReportesView();
        StringBuilder reporte = new StringBuilder();

        // Balance General
        double totalIngresos = modelo.getTotalIngresos(usuarioId);
        double totalGastos = modelo.getTotalEgresos(usuarioId);
        double balance = modelo.calcularBalance(usuarioId);
        reporte.append("=== REPORTE FINANCIERO ===\n\n");
        reporte.append("1. Balance General:\n");
        reporte.append(String.format("Total Ingresos: S/%.2f\n", totalIngresos));
        reporte.append(String.format("Total Gastos: S/%.2f\n", totalGastos));
        reporte.append(String.format("Balance Neto: S/%.2f\n", balance));
        if (totalIngresos > 0) {
            double porcentajeGastos = (totalGastos / totalIngresos) * 100;
            reporte.append(String.format("Porcentaje de Gastos respecto a Ingresos: %.2f%%\n", porcentajeGastos));
        }
        reporte.append("\n");

        // Desglose de Ingresos
        reporte.append("2. Desglose de Ingresos:\n");
        Map<String, Double> ingresosPorCategoria = modelo.getIngresosPorCategoria(usuarioId);
        for (Map.Entry<String, Double> entry : ingresosPorCategoria.entrySet()) {
            reporte.append(String.format("- %s: S/%.2f\n", entry.getKey(), entry.getValue()));
        }
        reporte.append("\nTransacciones Recientes (Ingresos):\n");
        for (Transaccion t : modelo.getTransacciones(usuarioId)) {
            if (t.getTipo().equals("Ingreso")) {
                reporte.append(String.format("%s - %s - S/%.2f - %s - %s\n",
                    t.getFecha(), t.getNombre(), t.getMonto(), t.getCategoria(), t.getNota()));
            }
        }
        reporte.append("\n");

        // Desglose de Gastos
        reporte.append("3. Desglose de Gastos:\n");
        Map<String, Double> gastosPorCategoria = modelo.getGastosPorCategoria(usuarioId);
        String categoriaMayorGasto = "";
        double mayorGasto = 0;
        for (Map.Entry<String, Double> entry : gastosPorCategoria.entrySet()) {
            reporte.append(String.format("- %s: S/%.2f\n", entry.getKey(), entry.getValue()));
            if (entry.getValue() > mayorGasto) {
                mayorGasto = entry.getValue();
                categoriaMayorGasto = entry.getKey();
            }
        }
        if (!categoriaMayorGasto.isEmpty()) {
            reporte.append(String.format("Categoría con Mayor Gasto: %s (S/%.2f)\n", categoriaMayorGasto, mayorGasto));
        }
        reporte.append("\nTransacciones Recientes (Gastos):\n");
        for (Transaccion t : modelo.getTransacciones(usuarioId)) {
            if (t.getTipo().equals("Egreso")) {
                reporte.append(String.format("%s - %s - S/%.2f - %s - %s\n",
                    t.getFecha(), t.getNombre(), t.getMonto(), t.getCategoria(), t.getNota()));
            }
        }
        reporte.append("\n");

        // Metas Financieras
        reporte.append("4. Metas Financieras:\n");
        List<Meta> metas = modelo.getMetas(usuarioId);
        double ahorros = balance > 0 ? balance : 0;
        for (Meta m : metas) {
            double montoObjetivo = m.getMontoObjetivo();
            double progreso = Math.min(ahorros, montoObjetivo);
            double porcentajeProgreso = (progreso / montoObjetivo) * 100;
            double faltante = montoObjetivo - progreso;
            reporte.append(String.format("%s - Objetivo: S/%.2f\n", m.getNombre(), montoObjetivo));
            reporte.append(String.format("   Progreso: S/%.2f (%.2f%%)\n", progreso, porcentajeProgreso));
            reporte.append(String.format("   Faltante: S/%.2f\n", faltante));
        }

        reportesView.setReporte(reporte.toString());
        reportesView.setVisible(true);
    }
}