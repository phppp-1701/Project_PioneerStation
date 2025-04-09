package dao;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoan_DAO {
    /**
     * Thêm tài khoản mới vào cơ sở dữ liệu
     * @param taiKhoan Tài khoản cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themTaiKhoan(TaiKhoan taiKhoan) {
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO TaiKhoan (tenTaiKhoan, matKhau, maNhanVien) VALUES (?, ?, ?)";
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, taiKhoan.getTenTaiKhoan());
            stmt.setString(2, taiKhoan.getMatKhau());
            stmt.setString(3, taiKhoan.getMaNhanVien().getMaNhanVien());
            
            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cập nhật thông tin tài khoản
     * @param taiKhoan Tài khoản cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean capNhatTaiKhoan(TaiKhoan taiKhoan) {
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE TaiKhoan SET matKhau = ? WHERE tenTaiKhoan = ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, taiKhoan.getMatKhau());
            stmt.setString(2, taiKhoan.getTenTaiKhoan());
            
            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tìm tài khoản theo mã nhân viên
     * @param maNhanVien Mã nhân viên cần tìm
     * @return Đối tượng TaiKhoan nếu tìm thấy, null nếu không tìm thấy
     */
    public TaiKhoan timTaiKhoanTheoMaNhanVien(String maNhanVien) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TaiKhoan taiKhoan = null;
        NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM TaiKhoan WHERE maNhanVien = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maNhanVien);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tenTaiKhoan = rs.getString("tenTaiKhoan");
                String matKhau = rs.getString("matKhau");
                NhanVien nhanVien = nhanVienDAO.timNhanVienTheoMa(maNhanVien);
                
                taiKhoan = new TaiKhoan(tenTaiKhoan, matKhau, nhanVien);
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
        
        return taiKhoan;
    }
}
