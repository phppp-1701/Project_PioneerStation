package entity;

import java.util.Objects;

public class Tau {
	private String maTau;
	private String tenTau;
	private enum LoaiTau {
	    SE,  // Tàu SE
	    TN,  // Tàu TN
	    DP   // Tàu DP
	}
	private LoaiTau loaiTau;
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
	public LoaiTau getLoaiTau() {
		return loaiTau;
	}
	public void setLoaiTau(LoaiTau loaiTau) {
		this.loaiTau = loaiTau;
	}
	public Tau(String maTau, String tenTau, LoaiTau loaiTau) {
		super();
		this.maTau = maTau;
		this.tenTau = tenTau;
		this.loaiTau = loaiTau;
	}
	public Tau() {

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
