package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ChuyenTau {
	private String maChuyenTau;
	private LocalDate ngayKhoiHanh;
	private LocalTime gioKhoiHanh;
	private double giaTien;
	private TrangThaiChuyenTau trangThaiChuyenTau;
	private TuyenTau maTuyenTau;
    private Tau maTau;
    private LichTrinh maLichTrinh;
	
	public enum TrangThaiChuyenTau{
		hoatDong, khongHoatDong
	}

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

	public double getGiaTien() {
		return giaTien;
	}

	public void setGiaTien(double giaTien) {
		this.giaTien = giaTien;
	}

	public TrangThaiChuyenTau getTrangThaiChuyenTau() {
		return trangThaiChuyenTau;
	}

	public void setTrangThaiChuyenTau(TrangThaiChuyenTau trangThaiChuyenTau) {
		this.trangThaiChuyenTau = trangThaiChuyenTau;
	}

	public TuyenTau getMaTuyenTau() {
		return maTuyenTau;
	}

	public void setMaTuyenTau(TuyenTau maTuyenTau) {
		this.maTuyenTau = maTuyenTau;
	}

	public Tau getMaTau() {
		return maTau;
	}

	public void setMaTau(Tau maTau) {
		this.maTau = maTau;
	}

	public LichTrinh getMaLichTrinh() {
		return maLichTrinh;
	}

	public void setMaLichTrinh(LichTrinh maLichTrinh) {
		this.maLichTrinh = maLichTrinh;
	}

	public ChuyenTau(String maChuyenTau, LocalDate ngayKhoiHanh, LocalTime gioKhoiHanh, double giaTien,
			TrangThaiChuyenTau trangThaiChuyenTau, TuyenTau maTuyenTau, Tau maTau, LichTrinh maLichTrinh) {
		this.maChuyenTau = maChuyenTau;
		this.ngayKhoiHanh = ngayKhoiHanh;
		this.gioKhoiHanh = gioKhoiHanh;
		this.giaTien = giaTien;
		this.trangThaiChuyenTau = trangThaiChuyenTau;
		this.maTuyenTau = maTuyenTau;
		this.maTau = maTau;
		this.maLichTrinh = maLichTrinh;
	}

	public ChuyenTau() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(maChuyenTau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChuyenTau other = (ChuyenTau) obj;
		return Objects.equals(maChuyenTau, other.maChuyenTau);
	}
	
	
	
}
