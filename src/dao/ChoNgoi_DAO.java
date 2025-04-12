package dao;

import connectDB.ConnectDB;
import entity.ChoNgoi;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChoNgoi_DAO {
    private static final Logger logger = Logger.getLogger(ChoNgoi_DAO.class.getName());
    
    /**
     * Đếm số chỗ ngồi chưa đặt theo mã chuyến tàu và mã toa
     * @param maChuyenTau Mã chuyến tàu cần kiểm tra
     * @param maToa Mã toa cần kiểm tra
     * @return Số lượng chỗ ngồi chưa đặt, -1 nếu có lỗi
     */
    public int demSoChoNgoiChuaDat(String maChuyenTau, String maToa) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int soLuong = -1;
        
        try {
            // Lấy kết nối từ ConnectDB
            con = ConnectDB.getConnection();
            
            // Câu lệnh SQL đếm số chỗ ngồi chưa đặt
            String sql = "SELECT COUNT(*) AS so_luong " +
                         "FROM ChoNgoi " +
                         "WHERE maChuyenTau = ? AND maToa = ? AND trangThai = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maChuyenTau);
            stmt.setString(2, maToa);
            stmt.setString(3, ChoNgoi.TrangThaiChoNgoi.chuaDat.name());
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                soLuong = rs.getInt("so_luong");
            }
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi đếm số chỗ ngồi chưa đặt", ex);
            soLuong = -1; // Trả về -1 nếu có lỗi
        } finally {
            // Đóng các resource
            closeResources(rs, stmt);
        }
        
        return soLuong;
    }
    
    /**
     * Tìm danh sách chỗ ngồi theo mã toa
     * @param maToa Mã toa cần tìm
     * @return Danh sách các chỗ ngồi thuộc toa, trả về danh sách rỗng nếu không tìm thấy hoặc có lỗi
     */
    public List<ChoNgoi> timChoNgoiTheoMaToa(String maToa) {
        List<ChoNgoi> dsChoNgoi = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Lấy kết nối từ ConnectDB
            con = ConnectDB.getConnection();
            
            // Câu lệnh SQL lấy tất cả chỗ ngồi theo mã toa
            String sql = "SELECT * FROM ChoNgoi WHERE maToa = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maToa);
            
            rs = stmt.executeQuery();
            
            // Duyệt qua kết quả và tạo danh sách chỗ ngồi
            while (rs.next()) {
                String maChoNgoi = rs.getString("maChoNgoi");
                String tenChoNgoi = rs.getString("tenChoNgoi");
                String trangThaiStr = rs.getString("trangThai");
                String maChuyenTau = rs.getString("maChuyenTau");
                BigDecimal giaCho = rs.getBigDecimal("giaCho");
                
                // Chuyển đổi trạng thái từ String sang enum TrangThaiChoNgoi
                ChoNgoi.TrangThaiChoNgoi trangThai = ChoNgoi.TrangThaiChoNgoi.valueOf(trangThaiStr);
                
                // Tạo đối tượng ChoNgoi
                ChoNgoi choNgoi = new ChoNgoi(maChoNgoi, tenChoNgoi, trangThai, maToa, maChuyenTau, giaCho);
                dsChoNgoi.add(choNgoi);
            }
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tìm chỗ ngồi theo mã toa: " + maToa, ex);
            return new ArrayList<>(); // Trả về danh sách rỗng nếu có lỗi
        } finally {
            // Đóng các resource
            closeResources(rs, stmt);
        }
        
        return dsChoNgoi;
    }
    
    /**
     * Tìm danh sách chỗ ngồi theo mã toa và mã chuyến tàu
     * @param maToa Mã toa cần tìm
     * @param maChuyenTau Mã chuyến tàu cần tìm
     * @return Danh sách các chỗ ngồi thuộc toa và chuyến tàu, trả về danh sách rỗng nếu không tìm thấy hoặc có lỗi
     */
    public List<ChoNgoi> timChoNgoiTheoMaToaVaMaChuyenTau(String maToa, String maChuyenTau) {
        List<ChoNgoi> dsChoNgoi = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Lấy kết nối từ ConnectDB
            con = ConnectDB.getConnection();
            
            // Câu lệnh SQL lấy tất cả chỗ ngồi theo mã toa và mã chuyến tàu
            String sql = "SELECT * FROM ChoNgoi WHERE maToa = ? AND maChuyenTau = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maToa);
            stmt.setString(2, maChuyenTau);
            
            rs = stmt.executeQuery();
            
            // Duyệt qua kết quả và tạo danh sách chỗ ngồi
            while (rs.next()) {
                String maChoNgoi = rs.getString("maChoNgoi");
                String tenChoNgoi = rs.getString("tenChoNgoi");
                String trangThaiStr = rs.getString("trangThai");
                BigDecimal giaCho = rs.getBigDecimal("giaCho");
                
                // Chuyển đổi trạng thái từ String sang enum TrangThaiChoNgoi
                ChoNgoi.TrangThaiChoNgoi trangThai = ChoNgoi.TrangThaiChoNgoi.valueOf(trangThaiStr);
                
                // Tạo đối tượng ChoNgoi
                ChoNgoi choNgoi = new ChoNgoi(maChoNgoi, tenChoNgoi, trangThai, maToa, maChuyenTau, giaCho);
                dsChoNgoi.add(choNgoi);
            }
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tìm chỗ ngồi theo mã toa: " + maToa + " và mã chuyến tàu: " + maChuyenTau, ex);
            return new ArrayList<>(); // Trả về danh sách rỗng nếu có lỗi
        } finally {
            // Đóng các resource
            closeResources(rs, stmt);
        }
        
        return dsChoNgoi;
    }
    
    /**
     * Đóng các tài nguyên kết nối CSDL
     * @param rs ResultSet cần đóng
     * @param stmt PreparedStatement cần đóng
     */
    private void closeResources(ResultSet rs, PreparedStatement stmt) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
        }
    }
    
    public BigDecimal timGiaChoTheoMaToaVaMaChuyenTau(String maToa, String maChuyenTau) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        BigDecimal giaCho = null;
        
        try {
            // Lấy kết nối từ ConnectDB
            con = ConnectDB.getConnection();
            
            // Câu lệnh SQL lấy giá chỗ ngồi theo mã toa và mã chuyến tàu
            String sql = "SELECT giaCho FROM ChoNgoi WHERE maToa = ? AND maChuyenTau = ? LIMIT 1";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maToa);
            stmt.setString(2, maChuyenTau);
            
            rs = stmt.executeQuery();
            
            // Lấy giá chỗ nếu có kết quả
            if (rs.next()) {
                giaCho = rs.getBigDecimal("giaCho");
            }
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tìm giá chỗ ngồi theo mã toa: " + maToa + " và mã chuyến tàu: " + maChuyenTau, ex);
            return null; // Trả về null nếu có lỗi
        } finally {
            // Đóng các resource
            closeResources(rs, stmt);
        }
        
        return giaCho;
    }
    
    
}