package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import connectDB.ConnectDB;
import entity.ChuyenTau;
import entity.Tau;
import entity.Tau.LoaiTau;
import entity.Tau.TrangThaiTau;

public class ChuyenTau_DAO {
    public List<ChuyenTau> timChuyenTau(String maGaDi, String maGaDen, LocalDate ngayKhoiHanh, LoaiTau loaiTau) {
        List<ChuyenTau> danhSachChuyenTau = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();

            StringBuilder sql = new StringBuilder(
                "SELECT ct.maChuyenTau, ct.ngayKhoiHanh, ct.gioKhoiHanh, ct.ngayDuKien, ct.gioDuKien, " +
                "ct.maTau, ct.maTuyen, t.tenTau, t.loaiTau, " +
                "g1.tenGa AS tenGaDi, g2.tenGa AS tenGaDen " +
                "FROM ChuyenTau ct " +
                "INNER JOIN Tau t ON ct.maTau = t.maTau " +
                "INNER JOIN TuyenTau tt ON ct.maTuyen = tt.maTuyen " +
                "INNER JOIN Ga g1 ON tt.maGaDi = g1.maGa " +
                "INNER JOIN Ga g2 ON tt.maGaDen = g2.maGa " +
                "WHERE 1=1"
            );

            List<Object> params = new ArrayList<>();

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

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
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
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return danhSachChuyenTau;
    }

    public List<ChuyenTau> timChuyenTauTheoTenGaVaNgay(String tenGaDi, String tenGaDen, LocalDate ngayKhoiHanh) {
        List<ChuyenTau> danhSachChuyenTau = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();

            StringBuilder sql = new StringBuilder(
                "SELECT ct.maChuyenTau, ct.ngayKhoiHanh, ct.gioKhoiHanh, ct.ngayDuKien, ct.gioDuKien, " +
                "ct.maTau, ct.maTuyen, t.tenTau, t.loaiTau, " +
                "g1.tenGa AS tenGaDi, g2.tenGa AS tenGaDen " +
                "FROM ChuyenTau ct " +
                "INNER JOIN Tau t ON ct.maTau = t.maTau " +
                "INNER JOIN TuyenTau tt ON ct.maTuyen = tt.maTuyen " +
                "INNER JOIN Ga g1 ON tt.maGaDi = g1.maGa " +
                "INNER JOIN Ga g2 ON tt.maGaDen = g2.maGa " +
                "WHERE t.trangThai = ?"
            );

            List<Object> params = new ArrayList<>();
            params.add("hoatDong"); // Trạng thái từ bảng Tau

            if (tenGaDi != null && !tenGaDi.isEmpty()) {
                sql.append(" AND g1.tenGa LIKE ?");
                params.add("%" + tenGaDi + "%");
            }
            
            if (tenGaDen != null && !tenGaDen.isEmpty()) {
                sql.append(" AND g2.tenGa LIKE ?");
                params.add("%" + tenGaDen + "%");
            }
            
            if (ngayKhoiHanh != null) {
                sql.append(" AND ct.ngayKhoiHanh = ?");
                params.add(java.sql.Date.valueOf(ngayKhoiHanh));
            }

            pstmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
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
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return danhSachChuyenTau;
    }

    // Phương thức mới: Tìm chuyến tàu theo mã chuyến tàu
    public ChuyenTau timChuyenTauTheoMaChuyenTau(String maChuyenTau) {
        ChuyenTau chuyenTau = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();

            String sql = 
                "SELECT ct.maChuyenTau, ct.ngayKhoiHanh, ct.gioKhoiHanh, ct.ngayDuKien, ct.gioDuKien, " +
                "ct.maTau, ct.maTuyen, t.tenTau, t.loaiTau, t.trangThai, " +
                "g1.tenGa AS tenGaDi, g2.tenGa AS tenGaDen " +
                "FROM ChuyenTau ct " +
                "INNER JOIN Tau t ON ct.maTau = t.maTau " +
                "INNER JOIN TuyenTau tt ON ct.maTuyen = tt.maTuyen " +
                "INNER JOIN Ga g1 ON tt.maGaDi = g1.maGa " +
                "INNER JOIN Ga g2 ON tt.maGaDen = g2.maGa " +
                "WHERE ct.maChuyenTau = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maChuyenTau);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                chuyenTau = new ChuyenTau();
                chuyenTau.setMaChuyenTau(rs.getString("maChuyenTau"));
                chuyenTau.setNgayKhoiHanh(rs.getDate("ngayKhoiHanh").toLocalDate());
                chuyenTau.setGioKhoiHanh(rs.getTime("gioKhoiHanh").toLocalTime());
                chuyenTau.setNgayDuKien(rs.getDate("ngayDuKien").toLocalDate());
                chuyenTau.setGioDuKien(rs.getTime("gioDuKien").toLocalTime());
                chuyenTau.setMaTau(rs.getString("maTau"));
                chuyenTau.setMaTuyen(rs.getString("maTuyen"));

                // Tạo đối tượng Tau và gán thông tin
                Tau tau = new Tau();
                tau.setMaTau(rs.getString("maTau"));
                tau.setTenTau(rs.getString("tenTau"));
                tau.setLoaiTau(LoaiTau.valueOf(rs.getString("loaiTau")));
                tau.setTrangThaiTau(TrangThaiTau.valueOf(rs.getString("trangThai")));
                
                // Bạn có thể thêm logic để gán đối tượng Tau vào ChuyenTau nếu lớp ChuyenTau có thuộc tính Tau
                // Ví dụ: chuyenTau.setTau(tau); (nếu có setter này trong entity ChuyenTau)
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return chuyenTau;
    }
    
