package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;

public class NhanVien_DAO {
    // Lấy toàn bộ danh sách nhân viên
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> dsNhanVien = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien"));
                nv.setTenNhanVien(rs.getString("tenNhanVien"));
                nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
                
                // Xử lý enum GioiTinh
                String gioiTinh = rs.getString("gioiTinh");
                nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);
                
                nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));
                
                // Xử lý enum ChucVu
                String chucVu = rs.getString("chucVu");
                if ("quanLy".equals(chucVu)) {
                    nv.setChucVu(ChucVu.quanLy);
                } else {
                    nv.setChucVu(ChucVu.banVe);
                }
                
                String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
                if ("hoatDong".equals(trangThaiNhanVien)) {
                    nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
                } else {
                    nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieu);
                }
                
                dsNhanVien.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsNhanVien;
    }
    
    // Tìm nhân viên theo mã
    public List<NhanVien> timNhanVienTheoMa(String maNhanVien) {
        List<NhanVien> dsNhanVien = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE maNhanVien LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + maNhanVien + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                NhanVien nv = createNhanVienFromResultSet(rs);
                dsNhanVien.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsNhanVien;
    }
    
    // Tìm nhân viên theo tên
    public List<NhanVien> timNhanVienTheoTen(String tenNhanVien) {
        List<NhanVien> dsNhanVien = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE tenNhanVien LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenNhanVien + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                NhanVien nv = createNhanVienFromResultSet(rs);
                dsNhanVien.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsNhanVien;
    }
    
    // Tìm nhân viên theo cả mã và tên
    public List<NhanVien> timNhanVienTheoMaVaTen(String maNhanVien, String tenNhanVien) {
        List<NhanVien> dsNhanVien = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE maNhanVien LIKE ? AND tenNhanVien LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + maNhanVien + "%");
            stmt.setString(2, "%" + tenNhanVien + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                NhanVien nv = createNhanVienFromResultSet(rs);
                dsNhanVien.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsNhanVien;
    }
    
    // Thêm nhân viên mới
    public boolean themNhanVien(NhanVien nhanVien) throws SQLException {
        String sql = "INSERT INTO NhanVien(maNhanVien, tenNhanVien, ngaySinh, gioiTinh, CCCD_HoChieu, chucVu, trangThaiNhanVien) "
                   + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, nhanVien.getMaNhanVien());
            stmt.setString(2, nhanVien.getTenNhanVien());
            stmt.setDate(3, java.sql.Date.valueOf(nhanVien.getNgaySinh()));
            stmt.setString(4, nhanVien.getGioiTinh().toString());
            stmt.setString(5, nhanVien.getCccd_HoChieu());
            stmt.setString(6, nhanVien.getChucVu().toString());
            stmt.setString(7, nhanVien.getTrangThaiNhanVien().toString());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean tonTaiNhanVien(String maNV, String cccd) throws SQLException {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE maNhanVien = ? OR CCCD_HoChieu = ?";
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, maNV);
            stmt.setString(2, cccd);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    

    
    // Cập nhật thông tin nhân viên
    public boolean capNhatNhanVien(NhanVien nhanVien) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE NhanVien SET tenNhanVien = ?, ngaySinh = ?, gioiTinh = ?, "
                       + "CCCD_HoChieu = ?, chucVu = ? ,trangThaiNhanVien = ? WHERE maNhanVien = ?";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, nhanVien.getTenNhanVien());
            stmt.setDate(2, java.sql.Date.valueOf(nhanVien.getNgaySinh()));
            
            // Giới tính
            stmt.setString(3, nhanVien.getGioiTinh().toString().toLowerCase()); // "nam" hoặc "nu"
            
            stmt.setString(4, nhanVien.getCccd_HoChieu());
            
            // Chức vụ
            stmt.setString(5, nhanVien.getChucVu().toString().toLowerCase()); // "quanly" hoặc "banve"
            
            // Trạng thái nhân viên
            stmt.setString(6, nhanVien.getTrangThaiNhanVien().toString().toLowerCase()); // "hoatdong" hoặc "vohieu"
            
            stmt.setString(7, nhanVien.getMaNhanVien());

            int rowsAffected = stmt.executeUpdate();
            thanhCong = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return thanhCong;
    }

    
    // Xóa nhân viên
    public boolean xoaNhanVien(String maNhanVien) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maNhanVien);
            
            int rowsAffected = stmt.executeUpdate();
            thanhCong = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return thanhCong;
    }
    
    // Helper method để tạo đối tượng NhanVien từ ResultSet
    private NhanVien createNhanVienFromResultSet(ResultSet rs) throws SQLException {
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(rs.getString("maNhanVien"));
        nv.setTenNhanVien(rs.getString("tenNhanVien"));
        nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
        
        // Xử lý enum GioiTinh
        String gioiTinh = rs.getString("gioiTinh");
        nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);
        
        nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));
        
        // Xử lý enum ChucVu
        String chucVu = rs.getString("chucVu");
        if ("quanLy".equals(chucVu)) {
            nv.setChucVu(ChucVu.quanLy);
        } else {
            nv.setChucVu(ChucVu.banVe);
        }
        
        String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
        if ("hoatDong".equals(trangThaiNhanVien)) {
            nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
        } else {
            nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieu);
        }
        
        return nv;
    }
}