package entity;

import java.util.Objects;

public class ChoNgoi {
    private String maChoNgoi;
    private String tenCho;
    private TrangThaiChoNgoi trangThai;
    private String maChuyenTau;  // Thay vì dùng đối tượng ChuyenTau

    public ChoNgoi() {
    }

    public enum TrangThaiChoNgoi {
        chuaDat,   // Chỗ ngồi chưa được đặt
        dangDat,   // Chỗ ngồi đang trong quá trình đặt
        daDat      // Chỗ ngồi đã được đặt chính thức
    }

    // Constructor sửa lại theo maChuyenTau
    public ChoNgoi(String maChoNgoi, String tenCho, TrangThaiChoNgoi trangThai, String maChuyenTau) {
        this.maChoNgoi = maChoNgoi;
        this.tenCho = tenCho;
        this.trangThai = trangThai;
        this.maChuyenTau = maChuyenTau;
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

    public String getMaChoNgoi() {
		return maChoNgoi;
	}

	public void setMaChoNgoi(String maChoNgoi) {
		this.maChoNgoi = maChoNgoi;
	}

	public String getTenCho() {
		return tenCho;
	}

	public void setTenCho(String tenCho) {
		this.tenCho = tenCho;
	}

	public TrangThaiChoNgoi getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(TrangThaiChoNgoi trangThai) {
		this.trangThai = trangThai;
	}

	@Override
    public int hashCode() {
        return Objects.hash(maChoNgoi);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChoNgoi other = (ChoNgoi) obj;
        return Objects.equals(maChoNgoi, other.maChoNgoi);
    }
}