package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.KhachHang.LoaiThanhVien;
import entity.KhachHang.TrangThaiKhachHang;

public class KhachHang_DAO {
	//Xuat toan bo danh sach
    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setTenKhachHang(rs.getString("tenKhachHang"));
                kh.setCCCD_HoChieu(rs.getString("CCCD_HoChieu"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                
                // Xử lý enum LoaiThanhVien
                String loaiTV = rs.getString("loaiThanhVien");
                kh.setLoaiThanhVien("vip".equals(loaiTV) ? LoaiThanhVien.vip : LoaiThanhVien.thanThiet);
                
                // Xử lý enum TrangThaiKhachHang
                String trangThai = rs.getString("trangThaiKhachHang");
                kh.setTrangThaiKhachHang("voHieuHoa".equals(trangThai) 
                    ? TrangThaiKhachHang.voHieuHoa 
                    : TrangThaiKhachHang.hoatDong);
                
                dsKhachHang.add(kh);
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
        
        return dsKhachHang;
    }
    
    //Tim theo so dien thoai
    public List<KhachHang> timKhachHangTheoSoDienThoai(String soDienThoai) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE soDienThoai LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + soDienThoai + "%"); // Tìm kiếm gần đúng
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setTenKhachHang(rs.getString("tenKhachHang"));
                kh.setCCCD_HoChieu(rs.getString("CCCD_HoChieu"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                
                // Xử lý enum LoaiThanhVien
                String loaiTV = rs.getString("loaiThanhVien");
                kh.setLoaiThanhVien("vip".equals(loaiTV) ? LoaiThanhVien.vip : LoaiThanhVien.thanThiet);
                
                // Xử lý enum TrangThaiKhachHang
                String trangThai = rs.getString("trangThaiKhachHang");
                kh.setTrangThaiKhachHang("voHieuHoa".equals(trangThai) 
                    ? TrangThaiKhachHang.voHieuHoa 
                    : TrangThaiKhachHang.hoatDong);
                
                dsKhachHang.add(kh);
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
        
        return dsKhachHang;
    } 
    
    //Tim khach hang theo ten
    public List<KhachHang> timKhachHangTheoTen(String tenKhachHang) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE tenKhachHang LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenKhachHang + "%"); // Tìm kiếm gần đúng
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setTenKhachHang(rs.getString("tenKhachHang"));
                kh.setCCCD_HoChieu(rs.getString("CCCD_HoChieu"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                
                // Xử lý enum LoaiThanhVien
                String loaiTV = rs.getString("loaiThanhVien");
                kh.setLoaiThanhVien("vip".equals(loaiTV) ? LoaiThanhVien.vip : LoaiThanhVien.thanThiet);
                
                // Xử lý enum TrangThaiKhachHang
                String trangThai = rs.getString("trangThaiKhachHang");
                kh.setTrangThaiKhachHang("voHieuHoa".equals(trangThai) 
                    ? TrangThaiKhachHang.voHieuHoa 
                    : TrangThaiKhachHang.hoatDong);
                
                dsKhachHang.add(kh);
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
        
        return dsKhachHang;
    }
    
    //Them khach hang
    public boolean themKhachHang(KhachHang khachHang) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO KhachHang(maKhachHang, tenKhachHang, CCCD_HoChieu, soDienThoai, email, loaiThanhVien, trangThaiKhachHang) "
                       + "VALUES(?, ?, ?, ?, ?, ?, ?)";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, khachHang.getMaKhachHang());
            stmt.setString(2, khachHang.getTenKhachHang());
            stmt.setString(3, khachHang.getCCCD_HoChieu());
            stmt.setString(4, khachHang.getSoDienThoai());
            stmt.setString(5, khachHang.getEmail());
            stmt.setString(6, khachHang.getLoaiThanhVien().toString());
            stmt.setString(7, khachHang.getTrangThaiKhachHang().toString());
            
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
    
    //Cap nhat khach hang
    public boolean capNhatKhachHang(KhachHang khachHang) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE KhachHang SET tenKhachHang = ?, CCCD_HoChieu = ?, soDienThoai = ?, "
                       + "email = ?, loaiThanhVien = ?, trangThaiKhachHang = ? WHERE maKhachHang = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, khachHang.getTenKhachHang());
            stmt.setString(2, khachHang.getCCCD_HoChieu());
            stmt.setString(3, khachHang.getSoDienThoai());
            stmt.setString(4, khachHang.getEmail());
            stmt.setString(5, khachHang.getLoaiThanhVien().toString());
            stmt.setString(6, khachHang.getTrangThaiKhachHang().toString());
            stmt.setString(7, khachHang.getMaKhachHang());
            
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
    
 // Phương thức kiểm tra trùng mã khách hàng
    public boolean kiemTraMaKhachHang(String maKhachHang) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM KhachHang WHERE maKhachHang = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maKhachHang);
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

    // Phương thức kiểm tra trùng CCCD/Hộ chiếu
    public boolean kiemTraCCCD(String cccd) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM KhachHang WHERE CCCD_HoChieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, cccd);
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
}
