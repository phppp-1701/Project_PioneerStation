package entity;

import java.time.LocalTime;
import java.util.Objects;
import entity.CaLam.NgayTrongTuan;

public class LichTrinh {
	private String maLichTrinh;
	private LocalTime gioKhoiHanh;
	private NgayTrongTuan ngayKhoiHanh;
	
	//Thuộc tính enum:
	public enum NgayTrongTuan {
        thuHai, thuBa, thuTu, thuNam, thuSau, thuBay, chuNhat
    }

	public LichTrinh(String maLichTrinh, LocalTime gioKhoiHanh, NgayTrongTuan ngayKhoiHanh) {
		super();
		this.maLichTrinh = maLichTrinh;
		this.gioKhoiHanh = gioKhoiHanh;
		this.ngayKhoiHanh = ngayKhoiHanh;
	}
	
	public String getMaLichTrinh() {
		return maLichTrinh;
	}

	public void setMaLichTrinh(String maLichTrinh) {
		this.maLichTrinh = maLichTrinh;
	}

	public LocalTime getGioKhoiHanh() {
		return gioKhoiHanh;
	}

	public void setGioKhoiHanh(LocalTime gioKhoiHanh) {
		this.gioKhoiHanh = gioKhoiHanh;
	}

	public NgayTrongTuan getNgayKhoiHanh() {
		return ngayKhoiHanh;
	}

	public void setNgayKhoiHanh(NgayTrongTuan ngayKhoiHanh) {
		this.ngayKhoiHanh = ngayKhoiHanh;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maLichTrinh);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LichTrinh other = (LichTrinh) obj;
		return Objects.equals(maLichTrinh, other.maLichTrinh);
	}
	
}