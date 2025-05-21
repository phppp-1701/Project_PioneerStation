package entity;

import java.util.Objects;

public class KhachHang {
	private String maKhachHang;
	private String tenKhachHang;
	private String CCCD_HoChieu;
	private String soDienThoai;
	private String email;
	private LoaiThanhVien loaiThanhVien;
	private TrangThaiKhachHang trangThaiKhachHang;
	
	
	// Các thuộc tính enum:
	public enum LoaiThanhVien {
	    thanThiet("Thân thiết"), 
	    vip("VIP"),
	    khachVangLai("Khách vãng lai");
	    
	    private String displayName;
	    
	    private LoaiThanhVien(String displayName) {
	        this.displayName = displayName;
	    }
	    
	    @Override
	    public String toString() {
	        return displayName;
	    }
	}
	
	public enum TrangThaiKhachHang {
	    hoatDong("Hoạt động"), 
	    voHieuHoa("Vô hiệu hóa");
	    
	    private String displayName;
	    
	    private TrangThaiKhachHang(String displayName) {
	        this.displayName = displayName;
	    }
	    
	    @Override
	    public String toString() {
	        return displayName;
	    }
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
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

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LoaiThanhVien getLoaiThanhVien() {
		return loaiThanhVien;
	}

	public void setLoaiThanhVien(LoaiThanhVien loaiThanhVien) {
		this.loaiThanhVien = loaiThanhVien;
	}

	public TrangThaiKhachHang getTrangThaiKhachHang() {
		return trangThaiKhachHang;
	}

	public void setTrangThaiKhachHang(TrangThaiKhachHang trangThaiKhachHang) {
		this.trangThaiKhachHang = trangThaiKhachHang;
	}

	public KhachHang(String maKhachHang, String tenKhachHang, String cCCD_HoChieu, String soDienThoai, String email,
			LoaiThanhVien loaiThanhVien, TrangThaiKhachHang trangThaiKhachHang) {
		this.maKhachHang = maKhachHang;
		this.tenKhachHang = tenKhachHang;
		CCCD_HoChieu = cCCD_HoChieu;
		this.soDienThoai = soDienThoai;
		this.email = email;
		this.loaiThanhVien = loaiThanhVien;
		this.trangThaiKhachHang = trangThaiKhachHang;
	}

	public KhachHang() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(maKhachHang);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKhachHang, other.maKhachHang);
	}
	
	
	
}
