package dao;

import entity.Ga;
import connectDB.ConnectDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Ga_DAO {
    public Ga_DAO() {
        // Khởi tạo kết nối khi tạo DAO
        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tìm kiếm ga theo mã
     * @param maGa Mã ga cần tìm
     * @return Đối tượng Ga nếu tìm thấy, null nếu không tìm thấy
     */
    public Ga timGaTheoMa(String maGa) {
        Ga ga = null;
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM Ga WHERE maGa = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maGa);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ga = new Ga(
                    rs.getString("maGa"),
                    rs.getString("tenGa"),
                    rs.getString("diaChi")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ga;
    }

    /**
     * Tìm kiếm ga theo tên (tìm kiếm tương đối)
     * @param tenGa Tên ga cần tìm (có thể là một phần của tên)
     * @return Danh sách các ga phù hợp
     */
    public List<Ga> timGaTheoTen(String tenGa) {
        List<Ga> dsGa = new ArrayList<>();
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM Ga WHERE tenGa LIKE ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenGa + "%");
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ga ga = new Ga(
                    rs.getString("maGa"),
                    rs.getString("tenGa"),
                    rs.getString("diaChi")
                );
                dsGa.add(ga);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsGa;
    }

    /**
     * Thêm ga mới vào database
     * @param ga Đối tượng ga cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themGa(Ga ga) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "INSERT INTO Ga (maGa, tenGa, diaChi) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, ga.getMaGa());
            stmt.setString(2, ga.getTenGa());
            stmt.setString(3, ga.getDiaChi());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật thông tin ga
     * @param ga Đối tượng ga với thông tin cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean capNhatGa(Ga ga) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "UPDATE Ga SET tenGa = ?, diaChi = ? WHERE maGa = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, ga.getTenGa());
            stmt.setString(2, ga.getDiaChi());
            stmt.setString(3, ga.getMaGa());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tạo mã ga mới với định dạng 2022GA0001
     * - 2022: Năm hiện tại
     * - GA: Cố định
     * - 0001: Số thứ tự tự tăng
     * @return Mã ga mới dạng String
     */
    public String taoMaGaMoi() {
        String maGaMoi = "";
        try (Connection con = ConnectDB.getConnection()) {
            int namHienTai = Calendar.getInstance().get(Calendar.YEAR);
            String prefix = namHienTai + "GA";

            String sql = "SELECT MAX(maGa) FROM Ga WHERE maGa LIKE ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();

            int soThuTu = 0;
            if (rs.next() && rs.getString(1) != null) {
                String maGaMax = rs.getString(1);
                String soThuTuStr = maGaMax.substring(prefix.length());
                soThuTu = Integer.parseInt(soThuTuStr);
            }

            soThuTu++;
            maGaMoi = prefix + String.format("%04d", soThuTu);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maGaMoi;
    }

    /**
     * Kiểm tra xem tên ga đã tồn tại trong database chưa
     * @param tenGa Tên ga cần kiểm tra
     * @return true nếu tên ga đã tồn tại, false nếu chưa
     */
    public boolean kiemTraTonTaiGaTheoTen(String tenGa) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT COUNT(*) FROM Ga WHERE tenGa = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tenGa);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu có ít nhất 1 ga trùng tên
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Đóng kết nối khi không sử dụng nữa
     */
    public void close() {
        ConnectDB.getInstance().disconnect();
    }
    
    /**
     * Tìm tên ga theo mã
     * @param maGa Mã ga cần tìm
     * @return Tên ga nếu tìm thấy, null nếu không tìm thấy
     */
    public String timTenGaTheoMa(String maGa) {
        String tenGa = null;
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT tenGa FROM Ga WHERE maGa = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maGa);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tenGa = rs.getString("tenGa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenGa;
    }
}