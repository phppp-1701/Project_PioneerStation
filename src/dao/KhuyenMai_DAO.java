package dao;

import connectDB.ConnectDB;
import entity.KhuyenMai;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMai_DAO {

    // Create: Thêm khuyến mãi mới
    public boolean themKhuyenMai(KhuyenMai khuyenMai) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "INSERT INTO KhuyenMai (maKhuyenMai, tenKhuyenMai, phanTramGiamGia, ngayBatDau, ngayKetThuc) VALUES (?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, khuyenMai.getMaKhuyenMai());
            stmt.setString(2, khuyenMai.getTenKhuyenMai());
            stmt.setDouble(3, khuyenMai.getPhanTramGiamGia());
            stmt.setDate(4, Date.valueOf(khuyenMai.getNgayBatDau()));
            stmt.setDate(5, Date.valueOf(khuyenMai.getNgayKetThuc()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Read: Tìm khuyến mãi theo mã khuyến mãi
    public KhuyenMai timKhuyenMaiTheoMa(String maKhuyenMai) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhuyenMai WHERE maKhuyenMai = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, maKhuyenMai);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new KhuyenMai(
                    rs.getString("maKhuyenMai"),
                    rs.getString("tenKhuyenMai"),
                    rs.getDouble("phanTramGiamGia"),
                    rs.getDate("ngayBatDau").toLocalDate(),
                    rs.getDate("ngayKetThuc").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // Read: Lấy danh sách tất cả khuyến mãi
    public List<KhuyenMai> layDanhSachKhuyenMai() {
        List<KhuyenMai> danhSachKhuyenMai = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhuyenMai";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                KhuyenMai khuyenMai = new KhuyenMai(
                    rs.getString("maKhuyenMai"),
                    rs.getString("tenKhuyenMai"),
                    rs.getDouble("phanTramGiamGia"),
                    rs.getDate("ngayBatDau").toLocalDate(),
                    rs.getDate("ngayKetThuc").toLocalDate()
                );
                danhSachKhuyenMai.add(khuyenMai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return danhSachKhuyenMai;
    }

    // Update: Cập nhật thông tin khuyến mãi
    public boolean capNhatKhuyenMai(KhuyenMai khuyenMai) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "UPDATE KhuyenMai SET tenKhuyenMai = ?, phanTramGiamGia = ?, ngayBatDau = ?, ngayKetThuc = ? WHERE maKhuyenMai = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, khuyenMai.getTenKhuyenMai());
            stmt.setDouble(2, khuyenMai.getPhanTramGiamGia());
            stmt.setDate(3, Date.valueOf(khuyenMai.getNgayBatDau()));
            stmt.setDate(4, Date.valueOf(khuyenMai.getNgayKetThuc()));
            stmt.setString(5, khuyenMai.getMaKhuyenMai());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Delete: Xóa khuyến mãi
    public boolean xoaKhuyenMai(String maKhuyenMai) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "DELETE FROM KhuyenMai WHERE maKhuyenMai = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, maKhuyenMai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}