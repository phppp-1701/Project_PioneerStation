package dao;

import connectDB.ConnectDB;
import entity.Ve;
import entity.Ve.TrangThaiVe;
import entity.VeTam.LoaiKhachHang;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ve_DAO {

    // Create: Thêm vé mới
    public boolean themVe(Ve ve) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, CCCD_HoChieu, ngaySinh, loaiKhachHang, giaVe, maHoaDon, maChoNgoi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, ve.getMaVe());
            stmt.setDate(2, Date.valueOf(ve.getNgayTaoVe()));
            stmt.setString(3, ve.getTrangThaiVe().toString());
            stmt.setString(4, ve.getTenKhachHang());
            stmt.setString(5, ve.getCCCD_HoChieu());
            stmt.setDate(6, ve.getNgaySinh() != null ? Date.valueOf(ve.getNgaySinh()) : null);
            stmt.setString(7, ve.getLoaiKhachHang().toString());
            stmt.setBigDecimal(8, ve.getGiaVe());
            stmt.setString(9, ve.getMaHoaDon());
            stmt.setString(10, ve.getMaChoNgoi());
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

    // Read: Tìm vé theo mã vé
    public Ve timVeTheoMa(String maVe) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ve WHERE maVe = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, maVe);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ve(
                    rs.getString("maVe"),
                    rs.getDate("ngayTaoVe").toLocalDate(),
                    TrangThaiVe.valueOf(rs.getString("trangThaiVe")),
                    rs.getString("tenKhachHang"),
                    rs.getString("CCCD_HoChieu"),
                    rs.getDate("ngaySinh") != null ? rs.getDate("ngaySinh").toLocalDate() : null,
                    LoaiKhachHang.valueOf(rs.getString("loaiKhachHang")),
                    rs.getBigDecimal("giaVe"),
                    rs.getString("maHoaDon"),
                    rs.getString("maChoNgoi")
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

    // Read: Lấy danh sách tất cả vé
    public List<Ve> layDanhSachVe() {
        List<Ve> danhSachVe = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ve";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Ve ve = new Ve(
                    rs.getString("maVe"),
                    rs.getDate("ngayTaoVe").toLocalDate(),
                    TrangThaiVe.valueOf(rs.getString("trangThaiVe")),
                    rs.getString("tenKhachHang"),
                    rs.getString("CCCD_HoChieu"),
                    rs.getDate("ngaySinh") != null ? rs.getDate("ngaySinh").toLocalDate() : null,
                    LoaiKhachHang.valueOf(rs.getString("loaiKhachHang")),
                    rs.getBigDecimal("giaVe"),
                    rs.getString("maHoaDon"),
                    rs.getString("maChoNgoi")
                );
                danhSachVe.add(ve);
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
        return danhSachVe;
    }

    // Update: Cập nhật thông tin vé
    public boolean capNhatVe(Ve ve) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "UPDATE Ve SET ngayTaoVe = ?, trangThaiVe = ?, tenKhachHang = ?, CCCD_HoChieu = ?, ngaySinh = ?, loaiKhachHang = ?, giaVe = ?, maHoaDon = ?, maChoNgoi = ? WHERE maVe = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(ve.getNgayTaoVe()));
            stmt.setString(2, ve.getTrangThaiVe().toString());
            stmt.setString(3, ve.getTenKhachHang());
            stmt.setString(4, ve.getCCCD_HoChieu());
            stmt.setDate(5, ve.getNgaySinh() != null ? Date.valueOf(ve.getNgaySinh()) : null);
            stmt.setString(6, ve.getLoaiKhachHang().toString());
            stmt.setBigDecimal(7, ve.getGiaVe());
            stmt.setString(8, ve.getMaHoaDon());
            stmt.setString(9, ve.getMaChoNgoi());
            stmt.setString(10, ve.getMaVe());
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

    // Delete: Xóa vé
    public boolean xoaVe(String maVe) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "DELETE FROM Ve WHERE maVe = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, maVe);
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
    
 // Tạo mã vé mới theo mã chỗ ngồi, giờ và ngày tạo vé
    public String taoMaVeMoiTheoMaChoGioNgay(String maChoNgoi, LocalDate ngayTaoVe, LocalTime gioTaoVe) {
        // Định dạng ngày: ddMMyy
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyy");
        String ngayFormat = ngayTaoVe.format(dateFormatter);

        // Định dạng giờ: HHmm
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        String gioFormat = gioTaoVe.format(timeFormatter);

        // Kết hợp: [Mã chỗ ngắn][Ngày][Giờ][Số tự động]
        // Ví dụ: CN00114102514301234
        return maChoNgoi + ngayFormat + gioFormat;
    }
    
 // Read: Tìm danh sách vé theo mã hóa đơn
    public List<Ve> timVeTheoMaHoaDon(String maHoaDon) {
        List<Ve> danhSachVe = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ve WHERE maHoaDon = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, maHoaDon);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ve ve = new Ve(
                    rs.getString("maVe"),
                    rs.getDate("ngayTaoVe").toLocalDate(),
                    TrangThaiVe.valueOf(rs.getString("trangThaiVe")),
                    rs.getString("tenKhachHang"),
                    rs.getString("CCCD_HoChieu"),
                    rs.getDate("ngaySinh") != null ? rs.getDate("ngaySinh").toLocalDate() : null,
                    LoaiKhachHang.valueOf(rs.getString("loaiKhachHang")),
                    rs.getBigDecimal("giaVe"),
                    rs.getString("maHoaDon"),
                    rs.getString("maChoNgoi")
                );
                danhSachVe.add(ve);
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
        return danhSachVe;
    }
    
 // Read: Tìm danh sách vé theo tên khách hàng (tìm kiếm tương đối)
    public List<Ve> timVeTheoTenKhachHang(String tenKhachHang) {
        List<Ve> danhSachVe = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ve WHERE tenKhachHang LIKE ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + tenKhachHang + "%"); // Tìm kiếm tương đối
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ve ve = new Ve(
                    rs.getString("maVe"),
                    rs.getDate("ngayTaoVe").toLocalDate(),
                    TrangThaiVe.valueOf(rs.getString("trangThaiVe")),
                    rs.getString("tenKhachHang"),
                    rs.getString("CCCD_HoChieu"),
                    rs.getDate("ngaySinh") != null ? rs.getDate("ngaySinh").toLocalDate() : null,
                    LoaiKhachHang.valueOf(rs.getString("loaiKhachHang")),
                    rs.getBigDecimal("giaVe"),
                    rs.getString("maHoaDon"),
                    rs.getString("maChoNgoi")
                );
                danhSachVe.add(ve);
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
        return danhSachVe;
    }

 // Read: Tìm danh sách vé theo ngày tạo vé
    public List<Ve> timVeTheoNgayTao(LocalDate ngayTaoVe) {
        List<Ve> danhSachVe = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ve WHERE ngayTaoVe = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(ngayTaoVe));
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ve ve = new Ve(
                    rs.getString("maVe"),
                    rs.getDate("ngayTaoVe").toLocalDate(),
                    TrangThaiVe.valueOf(rs.getString("trangThaiVe")),
                    rs.getString("tenKhachHang"),
                    rs.getString("CCCD_HoChieu"),
                    rs.getDate("ngaySinh") != null ? rs.getDate("ngaySinh").toLocalDate() : null,
                    LoaiKhachHang.valueOf(rs.getString("loaiKhachHang")),
                    rs.getBigDecimal("giaVe"),
                    rs.getString("maHoaDon"),
                    rs.getString("maChoNgoi")
                );
                danhSachVe.add(ve);
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
        return danhSachVe;
    }
}