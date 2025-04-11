package entity;

import java.util.Objects;

public class Toa {
    // Các thuộc tính
    private String maToa;
    private String tenToa;
    private LoaiToa loaiToa;
    private TrangThaiToa trangThai;
    private String maTau;

    // Enum cho loaiToa
    public enum LoaiToa {
        ngoiMemDieuHoa("Ngồi mềm điều hòa"),
        giuongNamDieuHoa("Giường nằm điều hòa"),
        gheCungDieuHoa("Ghế cứng điều hòa");

        private final String displayName;

        LoaiToa(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Enum cho trangThai
    public enum TrangThaiToa {
        hoatDong("Hoạt động"),
        voHieuHoa("Vô hiệu hóa");

        private final String displayName;

        TrangThaiToa(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructor mặc định
    public Toa() {
    }

    // Constructor đầy đủ tham số
    public Toa(String maToa, String tenToa, LoaiToa loaiToa, TrangThaiToa trangThai, String maTau) {
        this.maToa = maToa;
        this.tenToa = tenToa;
        this.loaiToa = loaiToa;
        this.trangThai = trangThai;
        this.maTau = maTau;
    }

    // Getter và Setter
    public String getMaToa() {
        return maToa;
    }

    public void setMaToa(String maToa) {
        this.maToa = maToa;
    }

    public String getTenToa() {
        return tenToa;
    }

    public void setTenToa(String tenToa) {
        this.tenToa = tenToa;
    }

    public LoaiToa getLoaiToa() {
        return loaiToa;
    }

    public void setLoaiToa(LoaiToa loaiToa) {
        this.loaiToa = loaiToa;
    }

    public TrangThaiToa getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiToa trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaTau() {
        return maTau;
    }

    public void setMaTau(String maTau) {
        this.maTau = maTau;
    }

    // Phương thức toString để hiển thị thông tin
    @Override
    public String toString() {
        return "Toa{" +
                "maToa='" + maToa + '\'' +
                ", tenToa='" + tenToa + '\'' +
                ", loaiToa=" + (loaiToa != null ? loaiToa.getDisplayName() : "null") +
                ", trangThai=" + (trangThai != null ? trangThai.getDisplayName() : "null") +
                ", maTau='" + maTau + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(maToa);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Toa other = (Toa) obj;
        return Objects.equals(maToa, other.maToa);
    }
}