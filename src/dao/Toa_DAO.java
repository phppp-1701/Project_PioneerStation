package dao;

import connectDB.ConnectDB;
import entity.Toa;
import entity.Toa.LoaiToa;
import entity.Toa.TrangThaiToa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Toa_DAO {
    /**
     * Tìm toa theo mã toa
     * @param maToa Mã toa cần tìm
     * @return Đối tượng Toa nếu tìm thấy, null nếu không tìm thấy
     */
    public Toa getToaByMaToa(String maToa) throws SQLException {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Toa toa = null;

        try {
            String sql = "SELECT * FROM Toa WHERE maToa = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maToa);
            rs = stmt.executeQuery();

            if (rs.next()) {
                toa = new Toa(
                    rs.getString("maToa"),
                    rs.getString("tenToa"),
                    LoaiToa.valueOf(rs.getString("loaiToa")),
                    TrangThaiToa.valueOf(rs.getString("trangThai")),
                    rs.getString("maTau")
                );
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }

        return toa;
    }

    /**
     * Cập nhật thông tin toa theo tên và mã tàu
     * @param toa Đối tượng toa cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean updateToa(Toa toa) throws SQLException {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE Toa SET tenToa = ?, maTau = ? WHERE maToa = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, toa.getTenToa());
            stmt.setString(2, toa.getMaTau());
            stmt.setString(3, toa.getMaToa());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    /**
     * Tạo số lượng toa mới cho một tàu
     * @param soLuong Số lượng toa cần tạo
     * @param maTau Mã tàu mà các toa thuộc về
     * @return true nếu tạo thành công, false nếu thất bại
     */
    public boolean taoToaTheoSoLuong(int soLuong, String maTau) throws SQLException {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        
        try {
            // Lấy số lượng toa hiện tại để xác định số thứ tự bắt đầu
            int soLuongToaHienTai = laySoLuongToaTheoMaTau(maTau);
            int soThuTuBatDau = soLuongToaHienTai + 1;

            // Tạo các toa mới
            for (int i = 0; i < soLuong; i++) {
                int soThuTu = soThuTuBatDau + i;
                String maToa = maTau + String.format("%02d", soThuTu); // Mã toa = mã tàu + số thứ tự 2 chữ số
                String tenToa = "Toa số " + soThuTu;
                
                Toa toa = new Toa(
                    maToa, 
                    tenToa, 
                    LoaiToa.ngoiMemDieuHoa, // Loại toa mặc định
                    TrangThaiToa.hoatDong,  // Trạng thái mặc định
                    maTau
                );
                
                // Thêm toa vào cơ sở dữ liệu
                String sql = "INSERT INTO Toa (maToa, tenToa, loaiToa, trangThai, maTau) VALUES (?, ?, ?, ?, ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, toa.getMaToa());
                stmt.setString(2, toa.getTenToa());
                stmt.setString(3, toa.getLoaiToa().name());
                stmt.setString(4, toa.getTrangThai().name());
                stmt.setString(5, toa.getMaTau());
                
                stmt.executeUpdate();
            }
            
            return true; // Trả về true nếu tất cả các toa được tạo thành công
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi để debug
            return false; // Trả về false nếu có lỗi xảy ra
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    /**
     * Lấy danh sách toa theo mã tàu
     * @param maTau Mã tàu
     * @return Danh sách các toa thuộc tàu
     */
    public List<Toa> getToaByMaTau(String maTau) throws SQLException {
        List<Toa> danhSachToa = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT * FROM Toa WHERE maTau = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maTau);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Toa toa = new Toa(
                    rs.getString("maToa"),
                    rs.getString("tenToa"),
                    LoaiToa.valueOf(rs.getString("loaiToa")),
                    TrangThaiToa.valueOf(rs.getString("trangThai")),
                    rs.getString("maTau")
                );
                danhSachToa.add(toa);
            }
            
            return danhSachToa;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }

    /**
     * Lấy số lượng toa theo mã tàu
     * @param maTau Mã tàu
     * @return Số lượng toa của tàu
     */
    public int laySoLuongToaTheoMaTau(String maTau) throws SQLException {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int soLuongToa = 0;

        try {
            String sql = "SELECT COUNT(*) AS soLuong FROM Toa WHERE maTau = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maTau);
            rs = stmt.executeQuery();

            if (rs.next()) {
                soLuongToa = rs.getInt("soLuong");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }

        return soLuongToa;
    }
}