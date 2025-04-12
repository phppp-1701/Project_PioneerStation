package entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VeTam {
    // Enum cho loại thành viên
    public enum LoaiKhachHang {
        treEm, nguoiLon, nguoiCaoTuoi, sinhVien
    }

    // Thuộc tính
    private String maChoNgoi;
    private BigDecimal giaTien;
    private LoaiKhachHang loaiKhachHang;

    // Constructor
    public VeTam(String maChoNgoi, BigDecimal giaCho, LoaiKhachHang loaiKhachHang) {
        this.maChoNgoi = maChoNgoi;
        this.loaiKhachHang = loaiKhachHang;
        this.giaTien = tinhGiaTien(giaCho, loaiKhachHang);
    }

    // Phương thức tính giá tiền dựa trên loại thành viên
    private BigDecimal tinhGiaTien(BigDecimal giaCho, LoaiKhachHang loaiKhachHang) {
        if (giaCho == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal heSoGiamGia;
        switch (loaiKhachHang) {
            case treEm:
                heSoGiamGia = new BigDecimal("0.80"); // Giảm 20%
                break;
            case nguoiCaoTuoi:
                heSoGiamGia = new BigDecimal("0.85"); // Giảm 15%
                break;
            case sinhVien:
                heSoGiamGia = new BigDecimal("0.90"); // Giảm 10%
                break;
            case nguoiLon:
            default:
                heSoGiamGia = BigDecimal.ONE; // Không giảm (100%)
                break;
        }

        // Tính giá tiền = giá chỗ * hệ số giảm giá, làm tròn đến 2 chữ số thập phân
        return giaCho.multiply(heSoGiamGia).setScale(2, RoundingMode.HALF_UP);
    }

    // Getter và Setter
    public String getMaChoNgoi() {
        return maChoNgoi;
    }

    public void setMaChoNgoi(String maChoNgoi) {
        this.maChoNgoi = maChoNgoi;
    }

    public BigDecimal getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }

    public LoaiKhachHang getLoaiKhachHang() {
        return loaiKhachHang;
    }

    public void setLoaiThanhVien(LoaiKhachHang loaiKhachHang) {
        this.loaiKhachHang = loaiKhachHang;
        // Cập nhật lại giá tiền nếu loại thành viên thay đổi
        this.giaTien = tinhGiaTien(this.giaTien, loaiKhachHang);
    }
}
