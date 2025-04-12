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
            con = ConnectDB.getConnection();
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
            soLuong = -1;
        } finally {
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
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChoNgoi WHERE maToa = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maToa);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String maChoNgoi = rs.getString("maChoNgoi");
                String tenChoNgoi = rs.getString("tenChoNgoi");
                String trangThaiStr = rs.getString("trangThai");
                String maChuyenTau = rs.getString("maChuyenTau");
                BigDecimal giaCho = rs.getBigDecimal("giaCho");
                ChoNgoi.TrangThaiChoNgoi trangThai = ChoNgoi.TrangThaiChoNgoi.valueOf(trangThaiStr);
                ChoNgoi choNgoi = new ChoNgoi(maChoNgoi, tenChoNgoi, trangThai, maToa, maChuyenTau, giaCho);
                dsChoNgoi.add(choNgoi);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tìm chỗ ngồi theo mã toa: " + maToa, ex);
            return new ArrayList<>();
        } finally {
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
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChoNgoi WHERE maToa = ? AND maChuyenTau = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maToa);
            stmt.setString(2, maChuyenTau);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String maChoNgoi = rs.getString("maChoNgoi");
                String tenChoNgoi = rs.getString("tenChoNgoi");
                String trangThaiStr = rs.getString("trangThai");
                BigDecimal giaCho = rs.getBigDecimal("giaCho");
                ChoNgoi.TrangThaiChoNgoi trangThai = ChoNgoi.TrangThaiChoNgoi.valueOf(trangThaiStr);
                ChoNgoi choNgoi = new ChoNgoi(maChoNgoi, tenChoNgoi, trangThai, maToa, maChuyenTau, giaCho);
                dsChoNgoi.add(choNgoi);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tìm chỗ ngồi theo mã toa: " + maToa + " và mã chuyến tàu: " + maChuyenTau, ex);
            return new ArrayList<>();
        } finally {
            closeResources(rs, stmt);
        }
        return dsChoNgoi;
    }
    
    /**
     * Cập nhật trạng thái chỗ ngồi theo mã chỗ ngồi và mã chuyến tàu
     * @param maChoNgoi Mã chỗ ngồi cần cập nhật
     * @param trangThai Trạng thái mới của chỗ ngồi
     * @param maChuyenTau Mã chuyến tàu liên quan
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean capNhatTrangThaiChoNgoi(String maChoNgoi, ChoNgoi.TrangThaiChoNgoi trangThai, String maChuyenTau) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean result = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE ChoNgoi SET trangThai = ? WHERE maChoNgoi = ? AND maChuyenTau = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, trangThai.name());
            stmt.setString(2, maChoNgoi);
            stmt.setString(3, maChuyenTau);
            
            int rowsAffected = stmt.executeUpdate();
            result = rowsAffected > 0;
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi cập nhật trạng thái chỗ ngồi: " + maChoNgoi, ex);
            result = false;
        } finally {
            closeResources(null, stmt);
        }
        
        return result;
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
            con = ConnectDB.getConnection();
            String sql = "SELECT giaCho FROM ChoNgoi WHERE maToa = ? AND maChuyenTau = ? LIMIT 1";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maToa);
            stmt.setString(2, maChuyenTau);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                giaCho = rs.getBigDecimal("giaCho");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tìm giá chỗ ngồi theo mã toa: " + maToa + " và mã chuyến tàu: " + maChuyenTau, ex);
            return null;
        } finally {
            closeResources(rs, stmt);
        }
        return giaCho;
    }
}