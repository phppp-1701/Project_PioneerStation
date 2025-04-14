package dao;

import connectDB.ConnectDB;
import entity.HoaDon;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_DAO {

    // Create: Thêm hóa đơn mới
    public boolean themHoaDon(HoaDon hoaDon) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, tienKhachDua, thanhTien, tienTraLai, maKhuyenMai, maNhanVien) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, hoaDon.getMaHoaDon());
            stmt.setDate(2, Date.valueOf(hoaDon.getNgayTaoHoaDon()));
            stmt.setString(3, hoaDon.getPhuongThucThanhToan().toString());
            stmt.setDouble(4, hoaDon.getPhanTramGiamGia());
            stmt.setBigDecimal(5, hoaDon.getTienKhachDua());
            stmt.setBigDecimal(6, hoaDon.getThanhTien());
            stmt.setBigDecimal(7, hoaDon.getTienTraLai());
            stmt.setString(8, hoaDon.getMaKhuyenMai());
            stmt.setString(9, hoaDon.getMaNhanVien());
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

    // Read: Tìm hóa đơn theo mã hóa đơn
    public HoaDon timHoaDonTheoMa(String maHoaDon) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, maHoaDon);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new HoaDon(
                    rs.getString("maHoaDon"),
                    rs.getDate("ngayTaoHoaDon").toLocalDate(),
                    HoaDon.PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")),
                    rs.getDouble("phanTramGiamGia"),
                    rs.getBigDecimal("tienKhachDua"),
                    rs.getBigDecimal("thanhTien"),
                    rs.getBigDecimal("tienTraLai"),
                    rs.getString("maKhuyenMai"),
                    rs.getString("maNhanVien")
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

    // Read: Lấy danh sách tất cả hóa đơn
    public List<HoaDon> layDanhSachHoaDon() {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM HoaDon";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HoaDon hoaDon = new HoaDon(
                    rs.getString("maHoaDon"),
                    rs.getDate("ngayTaoHoaDon").toLocalDate(),
                    HoaDon.PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")),
                    rs.getDouble("phanTramGiamGia"),
                    rs.getBigDecimal("tienKhachDua"),
                    rs.getBigDecimal("thanhTien"),
                    rs.getBigDecimal("tienTraLai"),
                    rs.getString("maKhuyenMai"),
                    rs.getString("maNhanVien")
                );
                danhSachHoaDon.add(hoaDon);
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
        return danhSachHoaDon;
    }

    // Update: Cập nhật thông tin hóa đơn
    public boolean capNhatHoaDon(HoaDon hoaDon) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "UPDATE HoaDon SET ngayTaoHoaDon = ?, phuongThucThanhToan = ?, phanTramGiamGia = ?, tienKhachDua = ?, thanhTien = ?, tienTraLai = ?, maKhuyenMai = ?, maNhanVien = ? WHERE maHoaDon = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(hoaDon.getNgayTaoHoaDon()));
            stmt.setString(2, hoaDon.getPhuongThucThanhToan().toString());
            stmt.setDouble(3, hoaDon.getPhanTramGiamGia());
            stmt.setBigDecimal(4, hoaDon.getTienKhachDua());
            stmt.setBigDecimal(5, hoaDon.getThanhTien());
            stmt.setBigDecimal(6, hoaDon.getTienTraLai());
            stmt.setString(7, hoaDon.getMaKhuyenMai());
            stmt.setString(8, hoaDon.getMaNhanVien());
            stmt.setString(9, hoaDon.getMaHoaDon());
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

    // Delete: Xóa hóa đơn
    public boolean xoaHoaDon(String maHoaDon) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "DELETE FROM HoaDon WHERE maHoaDon = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, maHoaDon);
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