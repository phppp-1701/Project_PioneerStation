//package entity;
//
//import java.util.Date;
//import java.util.Objects;
//
//public class ChiTietCaLam {
//	private Date ngay;
//	private String trangThaiCaLam;
//	private String ghiChu;
//
//	private NhanVien maNhanVien;
//	private CaLam maCaLam;
//
//	public enum TrangThaiCaLam {
//		ChuaHoanThanh,
//		ChuaDenGio,
//		DaHoanThanh
//	}
//
//	public ChiTietCaLam(Date ngay, String trangThaiCaLam, String ghiChu, NhanVien maNhanVien, CaLam maCaLam) {
//		this.ngay = ngay;
//		this.trangThaiCaLam = trangThaiCaLam;
//		this.ghiChu = ghiChu;
//		this.maNhanVien = maNhanVien;
//		this.maCaLam = maCaLam;
//	}
//
//	public Date getNgay() {
//		return ngay;
//	}
//
//	public void setNgay(Date ngay) {
//		this.ngay = ngay;
//	}
//
//	public String getTrangThaiCaLam() {
//		return trangThaiCaLam;
//	}
//
//	public void setTrangThaiCaLam(String trangThaiCaLam) {
//		this.trangThaiCaLam = trangThaiCaLam;
//	}
//
//	public String getGhiChu() {
//		return ghiChu;
//	}
//
//	public void setGhiChu(String ghiChu) {
//		this.ghiChu = ghiChu;
//	}
//
//	public NhanVien getMaNhanVien() {
//		return maNhanVien;
//	}
//
//	public void setMaNhanVien(NhanVien maNhanVien) {
//		this.maNhanVien = maNhanVien;
//	}
//
//	public CaLam getMaCaLam() {
//		return maCaLam;
//	}
//
//	public void setMaCaLam(CaLam maCaLam) {
//		this.maCaLam = maCaLam;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(maCaLam, maNhanVien);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null || getClass() != obj.getClass())
//			return false;
//		ChiTietCaLam other = (ChiTietCaLam) obj;
//		return Objects.equals(maCaLam, other.maCaLam) && Objects.equals(maNhanVien, other.maNhanVien);
//	}
//}
