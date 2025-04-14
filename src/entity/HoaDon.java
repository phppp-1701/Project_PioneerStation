package entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class HoaDon {
	private String maHoaDon;
	private LocalDate ngayTaoHoaDon;
	private PhuongThucThanhToan phuongThucThanhToan;
	public enum PhuongThucThanhToan {
	    tienMat,
	    atm,
	    internetBanking;

	    @Override
	    public String toString() {
	        switch (this) {
	            case tienMat: return "Tiền mặt";
	            case atm: return "ATM";
	            case internetBanking: return "Internet Banking";
	            default: return name();
	        }
	    }
	}
	private double phanTramGiamGia;
	private BigDecimal tienKhachDua;
	private BigDecimal thanhTien;
	private BigDecimal tienTraLai;
	private String maKhuyenMai;
	private String maNhanVien;
	
	public HoaDon() {
		
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public LocalDate getNgayTaoHoaDon() {
		return ngayTaoHoaDon;
	}

	public void setNgayTaoHoaDon(LocalDate ngayTaoHoaDon) {
		this.ngayTaoHoaDon = ngayTaoHoaDon;
	}

	public PhuongThucThanhToan getPhuongThucThanhToan() {
		return phuongThucThanhToan;
	}

	public void setPhuongThucThanhToan(PhuongThucThanhToan phuongThucThanhToan) {
		this.phuongThucThanhToan = phuongThucThanhToan;
	}

	public double getPhanTramGiamGia() {
		return phanTramGiamGia;
	}

	public void setPhanTramGiamGia(double phanTramGiamGia) {
		this.phanTramGiamGia = phanTramGiamGia;
	}

	public BigDecimal getTienKhachDua() {
		return tienKhachDua;
	}

	public void setTienKhachDua(BigDecimal tienKhachDua) {
		this.tienKhachDua = tienKhachDua;
	}

	public BigDecimal getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(BigDecimal thanhTien) {
		this.thanhTien = thanhTien;
	}

	public BigDecimal getTienTraLai() {
		return tienTraLai;
	}

	public void setTienTraLai(BigDecimal tienTraLai) {
		this.tienTraLai = tienTraLai;
	}

	public HoaDon(String maHoaDon, LocalDate ngayTaoHoaDon, PhuongThucThanhToan phuongThucThanhToan,
			double phanTramGiamGia, BigDecimal tienKhachDua, BigDecimal thanhTien, BigDecimal tienTraLai,
			String maKhuyenMai, String maNhanVien) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayTaoHoaDon = ngayTaoHoaDon;
		this.phuongThucThanhToan = phuongThucThanhToan;
		this.phanTramGiamGia = phanTramGiamGia;
		this.tienKhachDua = tienKhachDua;
		this.thanhTien = thanhTien;
		this.tienTraLai = tienTraLai;
		this.maKhuyenMai = maKhuyenMai;
		this.maNhanVien = maNhanVien;
	}
	
	public String getMaKhuyenMai() {
		return maKhuyenMai;
	}

	public void setMaKhuyenMai(String maKhuyenMai) {
		this.maKhuyenMai = maKhuyenMai;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maHoaDon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHoaDon, other.maHoaDon);
	}
	
}
