package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.HoaDon.PhuongThucThanhToan;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_DAO {

    // Thêm hóa đơn mới
    public boolean themHoaDon(HoaDon hoaDon) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, " +
                         "tienKhachDua, thanhTien, tienTraLai, maKhuyenMai, maNhanVien, maKhachHang) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, hoaDon.getMaHoaDon());
            stmt.setDate(2, Date.valueOf(hoaDon.getNgayTaoHoaDon()));
            stmt.setString(3, convertPhuongThucThanhToanToDBValue(hoaDon.getPhuongThucThanhToan()));
            stmt.setDouble(4, hoaDon.getPhanTramGiamGia());
            stmt.setBigDecimal(5, hoaDon.getTienKhachDua());
            stmt.setBigDecimal(6, hoaDon.getThanhTien());
            stmt.setBigDecimal(7, hoaDon.getTienTraLai());
            stmt.setString(8, hoaDon.getMaKhuyenMai());
            stmt.setString(9, hoaDon.getMaNhanVien());
            stmt.setString(10, hoaDon.getMaKhachHang());
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

    // Tìm hóa đơn theo mã
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
                    rs.getString("maNhanVien"),
                    rs.getString("maKhachHang") // Thêm tham số mới
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // [Phần đóng kết nối giữ nguyên]
        }
        return null;
    }

    // Lấy danh sách tất cả hóa đơn
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
                    rs.getString("maNhanVien"),
                    rs.getString("maKhachHang") // Thêm tham số mới
                );
                danhSachHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // [Phần đóng kết nối giữ nguyên]
        }
        return danhSachHoaDon;
    }

    // Cập nhật hóa đơn
    public boolean capNhatHoaDon(HoaDon hoaDon) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "UPDATE HoaDon SET ngayTaoHoaDon = ?, phuongThucThanhToan = ?, phanTramGiamGia = ?, " +
                         "tienKhachDua = ?, thanhTien = ?, tienTraLai = ?, maKhuyenMai = ?, maNhanVien = ?, " +
                         "maKhachHang = ? WHERE maHoaDon = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(hoaDon.getNgayTaoHoaDon()));
            stmt.setString(2, hoaDon.getPhuongThucThanhToan().toString());
            stmt.setDouble(3, hoaDon.getPhanTramGiamGia());
            stmt.setBigDecimal(4, hoaDon.getTienKhachDua());
            stmt.setBigDecimal(5, hoaDon.getThanhTien());
            stmt.setBigDecimal(6, hoaDon.getTienTraLai());
            stmt.setString(7, hoaDon.getMaKhuyenMai());
            stmt.setString(8, hoaDon.getMaNhanVien());
            stmt.setString(9, hoaDon.getMaKhachHang());
            stmt.setString(10, hoaDon.getMaHoaDon());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // [Phần đóng kết nối giữ nguyên]
        }
    }

    // [Các phương thức khác giữ nguyên]
    
    public String taoMaHoaDonMoi() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            
            // Lấy ngày hiện tại dưới dạng yyyyMMdd
            String ngayHienTai = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            
            // Tìm hóa đơn có mã lớn nhất trong ngày
            String sql = "SELECT MAX(maHoaDon) FROM HoaDon WHERE maHoaDon LIKE ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, ngayHienTai + "HD%");
            rs = stmt.executeQuery();
            
            int soThuTu = 1;
            if (rs.next()) {
                String maMax = rs.getString(1);
                if (maMax != null) {
                    // Lấy phần số từ mã hóa đơn (6 chữ số cuối)
                    String soStr = maMax.substring(maMax.length() - 6);
                    soThuTu = Integer.parseInt(soStr) + 1;
                }
            }
            
            // Định dạng số thứ tự thành 6 chữ số, thêm số 0 ở đầu nếu cần
            String soThuTuStr = String.format("%06d", soThuTu);
            
            // Tạo mã hóa đơn mới: yyyyMMdd + "HD" + 6 chữ số
            return ngayHienTai + "HD" + soThuTuStr;
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Trường hợp lỗi, trả về mã mặc định
            return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "HD000001";
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
    }
    
    private String convertPhuongThucThanhToanToDBValue(PhuongThucThanhToan pttt) {
        switch (pttt.toString()) {
            case "Tiền mặt": return "tienMat";
            case "ATM": return "atm";
            case "Internet Banking": return "internetBanking";
            default: return pttt.toString();
        }
    }
}