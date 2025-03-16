package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorFinanzas {
    public Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/smartpocket?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url, user, password);
    }

    public boolean registrarUsuario(String email, String contrasena) {
        String sql = "INSERT INTO usuarios (email, contrasena) VALUES (?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, contrasena);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                return false;
            }
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean validarUsuario(String email, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND contrasena = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, contrasena);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public int getUsuarioId(String email) {
        String sql = "SELECT id FROM usuarios WHERE email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    public void registrarTransaccion(String tipo, double monto, String categoria, Date fecha, String nombre, String nota, int usuarioId) {
        String sql = "INSERT INTO transacciones (tipo, monto, categoria, fecha, nombre, nota, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tipo);
            pstmt.setDouble(2, monto);
            pstmt.setString(3, categoria);
            pstmt.setDate(4, new java.sql.Date(fecha.getTime()));
            pstmt.setString(5, nombre);
            pstmt.setString(6, nota);
            pstmt.setInt(7, usuarioId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void actualizarTransaccion(int id, String tipo, double monto, String categoria, Date fecha, String nombre, String nota, int usuarioId) {
        String sql = "UPDATE transacciones SET tipo = ?, monto = ?, categoria = ?, fecha = ?, nombre = ?, nota = ? WHERE id = ? AND usuario_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tipo);
            pstmt.setDouble(2, monto);
            pstmt.setString(3, categoria);
            pstmt.setDate(4, new java.sql.Date(fecha.getTime()));
            pstmt.setString(5, nombre);
            pstmt.setString(6, nota);
            pstmt.setInt(7, id);
            pstmt.setInt(8, usuarioId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void registrarMeta(String nombre, double montoObjetivo, int usuarioId) {
        String sql = "INSERT INTO metas (nombre, monto_objetivo, usuario_id) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, montoObjetivo);
            pstmt.setInt(3, usuarioId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public double calcularBalance(int usuarioId) {
        double ingresos = getTotalIngresos(usuarioId);
        double egresos = getTotalEgresos(usuarioId);
        return ingresos - egresos;
    }

    public double getTotalIngresos(int usuarioId) {
        String sql = "SELECT SUM(monto) AS total FROM transacciones WHERE tipo = 'Ingreso' AND usuario_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total") != 0 ? rs.getDouble("total") : 0;
            }
            return 0;
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public double getTotalEgresos(int usuarioId) {
        String sql = "SELECT SUM(monto) AS total FROM transacciones WHERE tipo = 'Egreso' AND usuario_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total") != 0 ? rs.getDouble("total") : 0;
            }
            return 0;
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public List<Transaccion> getTransacciones(int usuarioId) {
        List<Transaccion> transacciones = new ArrayList<>();
        String sql = "SELECT * FROM transacciones WHERE usuario_id = ? ORDER BY fecha DESC LIMIT 10";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Transaccion t = new Transaccion(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    rs.getDouble("monto"),
                    rs.getString("categoria"),
                    rs.getDate("fecha"),
                    rs.getString("nombre"),
                    rs.getString("nota")
                );
                transacciones.add(t);
            }
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
        }
        return transacciones;
    }

    public Transaccion getTransaccionPorId(int id, int usuarioId) {
        String sql = "SELECT * FROM transacciones WHERE id = ? AND usuario_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Transaccion(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    rs.getDouble("monto"),
                    rs.getString("categoria"),
                    rs.getDate("fecha"),
                    rs.getString("nombre"),
                    rs.getString("nota")
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<Meta> getMetas(int usuarioId) {
        List<Meta> metas = new ArrayList<>();
        String sql = "SELECT * FROM metas WHERE usuario_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Meta m = new Meta(rs.getString("nombre"), rs.getDouble("monto_objetivo"));
                metas.add(m);
            }
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
        }
        return metas;
    }

    // Nuevo método para obtener ingresos por categoría
    public Map<String, Double> getIngresosPorCategoria(int usuarioId) {
        Map<String, Double> ingresosPorCategoria = new HashMap<>();
        String sql = "SELECT categoria, SUM(monto) AS total FROM transacciones WHERE tipo = 'Ingreso' AND usuario_id = ? GROUP BY categoria";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String categoria = rs.getString("categoria");
                double total = rs.getDouble("total");
                ingresosPorCategoria.put(categoria, total);
            }
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
        }
        return ingresosPorCategoria;
    }

    // Nuevo método para obtener gastos por categoría
    public Map<String, Double> getGastosPorCategoria(int usuarioId) {
        Map<String, Double> gastosPorCategoria = new HashMap<>();
        String sql = "SELECT categoria, SUM(monto) AS total FROM transacciones WHERE tipo = 'Egreso' AND usuario_id = ? GROUP BY categoria";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String categoria = rs.getString("categoria");
                double total = rs.getDouble("total");
                gastosPorCategoria.put(categoria, total);
            }
        } catch (SQLException e) {
            Logger.getLogger(GestorFinanzas.class.getName()).log(Level.SEVERE, null, e);
        }
        return gastosPorCategoria;
    }
}