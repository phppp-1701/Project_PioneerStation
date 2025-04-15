package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChuyenTau;
import entity.Tau.LoaiTau;

public class ChuyenTau_DAO {
	public List<ChuyenTau> timChuyenTau(String maGaDi, String maGaDen, LocalDate ngayKhoiHanh, LoaiTau loaiTau) {
        List<ChuyenTau> danhSachChuyenTau = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Lấy kết nối từ ConnectDB
            conn = ConnectDB.getConnection();

            // Xây dựng câu truy vấn SQL động
            StringBuilder sql = new StringBuilder(
                "SELECT ct.maChuyenTau, ct.ngayKhoiHanh, ct.gioKhoiHanh, ct.ngayDuKien, ct.gioDuKien, " +
                "ct.maTau, ct.maTuyen, t.tenTau, t.loaiTau, " +
                "g1.tenGa AS tenGaDi, g2.tenGa AS tenGaDen " +
                "FROM ChuyenTau ct " +
                "INNER JOIN Tau t ON ct.maTau = t.maTau " +
                "INNER JOIN TuyenTau tt ON ct.maTuyen = tt.maTuyen " +
                "INNER JOIN Ga g1 ON tt.maGaDi = g1.maGa " +
                "INNER JOIN Ga g2 ON tt.maGaDen = g2.maGa " +
                "WHERE 1=1" // Điều kiện luôn đúng để dễ thêm các điều kiện khác
            );

            // Danh sách tham số cho PreparedStatement
            List<Object> params = new ArrayList<>();

            // Thêm điều kiện nếu có giá trị
            if (maGaDi != null) {
                sql.append(" AND tt.maGaDi = ?");
                params.add(maGaDi);
            }
            if (maGaDen != null) {
                sql.append(" AND tt.maGaDen = ?");
                params.add(maGaDen);
            }
            if (ngayKhoiHanh != null) {
                sql.append(" AND ct.ngayKhoiHanh = ?");
                params.add(java.sql.Date.valueOf(ngayKhoiHanh));
            }
            if (loaiTau != null) {
                sql.append(" AND t.loaiTau = ?");
                params.add(loaiTau.name());
            }

            pstmt = conn.prepareStatement(sql.toString());

            // Gán giá trị cho các tham số
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            rs = pstmt.executeQuery();

            // Duyệt qua kết quả và tạo đối tượng ChuyenTau
            while (rs.next()) {
                // Tạo đối tượng ChuyenTau
                ChuyenTau chuyenTau = new ChuyenTau();
                chuyenTau.setMaChuyenTau(rs.getString("maChuyenTau"));
                chuyenTau.setNgayKhoiHanh(rs.getDate("ngayKhoiHanh").toLocalDate());
                chuyenTau.setGioKhoiHanh(rs.getTime("gioKhoiHanh").toLocalTime());
                chuyenTau.setNgayDuKien(rs.getDate("ngayDuKien").toLocalDate());
                chuyenTau.setGioDuKien(rs.getTime("gioDuKien").toLocalTime());
                chuyenTau.setMaTau(rs.getString("maTau"));
                chuyenTau.setMaTuyen(rs.getString("maTuyen"));

                danhSachChuyenTau.add(chuyenTau);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return danhSachChuyenTau;
    }
}
