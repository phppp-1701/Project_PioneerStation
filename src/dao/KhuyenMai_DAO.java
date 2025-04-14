package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhuyenMai;

public class KhuyenMai_DAO {
    // Thêm khuyến mãi mới
    public boolean themKhuyenMai(KhuyenMai khuyenMai) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO KhuyenMai(maKhuyenMai, tenKhuyenMai, phanTramGiamGia, ngayBatDau, ngayKetThuc) "
                       + "VALUES(?, ?, ?, ?, ?)";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, khuyenMai.getMaKhuyenMai());
            stmt.setString(2, khuyenMai.getTenKhuyenMai());
            stmt.setDouble(3, khuyenMai.getPhanTramGiamGia());
            stmt.setDate(4, java.sql.Date.valueOf(khuyenMai.getNgayBatDau()));
            stmt.setDate(5, java.sql.Date.valueOf(khuyenMai.getNgayKetThuc()));
            
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
    
    // Xóa khuyến mãi theo mã
    public boolean xoaKhuyenMai(String maKhuyenMai) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "DELETE FROM KhuyenMai WHERE maKhuyenMai = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maKhuyenMai);
            
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
    
    // Tìm khuyến mãi theo tên (tìm kiếm gần đúng)
    public List<KhuyenMai> timKhuyenMaiTheoTen(String tenKhuyenMai) {
        List<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhuyenMai WHERE tenKhuyenMai LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenKhuyenMai + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                km.setTenKhuyenMai(rs.getString("tenKhuyenMai"));
                km.setPhanTramGiamGia(rs.getDouble("phanTramGiamGia"));
                km.setNgayBatDau(rs.getDate("ngayBatDau").toLocalDate());
                km.setNgayKetThuc(rs.getDate("ngayKetThuc").toLocalDate());
              
                dsKhuyenMai.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsKhuyenMai;
    }
    
    // Kiểm tra trùng mã khuyến mãi
    public boolean kiemTraMaKhuyenMai(String maKhuyenMai) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM KhuyenMai WHERE maKhuyenMai = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maKhuyenMai);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
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
        return false;
    }
    
    // Lấy tất cả khuyến mãi (tùy chọn bổ sung)
    public List<KhuyenMai> getAllKhuyenMai() {
        List<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhuyenMai";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                km.setTenKhuyenMai(rs.getString("tenKhuyenMai"));
                km.setPhanTramGiamGia(rs.getDouble("phanTramGiamGia"));
                km.setNgayBatDau(rs.getDate("ngayBatDau").toLocalDate());
                km.setNgayKetThuc(rs.getDate("ngayKetThuc").toLocalDate());
                
                dsKhuyenMai.add(km);
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
        
        return dsKhuyenMai;
    }
}