package dao;

import entity.Ga;
import connectDB.ConnectDB;
import java.sql.*;
import java.util.ArrayList;
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
     * Đóng kết nối khi không sử dụng nữa
     */
    public void close() {
        ConnectDB.getInstance().disconnect();
    }
}