package entity;

import java.util.Objects;

public class Tau {
    private String maTau;
    private String tenTau;
    private TrangThaiTau trangThaiTau;
    private LoaiTau loaiTau;

    public enum TrangThaiTau {
        hoatDong("Hoạt động"),
        baoTri("Bảo trì");

        private String displayName;

        private TrangThaiTau(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public enum LoaiTau {
        SE,  // Tàu SE
        TN
    }

    public Tau() {
    }

    public Tau(String maTau, String tenTau, TrangThaiTau trangThaiTau, LoaiTau loaiTau) {
        this.maTau = maTau;
        this.tenTau = tenTau;
        this.trangThaiTau = trangThaiTau;
        this.loaiTau = loaiTau;
    }

    public String getMaTau() {
        return maTau;
    }

    public void setMaTau(String maTau) {
        this.maTau = maTau;
    }

    public String getTenTau() {
        return tenTau;
    }

    public void setTenTau(String tenTau) {
        this.tenTau = tenTau;
    }

    public TrangThaiTau getTrangThaiTau() {
        return trangThaiTau;
    }

    public void setTrangThaiTau(TrangThaiTau trangThaiTau) {
        this.trangThaiTau = trangThaiTau;
    }

    public LoaiTau getLoaiTau() {
        return loaiTau;
    }

    public void setLoaiTau(LoaiTau loaiTau) {
        this.loaiTau = loaiTau;
    }

    // Phương thức mới: Lấy tên ký hiệu tàu
    public String getKyHieuTau() {
        if (maTau == null || maTau.length() < 12) {
            return ""; // Trả về chuỗi rỗng nếu maTau không hợp lệ
        }

        // Bỏ 4 ký tự đầu, lấy 2 ký tự kế tiếp
        String kyHieuPhanDau = maTau.substring(4, 6);

        // Lấy 6 ký tự cuối, rồi lấy 2 ký tự cuối của phần đó
        String sixLastChars = maTau.substring(maTau.length() - 6);
        String kyHieuPhanCuoi = sixLastChars.substring(4, 6);

        // Ghép lại và trả về
        return kyHieuPhanDau + kyHieuPhanCuoi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maTau);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tau other = (Tau) obj;
        return Objects.equals(maTau, other.maTau);
    }
}