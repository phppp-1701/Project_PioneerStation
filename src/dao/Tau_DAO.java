package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Tau;
import entity.Tau.LoaiTau;
import entity.Tau.TrangThaiTau;

public class Tau_DAO {
    // Lấy toàn bộ danh sách tàu
    public List<Tau> getAllTau() {
        List<Tau> dsTau = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM Tau";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Tau tau = new Tau();
                tau.setMaTau(rs.getString("maTau"));
                tau.setTenTau(rs.getString("tenTau"));
                
                String loaiTau = rs.getString("loaiTau");
                tau.setLoaiTau("SE".equals(loaiTau) ? LoaiTau.SE : ("TN".equals(loaiTau) ? LoaiTau.TN : LoaiTau.DP));
                
                // Xử lý enum TrangThaiTau
                String trangThai = rs.getString("trangThai");
                tau.setTrangThaiTau("baoTri".equals(trangThai) 
                    ? TrangThaiTau.baoTri 
                    : TrangThaiTau.hoatDong);
                
                dsTau.add(tau);
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
        
        return dsTau;
    }
    

    
    // Tìm tàu theo tên (tìm kiếm gần đúng)
    public List<Tau> timTauTheoTen(String tenTau) {
        List<Tau> dsTau = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM Tau WHERE tenTau LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenTau + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Tau tau = new Tau();
                tau.setMaTau(rs.getString("maTau"));
                tau.setTenTau(rs.getString("tenTau"));
                
                String loaiTau = rs.getString("loaiTau");
                tau.setLoaiTau("SE".equals(loaiTau) ? LoaiTau.SE : ("TN".equals(loaiTau) ? LoaiTau.TN : LoaiTau.DP));
                
                // Xử lý enum TrangThaiTau
                String trangThai = rs.getString("trangThai");
                tau.setTrangThaiTau("baoTri".equals(trangThai) 
                    ? TrangThaiTau.baoTri 
                    : TrangThaiTau.hoatDong);
                
                dsTau.add(tau);
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
        
        return dsTau;
    }
    
    // Thêm tàu mới
    public boolean themTau(Tau tau) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO Tau (maTau, tenTau, loaiTau, trangThai) " +
                         "VALUES (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);

            stmt.setString(1, tau.getMaTau());
            stmt.setString(2, tau.getTenTau());
            
            String loaiTau = (tau.getLoaiTau() == LoaiTau.SE) ? "SE" : (tau.getLoaiTau() == LoaiTau.TN) ? "TN" : "DP";
            stmt.setString(3, loaiTau);

            
            // Xử lý enum TrangThaiTau
            String trangThai = (tau.getTrangThaiTau() == TrangThaiTau.baoTri) ? "baoTri" : "hoatDong";
            stmt.setString(4, trangThai);

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
    
    // Cập nhật thông tin tàu
    public boolean capNhatTau(Tau tau) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE Tau SET tenTau = ?, loaiTau = ?, trangThai = ? WHERE maTau = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tau.getTenTau());
            
            String loaiTau = (tau.getLoaiTau() == LoaiTau.SE) ? "SE" : (tau.getLoaiTau() == LoaiTau.TN) ? "TN" : "DP";
            stmt.setString(2, loaiTau);

            
            // Xử lý enum TrangThaiTau
            String trangThai = (tau.getTrangThaiTau() == TrangThaiTau.baoTri) ? "baoTri" : "hoatDong";
            stmt.setString(3, trangThai);
            
            stmt.setString(4, tau.getMaTau());
            
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
    
    public String layMaTauCuoiCung(LoaiTau loaiTau) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String maCuoiCung = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT TOP 1 maTau FROM Tau WHERE maTau LIKE ? ORDER BY maTau DESC";
            stmt = con.prepareStatement(sql);
            
            // Ví dụ: 2025SE%
            String prefix = Year.now().getValue() + loaiTau.name();
            stmt.setString(1, prefix + "%");

            rs = stmt.executeQuery();
            if (rs.next()) {
                maCuoiCung = rs.getString("maTau");
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

        return maCuoiCung;
    }

    
    public String taoMaTauMoi(LoaiTau loaiTau) {
        String maCuoiCung = layMaTauCuoiCung(loaiTau);
        int namHienTai = Year.now().getValue();
        String loai = loaiTau.name();
        String maSoMoi;

        if (maCuoiCung == null || maCuoiCung.isEmpty()) {
            maSoMoi = "000001";
        } else {
            int soCuoi = Integer.parseInt(maCuoiCung.substring(6));
            soCuoi++;
            maSoMoi = String.format("%06d", soCuoi);
        }

        return namHienTai + loai + maSoMoi;
    }

    
    // Kiểm tra trùng tên tàu
    public boolean kiemTraTrungTenTau(String tenTau) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM Tau WHERE tenTau = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tenTau);
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
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}