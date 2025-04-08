package entity;

import java.util.Objects;

public class TaiKhoan {
    private String tenTaiKhoan;
    private String matKhau;
    private NhanVien maNhanVien;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenTaiKhoan, String matKhau, NhanVien maNhanVien) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

	public NhanVien getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(NhanVien maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tenTaiKhoan);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		return Objects.equals(tenTaiKhoan, other.tenTaiKhoan);
	}
    
    
}
