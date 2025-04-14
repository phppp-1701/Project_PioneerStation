package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ga;

public class Ga_DAO {
    // Thêm ga mới
    public boolean themGa(Ga ga) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO Ga(maGa, tenGa, diaChi) VALUES(?, ?, ?)";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, ga.getMaGa());
            stmt.setString(2, ga.getTenGa());
            stmt.setString(3, ga.getDiaChi());
            
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
    
    // Xóa ga theo mã
    public boolean xoaGa(String maGa) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "DELETE FROM Ga WHERE maGa = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maGa);
            
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
    
    // Tìm ga theo tên (tìm kiếm gần đúng)
    public List<Ga> timGaTheoTen(String tenGa) {
        List<Ga> dsGa = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ga WHERE tenGa LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenGa + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Ga ga = new Ga();
                ga.setMaGa(rs.getString("maGa"));
                ga.setTenGa(rs.getString("tenGa"));
                ga.setDiaChi(rs.getString("diaChi"));
                
                dsGa.add(ga);
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
        
        return dsGa;
    }
    
    // Lấy tất cả ga
    public List<Ga> getAllGa() {
        List<Ga> dsGa = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ga";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Ga ga = new Ga();
                ga.setMaGa(rs.getString("maGa"));
                ga.setTenGa(rs.getString("tenGa"));
                ga.setDiaChi(rs.getString("diaChi"));
                
                dsGa.add(ga);
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
        
        return dsGa;
    }
    
    // Kiểm tra trùng mã ga
    public boolean kiemTraMaGa(String maGa) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM Ga WHERE maGa = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maGa);
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
    
    // Cập nhật thông tin ga
    public boolean capNhatGa(Ga ga) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE Ga SET tenGa = ?, diaChi = ? WHERE maGa = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, ga.getTenGa());
            stmt.setString(2, ga.getDiaChi());
            stmt.setString(3, ga.getMaGa());
            
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
}