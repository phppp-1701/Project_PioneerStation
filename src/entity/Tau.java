package entity;

import java.util.Objects;

public class Tau {
	private String maTau;
	private String tenTau;
	private TrangThai trangThaiTau;
	
	public enum TrangThai {
        hoatDong,
        baoTri
    }
	
	public Tau() {
		
	}
	
	public Tau(String maTau, String tenTau, TrangThai trangThaiTau) {
		super();
		this.maTau = maTau;
		this.tenTau = tenTau;
		this.trangThaiTau = trangThaiTau;
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
