package entity;

import java.util.Objects;

public class Tau {
	private String maTau;
	private String tenTau;
	private TrangThai trangThaiTau;
	private LoaiTau loaiTau;
	
	public enum TrangThai {
        hoatDong,
        baoTri
    }
	private enum LoaiTau {
	    SE,  // Tàu SE
	    TN,  // Tàu TN
	    DP   // Tàu DP
	}

	
	public Tau() {

	}

	public Tau(String maTau, String tenTau, TrangThai trangThaiTau, LoaiTau loaiTau) {
		this.maTau = maTau;
		this.tenTau = tenTau;
		this.trangThaiTau = trangThaiTau;
		this.loaiTau = loaiTau;
	}

	public String getMaTau() {
		return maTau;
	}

	public void setMaTau(String maTau) {
		this.maTau = maTau;
	}

	public String getTenTau() {
		return tenTau;
	}

	public void setTenTau(String tenTau) {
		this.tenTau = tenTau;
	}

	public TrangThai getTrangThaiTau() {
		return trangThaiTau;
	}

	public void setTrangThaiTau(TrangThai trangThaiTau) {
		this.trangThaiTau = trangThaiTau;
	}

	public LoaiTau getLoaiTau() {
		return loaiTau;
	}

	public void setLoaiTau(LoaiTau loaiTau) {
		this.loaiTau = loaiTau;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maTau);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tau other = (Tau) obj;
		return Objects.equals(maTau, other.maTau);
	}
}
