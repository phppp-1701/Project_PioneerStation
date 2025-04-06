package entity;

import java.time.LocalTime;
import java.util.Objects;

public class CaLam {
	private String maCaLam;
	private LocalTime gioBatDau;
	private LocalTime gioKetThuc;
	private NgayTrongTuan ngay;
	
	//Thuộc tính enum:
	public enum NgayTrongTuan {
        thuHai, thuBa, thuTu, thuNam, thuSau, thuBay, chuNhat
    }

	public String getMaCaLam() {
		return maCaLam;
	}

	public void setMaCaLam(String maCaLam) {
		this.maCaLam = maCaLam;
	}

	public LocalTime getGioBatDau() {
		return gioBatDau;
	}

	public void setGioBatDau(LocalTime gioBatDau) {
		this.gioBatDau = gioBatDau;
	}

	public LocalTime getGioKetThuc() {
		return gioKetThuc;
	}

	public void setGioKetThuc(LocalTime gioKetThuc) {
		this.gioKetThuc = gioKetThuc;
	}

	public NgayTrongTuan getNgay() {
		return ngay;
	}

	public void setNgay(NgayTrongTuan ngay) {
		this.ngay = ngay;
	}

	public CaLam(String maCaLam, LocalTime gioBatDau, LocalTime gioKetThuc, NgayTrongTuan ngay) {
		this.maCaLam = maCaLam;
		this.gioBatDau = gioBatDau;
		this.gioKetThuc = gioKetThuc;
		this.ngay = ngay;
	}

	public CaLam() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(maCaLam);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaLam other = (CaLam) obj;
		return Objects.equals(maCaLam, other.maCaLam);
	}
	
	
}
