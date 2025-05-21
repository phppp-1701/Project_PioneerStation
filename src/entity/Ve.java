package entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import entity.VeTam.LoaiKhachHang;

public class Ve {
	private String maVe;
	private LocalDate ngayTaoVe;
	private TrangThaiVe trangThaiVe;
	public enum TrangThaiVe {
	    hoatDong,
	    daHuy_Hoan,
	    daDoi
	}
	private String tenKhachHang;
	private String CCCD_HoChieu;
	private LocalDate ngaySinh;
	private LoaiKhachHang loaiKhachHang;
	private BigDecimal giaVe;
	private String maHoaDon;
	
	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	//phương thức tham chiếu
	private String maChoNgoi;
	
	public Ve() {
		
	}

	public Ve(String maVe, LocalDate ngayTaoVe, TrangThaiVe trangThaiVe, String tenKhachHang, String cCCD_HoChieu,
			LocalDate ngaySinh, LoaiKhachHang loaiKhachHang, BigDecimal giaVe, String maHoaDon, String maChoNgoi) {
		super();
		this.maVe = maVe;
		this.ngayTaoVe = ngayTaoVe;
		this.trangThaiVe = trangThaiVe;
		this.tenKhachHang = tenKhachHang;
		CCCD_HoChieu = cCCD_HoChieu;
		this.ngaySinh = ngaySinh;
		this.loaiKhachHang = loaiKhachHang;
		this.giaVe = giaVe;
		this.maHoaDon = maHoaDon;
		this.maChoNgoi = maChoNgoi;
	}





	public String getMaVe() {
		return maVe;
	}

	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}

	public LocalDate getNgayTaoVe() {
		return ngayTaoVe;
	}

	public void setNgayTaoVe(LocalDate ngayTaoVe) {
		this.ngayTaoVe = ngayTaoVe;
	}

	public TrangThaiVe getTrangThaiVe() {
		return trangThaiVe;
	}

	public void setTrangThaiVe(TrangThaiVe trangThaiVe) {
		this.trangThaiVe = trangThaiVe;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public String getCCCD_HoChieu() {
		return CCCD_HoChieu;
	}

	public void setCCCD_HoChieu(String cCCD_HoChieu) {
		CCCD_HoChieu = cCCD_HoChieu;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public LoaiKhachHang getLoaiKhachHang() {
		return loaiKhachHang;
	}

	public void setLoaiKhachHang(LoaiKhachHang loaiKhachHang) {
		this.loaiKhachHang = loaiKhachHang;
	}

	public BigDecimal getGiaVe() {
		return giaVe;
	}

	public void setGiaVe(BigDecimal giaVe) {
		this.giaVe = giaVe;
	}
	
	

	public String getMaChoNgoi() {
		return maChoNgoi;
	}



	public void setMaChoNgoi(String maChoNgoi) {
		this.maChoNgoi = maChoNgoi;
	}



	@Override
	public int hashCode() {
		return Objects.hash(maVe);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ve other = (Ve) obj;
		return Objects.equals(maVe, other.maVe);
	}
}
