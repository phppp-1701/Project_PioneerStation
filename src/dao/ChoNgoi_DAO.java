package dao;

import connectDB.ConnectDB;
import entity.ChoNgoi;
import entity.Tau.LoaiTau;
import entity.Toa.LoaiToa;

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
     * Tìm chỗ ngồi theo mã chỗ ngồi
     * @param maChoNgoi Mã chỗ ngồi cần tìm
     * @return Đối tượng ChoNgoi nếu tìm thấy, null nếu không tìm thấy hoặc có lỗi
     */
    public ChoNgoi timChoNgoiTheoMaChoNgoi(String maChoNgoi) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ChoNgoi choNgoi = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChoNgoi WHERE maChoNgoi = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maChoNgoi);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tenChoNgoi = rs.getString("tenChoNgoi");
                String trangThaiStr = rs.getString("trangThai");
                String maToa = rs.getString("maToa");
                String maChuyenTau = rs.getString("maChuyenTau");
                BigDecimal giaCho = rs.getBigDecimal("giaCho");
                ChoNgoi.TrangThaiChoNgoi trangThai = ChoNgoi.TrangThaiChoNgoi.valueOf(trangThaiStr);
                choNgoi = new ChoNgoi(maChoNgoi, tenChoNgoi, trangThai, maToa, maChuyenTau, giaCho);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tìm chỗ ngồi theo mã chỗ ngồi: " + maChoNgoi, ex);
            return null;
        } finally {
            closeResources(rs, stmt);
        }
        return choNgoi;
    }
    
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
    
    public double tinhGiaCho(LoaiTau loaiTau, LoaiToa loaiToa) {
        if (loaiTau == LoaiTau.SE) {
            if (loaiToa == LoaiToa.ngoiMemDieuHoa) {
                return 500000.00;
            } else if (loaiToa == LoaiToa.giuongNamDieuHoa) {
                return 800000.00;
            }
        } else if (loaiTau == LoaiTau.TN) {
            if (loaiToa == LoaiToa.ngoiMemDieuHoa) {
                return 400000.00;
            } else if (loaiToa == LoaiToa.gheCungDieuHoa) {
                return 300000.00;
            }
        }
        return 0.0; // Trường hợp lỗi
    }
    
    public boolean themChoNgoi(String maChuyenTau, String maToa, int soCho, LoaiToa loaiToa, int soThuTuToa, double giaCho) {
        String sql = "INSERT INTO ChoNgoi (maChoNgoi, tenChoNgoi, trangThai, maToa, maChuyenTau, giaCho) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	String maChoNgoi = String.format("%sT%02dCN%02d", maChuyenTau, soThuTuToa, soCho);
            String tenChoNgoi = String.format("%02d", soCho);
            stmt.setString(1, maChoNgoi);
            stmt.setString(2, tenChoNgoi);
            stmt.setString(3, "chuaDat");
            stmt.setString(4, maToa);
            stmt.setString(5, maChuyenTau);
            stmt.setDouble(6, giaCho);
            int rows = stmt.executeUpdate();
            System.out.println("Thêm chỗ ngồi: maChoNgoi = " + maChoNgoi + ", maToa = " + maToa + ", maChuyenTau = " + maChuyenTau + ", rows = " + rows);
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chỗ ngồi: maChuyenTau = " + maChuyenTau + ", maToa = " + maToa + ", lỗi: " + e.getMessage());
            return false;
        }
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
    
    /**
     * Tìm giá chỗ ngồi theo mã toa và mã chuyến tàu
     * @param maToa Mã toa
     * @param maChuyenTau Mã chuyến tàu
     * @return Giá chỗ ngồi, null nếu không tìm thấy hoặc có lỗi
     */
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
    
    /**
     * Tìm danh sách chỗ ngồi trong khoảng từ mã chỗ ngồi 1 đến mã chỗ ngồi 2
     * @param maChoNgoi1 Mã chỗ ngồi bắt đầu
     * @param maChoNgoi2 Mã chỗ ngồi kết thúc
     * @return Danh sách các chỗ ngồi, trả về danh sách rỗng nếu không tìm thấy hoặc có lỗi
     */
    public List<ChoNgoi> timDanhSachChoNgoiTuMaDenMa(String maChoNgoi1, String maChoNgoi2) {
        List<ChoNgoi> dsChoNgoi = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChoNgoi " +
                         "WHERE maChoNgoi BETWEEN ? AND ? " +
                         "ORDER BY maChoNgoi";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maChoNgoi1.compareTo(maChoNgoi2) <= 0 ? maChoNgoi1 : maChoNgoi2);
            stmt.setString(2, maChoNgoi1.compareTo(maChoNgoi2) <= 0 ? maChoNgoi2 : maChoNgoi1);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String maChoNgoi = rs.getString("maChoNgoi");
                String tenChoNgoi = rs.getString("tenChoNgoi");
                String trangThaiStr = rs.getString("trangThai");
                String maToa = rs.getString("maToa");
                String maChuyenTau = rs.getString("maChuyenTau");
                BigDecimal giaCho = rs.getBigDecimal("giaCho");
                ChoNgoi.TrangThaiChoNgoi trangThai = ChoNgoi.TrangThaiChoNgoi.valueOf(trangThaiStr);
                ChoNgoi choNgoi = new ChoNgoi(maChoNgoi, tenChoNgoi, trangThai, maToa, maChuyenTau, giaCho);
                dsChoNgoi.add(choNgoi);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tìm chỗ ngồi từ mã " + maChoNgoi1 + " đến mã " + maChoNgoi2, ex);
            return new ArrayList<>();
        } finally {
            closeResources(rs, stmt);
        }
        return dsChoNgoi;
    }
}