    public boolean themChuyenTau(ChuyenTau chuyenTau) {
        String sql = "INSERT INTO ChuyenTau (maChuyenTau, ngayKhoiHanh, gioKhoiHanh, ngayDuKien, gioDuKien, maTau, maTuyen) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, chuyenTau.getMaChuyenTau());
            pstmt.setDate(2, java.sql.Date.valueOf(chuyenTau.getNgayKhoiHanh()));
            pstmt.setTime(3, java.sql.Time.valueOf(chuyenTau.getGioKhoiHanh()));
            pstmt.setDate(4, java.sql.Date.valueOf(chuyenTau.getNgayDuKien()));
            pstmt.setTime(5, java.sql.Time.valueOf(chuyenTau.getGioDuKien()));
            pstmt.setString(6, chuyenTau.getMaTau());
            pstmt.setString(7, chuyenTau.getMaTuyen());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean capNhatChuyenTau(ChuyenTau chuyenTau) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = ConnectDB.getConnection();

            String sql = 
                "UPDATE ChuyenTau SET ngayKhoiHanh = ?, gioKhoiHanh = ?, " +
                "ngayDuKien = ?, gioDuKien = ?, maTau = ?, maTuyen = ? WHERE maChuyenTau = ?";

            pstmt = conn.prepareStatement(sql);

            // Gán các tham số
            pstmt.setDate(1, java.sql.Date.valueOf(chuyenTau.getNgayKhoiHanh()));
            pstmt.setTime(2, java.sql.Time.valueOf(chuyenTau.getGioKhoiHanh()));
            pstmt.setDate(3, java.sql.Date.valueOf(chuyenTau.getNgayDuKien()));
            pstmt.setTime(4, java.sql.Time.valueOf(chuyenTau.getGioDuKien()));
            pstmt.setString(5, chuyenTau.getMaTau());
            pstmt.setString(6, chuyenTau.getMaTuyen());
            pstmt.setString(7, chuyenTau.getMaChuyenTau());

            // Thực thi câu lệnh
            int rowsAffected = pstmt.executeUpdate();
            success = rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }
    
    public boolean kiemTraKhoangCachThoiGianChuyenTau(String tenTau,
            LocalDate ngayKhoiHanh, LocalTime gioKhoiHanh, String maChuyenTauHienTai) {
        System.out.println("Kiểm tra thời gian: tenTau = " + tenTau +
                           ", ngayKhoiHanh = " + ngayKhoiHanh + ", gioKhoiHanh = " + gioKhoiHanh);
        String sql = "SELECT ct.maChuyenTau, ct.ngayKhoiHanh, ct.gioKhoiHanh " +
                     "FROM ChuyenTau ct " +
                     "JOIN Tau t ON ct.maTau = t.maTau " +
                     "WHERE t.tenTau = ?";
        if (maChuyenTauHienTai != null && !maChuyenTauHienTai.isEmpty()) {
            sql += " AND ct.maChuyenTau != ?";
        }
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                return false;
            }
            pstmt.setString(1, tenTau);
            if (maChuyenTauHienTai != null && !maChuyenTauHienTai.isEmpty()) {
                pstmt.setString(2, maChuyenTauHienTai);
            }
            ResultSet rs = pstmt.executeQuery();
            LocalDateTime thoiGianMoi = LocalDateTime.of(ngayKhoiHanh, gioKhoiHanh);
            System.out.println("thoiGianMoi = " + thoiGianMoi);
            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                String maChuyenTau = rs.getString("maChuyenTau");
                LocalDate ngayCu = rs.getDate("ngayKhoiHanh") != null ?
                                   rs.getDate("ngayKhoiHanh").toLocalDate() : null;
                LocalTime gioCu = rs.getTime("gioKhoiHanh") != null ?
                                  rs.getTime("gioKhoiHanh").toLocalTime() : null;
                if (ngayCu == null || gioCu == null) {
                    System.err.println("Dữ liệu không hợp lệ: maChuyenTau = " + maChuyenTau);
                    continue;
                }
                LocalDateTime thoiGianCu = LocalDateTime.of(ngayCu, gioCu);
                long khoangCachGiay = Math.abs(Duration.between(thoiGianMoi, thoiGianCu).getSeconds());
                System.out.println("So sánh: maChuyenTau = " + maChuyenTau + ", khoangCach = " + khoangCachGiay);
                if (khoangCachGiay < 1) {
                    System.out.println("Xung đột thời gian: " + maChuyenTau);
                    return false;
                }
            }
            System.out.println("Kết quả: " + (hasResults ? "Không xung đột" : "Không có chuyến tàu"));
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
            return false;
        }
    }
    
    public String taoMaChuyenTauMoi(String tenTau, LocalDate ngayKhoiHanh, LocalTime gioKhoiHanh) {
        // Kiểm tra đầu vào
        if (ngayKhoiHanh == null || gioKhoiHanh == null) {
            throw new IllegalArgumentException("Ngày hoặc giờ khởi hành không được null");
        }

        // Format hour (HH, e.g., "06")
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        String hour = gioKhoiHanh.format(hourFormatter);
        
        // Format minute (mm, e.g., "00")
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");
        String minute = gioKhoiHanh.format(minuteFormatter);
        
        // Format date (yyyyMMdd, e.g., "20250411")
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = ngayKhoiHanh.format(dateFormatter);
        
        // Sử dụng toàn bộ tên tàu, mặc định "UNKNOWN" nếu rỗng
        String tenTauCode = tenTau.isEmpty() ? "UNKNOWN" : tenTau;
        
        // Kết hợp: giờ + phút + ngày + tên tàu
        return hour + minute + date + tenTauCode;
    }
    
}