package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import connectDB.ConnectDB;

public class ChuyenTau {
    // Thuộc tính
    private String maChuyenTau;
    private LocalDate ngayKhoiHanh;
    private LocalTime gioKhoiHanh;
    private LocalDate ngayDuKien;
    private LocalTime gioDuKien;
    private String maTau;
    private String maTuyen;

    // Constructor mặc định
    public ChuyenTau() {
    }

    // Constructor đầy đủ tham số
    public ChuyenTau(String maChuyenTau, LocalDate ngayKhoiHanh, LocalTime gioKhoiHanh,
                     LocalDate ngayDuKien, LocalTime gioDuKien, String maTau, String maTuyen) {
        this.maChuyenTau = maChuyenTau;
        this.ngayKhoiHanh = ngayKhoiHanh;
        this.gioKhoiHanh = gioKhoiHanh;
        this.ngayDuKien = ngayDuKien;
        this.gioDuKien = gioDuKien;
        this.maTau = maTau;
        this.maTuyen = maTuyen;
    }

    // Constructor nhận mã tàu, mã tuyến, ngày khởi hành, giờ khởi hành
    public ChuyenTau(String maChuyenTau, String maTau, String maTuyen,
                     LocalDate ngayKhoiHanh, LocalTime gioKhoiHanh) {
        this.maChuyenTau = maChuyenTau;
        this.maTau = maTau;
        this.maTuyen = maTuyen;
        this.ngayKhoiHanh = ngayKhoiHanh;
        this.gioKhoiHanh = gioKhoiHanh;
        // Tính ngayDuKien và gioDuKien
        calculateNgayGioDuKien();
    }

    // Getter và Setter
    public String getMaChuyenTau() {
        return maChuyenTau;
    }

    public void setMaChuyenTau(String maChuyenTau) {
        this.maChuyenTau = maChuyenTau;
    }

    public LocalDate getNgayKhoiHanh() {
        return ngayKhoiHanh;
    }

    public void setNgayKhoiHanh(LocalDate ngayKhoiHanh) {
        this.ngayKhoiHanh = ngayKhoiHanh;
    }

    public LocalTime getGioKhoiHanh() {
        return gioKhoiHanh;
    }

    public void setGioKhoiHanh(LocalTime gioKhoiHanh) {
        this.gioKhoiHanh = gioKhoiHanh;
    }

    public LocalDate getNgayDuKien() {
        return ngayDuKien;
    }

    public void setNgayDuKien(LocalDate ngayDuKien) {
        this.ngayDuKien = ngayDuKien;
    }

    public LocalTime getGioDuKien() {
        return gioDuKien;
    }

    public void setGioDuKien(LocalTime gioDuKien) {
        this.gioDuKien = gioDuKien;
    }

    public String getMaTau() {
        return maTau;
    }

    public void setMaTau(String maTau) {
        this.maTau = maTau;
    }

    public String getMaTuyen() {
        return maTuyen;
    }

    public void setMaTuyen(String maTuyen) {
        this.maTuyen = maTuyen;
    }

    // Phương thức tính ngayDuKien và gioDuKien
    private void calculateNgayGioDuKien() {
        Connection conn = null;
        PreparedStatement pstmtTau = null;
        PreparedStatement pstmtTuyen = null;
        ResultSet rsTau = null;
        ResultSet rsTuyen = null;

        try {
            // Lấy kết nối từ ConnectDB
            conn = ConnectDB.getConnection();

            // 1. Lấy loại tàu (SE hay TN) từ bảng Tau
            String sqlTau = "SELECT loaiTau FROM Tau WHERE maTau = ?";
            pstmtTau = conn.prepareStatement(sqlTau);
            pstmtTau.setString(1, maTau);
            rsTau = pstmtTau.executeQuery();

            String loaiTau = null;
            if (rsTau.next()) {
                loaiTau = rsTau.getString("loaiTau");
            } else {
                throw new SQLException("Không tìm thấy tàu với mã: " + maTau);
            }

            // Xác định tốc độ dựa trên loại tàu
            double tocDo;
            if ("SE".equals(loaiTau)) {
                tocDo = 50.0; // 50 km/h cho tàu SE
            } else if ("TN".equals(loaiTau)) {
                tocDo = 60.0; // 60 km/h cho tàu TN
            } else {
                throw new SQLException("Loại tàu không hợp lệ: " + loaiTau);
            }

            // 2. Lấy khoảng cách từ bảng TuyenTau
            String sqlTuyen = "SELECT khoangCach FROM TuyenTau WHERE maTuyen = ?";
            pstmtTuyen = conn.prepareStatement(sqlTuyen);
            pstmtTuyen.setString(1, maTuyen);
            rsTuyen = pstmtTuyen.executeQuery();

            double khoangCach = 0.0;
            if (rsTuyen.next()) {
                khoangCach = rsTuyen.getDouble("khoangCach");
            } else {
                throw new SQLException("Không tìm thấy tuyến tàu với mã: " + maTuyen);
            }

            // 3. Tính thời gian đi (giờ)
            double thoiGianDi = khoangCach / tocDo; // giờ

            // 4. Chuyển thời gian đi thành phút để dễ tính toán
            long thoiGianDiPhut = (long) (thoiGianDi * 60); // phút

            // 5. Tính ngày và giờ dự kiến
            // Chuyển thời gian khởi hành thành phút trong ngày
            long phutKhoiHanhTrongNgay = gioKhoiHanh.getHour() * 60L + gioKhoiHanh.getMinute();
            long tongPhut = phutKhoiHanhTrongNgay + thoiGianDiPhut;

            // Tính số ngày và phút còn lại
            long soNgayThem = tongPhut / (24 * 60); // Số ngày
            long phutConLai = tongPhut % (24 * 60); // Số phút còn lại trong ngày

            // Tính ngày dự kiến
            this.ngayDuKien = ngayKhoiHanh.plusDays(soNgayThem);

            // Tính giờ dự kiến
            int gioDuKien = (int) (phutConLai / 60);
            int phutDuKien = (int) (phutConLai % 60);
            this.gioDuKien = LocalTime.of(gioDuKien, phutDuKien);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tính ngày và giờ dự kiến: " + e.getMessage());
        } finally {
            // Đóng tài nguyên
            try {
                if (rsTau != null) rsTau.close();
                if (rsTuyen != null) rsTuyen.close();
                if (pstmtTau != null) pstmtTau.close();
                if (pstmtTuyen != null) pstmtTuyen.close();
                // Không đóng Connection vì ConnectDB quản lý vòng đời của nó
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Override toString để dễ kiểm tra
    @Override
    public String toString() {
        return "ChuyenTau{" +
               "maChuyenTau='" + maChuyenTau + '\'' +
               ", ngayKhoiHanh=" + ngayKhoiHanh +
               ", gioKhoiHanh=" + gioKhoiHanh +
               ", ngayDuKien=" + ngayDuKien +
               ", gioDuKien=" + gioDuKien +
               ", maTau='" + maTau + '\'' +
               ", maTuyen='" + maTuyen + '\'' +
               '}';
    }
    
    
}