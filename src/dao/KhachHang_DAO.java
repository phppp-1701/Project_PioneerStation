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
    
    
}
