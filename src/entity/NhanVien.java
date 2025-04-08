package entity;

import java.time.LocalDate;
import java.util.Objects;

public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private LocalDate ngaySinh;
    private GioiTinh gioiTinh;
    private String cccd_HoChieu;
    private ChucVu chucVu;
    private TrangThaiNhanVien trangThaiNhanVien;
    private String soDienThoai;
    private String email;
    private String linkAnh;


    public NhanVien() {

	}

	public NhanVien(String maNhanVien, String tenNhanVien, LocalDate ngaySinh, GioiTinh gioiTinh, String cccd_HoChieu,
			ChucVu chucVu, TrangThaiNhanVien trangThaiNhanVien, String soDienThoai, String email, String linkAnh) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.cccd_HoChieu = cccd_HoChieu;
		this.chucVu = chucVu;
		this.trangThaiNhanVien = trangThaiNhanVien;
		this.soDienThoai = soDienThoai;
		this.email = email;
		this.linkAnh = linkAnh;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getTenNhanVien() {
		return tenNhanVien;
	}

	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public GioiTinh getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(GioiTinh gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getCccd_HoChieu() {
		return cccd_HoChieu;
	}

	public void setCccd_HoChieu(String cccd_HoChieu) {
		this.cccd_HoChieu = cccd_HoChieu;
	}

	public ChucVu getChucVu() {
		return chucVu;
	}

	public void setChucVu(ChucVu chucVu) {
		this.chucVu = chucVu;
	}

	public TrangThaiNhanVien getTrangThaiNhanVien() {
		return trangThaiNhanVien;
	}

	public void setTrangThaiNhanVien(TrangThaiNhanVien trangThaiNhanVien) {
		this.trangThaiNhanVien = trangThaiNhanVien;
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

	public String getLinkAnh() {
		return linkAnh;
	}

	public void setLinkAnh(String linkAnh) {
		this.linkAnh = linkAnh;
	}




	public enum GioiTinh {
	    nam("Nam"),
	    nu("Nữ");

	    private String displayName;

	    private GioiTinh(String displayName) {
	        this.displayName = displayName;
	    }

	    @Override
	    public String toString() {
	        return displayName;
	    }
	}
	
	public enum ChucVu {
	    quanLy("Quản lý"),
	    banVe("Bán vé");

	    private String displayName;

	    private ChucVu(String displayName) {
	        this.displayName = displayName;
	    }

	    @Override
	    public String toString() {
	        return displayName;
	    }
	}
	
	public enum TrangThaiNhanVien {
	    hoatDong("Hoạt động"),
	    voHieuHoa("Vô hiệu hóa");

	    private String displayName;

	    private TrangThaiNhanVien(String displayName) {
	        this.displayName = displayName;
	    }

	    @Override
	    public String toString() {
	        return displayName;
	    }
	}

    @Override
	public int hashCode() {
		return Objects.hash(maNhanVien);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNhanVien, other.maNhanVien);
	}
	
}