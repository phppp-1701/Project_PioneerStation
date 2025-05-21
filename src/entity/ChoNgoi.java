package entity;

import java.math.BigDecimal;
import java.util.Objects;

public class ChoNgoi {
    private String maChoNgoi;
    private String tenChoNgoi; // Đổi tên thành tenChoNgoi để khớp với bảng
    private TrangThaiChoNgoi trangThai;
    private String maToa;      // Thêm thuộc tính maToa
    private String maChuyenTau;
    private BigDecimal giaCho; // Thêm thuộc tính giaCho

    // Constructor mặc định
    public ChoNgoi() {
    }

    // Enum cho trạng thái chỗ ngồi
    public enum TrangThaiChoNgoi {
        chuaDat,   // Chỗ ngồi chưa được đặt
        dangDat,   // Chỗ ngồi đang trong quá trình đặt
        daDat      // Chỗ ngồi đã được đặt chính thức
    }
    
    

    public ChoNgoi(String maChoNgoi, String tenChoNgoi, TrangThaiChoNgoi trangThai, String maToa, String maChuyenTau) {
		super();
		this.maChoNgoi = maChoNgoi;
		this.tenChoNgoi = tenChoNgoi;
		this.trangThai = trangThai;
		this.maToa = maToa;
		this.maChuyenTau = maChuyenTau;
	}

	// Constructor đầy đủ
    public ChoNgoi(String maChoNgoi, String tenChoNgoi, TrangThaiChoNgoi trangThai, 
                   String maToa, String maChuyenTau, BigDecimal giaCho) {
        setMaChoNgoi(maChoNgoi); // Sử dụng setter để kiểm tra hợp lệ
        setTenChoNgoi(tenChoNgoi);
        setTrangThai(trangThai);
        setMaToa(maToa);
        setMaChuyenTau(maChuyenTau);
        setGiaCho(giaCho);
    }

    // Getter/Setter cho maChoNgoi
    public String getMaChoNgoi() {
        return maChoNgoi;
    }

    public void setMaChoNgoi(String maChoNgoi) {
        if (maChoNgoi == null || maChoNgoi.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã chỗ ngồi không được để trống");
        }
        this.maChoNgoi = maChoNgoi;
    }

    // Getter/Setter cho tenChoNgoi
    public String getTenChoNgoi() {
        return tenChoNgoi;
    }

    public void setTenChoNgoi(String tenChoNgoi) {
        if (tenChoNgoi == null || tenChoNgoi.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên chỗ ngồi không được để trống");
        }
        this.tenChoNgoi = tenChoNgoi;
    }

    // Getter/Setter cho trangThai
    public TrangThaiChoNgoi getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiChoNgoi trangThai) {
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái chỗ ngồi không được để trống");
        }
        this.trangThai = trangThai;
    }

    // Getter/Setter cho maToa
    public String getMaToa() {
        return maToa;
    }

    public void setMaToa(String maToa) {
        if (maToa == null || maToa.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã toa không được để trống");
        }
        this.maToa = maToa;
    }

    // Getter/Setter cho maChuyenTau
    public String getMaChuyenTau() {
        return maChuyenTau;
    }

    public void setMaChuyenTau(String maChuyenTau) {
        if (maChuyenTau == null || maChuyenTau.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã chuyến tàu không được để trống");
        }
        this.maChuyenTau = maChuyenTau;
    }

    // Getter/Setter cho giaCho
    public BigDecimal getGiaCho() {
        return giaCho;
    }

    public void setGiaCho(BigDecimal giaCho) {
        if (giaCho == null || giaCho.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Giá chỗ không được để trống và phải lớn hơn hoặc bằng 0");
        }
        this.giaCho = giaCho;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maChoNgoi);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ChoNgoi other = (ChoNgoi) obj;
        return Objects.equals(maChoNgoi, other.maChoNgoi);
    }

    @Override
    public String toString() {
        return "ChoNgoi{" +
               "maChoNgoi='" + maChoNgoi + '\'' +
               ", tenChoNgoi='" + tenChoNgoi + '\'' +
               ", trangThai=" + trangThai +
               ", maToa='" + maToa + '\'' +
               ", maChuyenTau='" + maChuyenTau + '\'' +
               ", giaCho=" + giaCho +
               '}';
    }
}