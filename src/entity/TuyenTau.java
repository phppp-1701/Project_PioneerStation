package entity;

import java.util.Objects;

public class TuyenTau {
	private String maTuyenTau;
	private String tenTuyenTau;
	private double khoangCach;
	private Ga maGaDi;
	private Ga maGaDen;
	
	public String getMaTuyenTau() {
		return maTuyenTau;
	}
	public void setMaTuyenTau(String maTuyenTau) {
		this.maTuyenTau = maTuyenTau;
	}
	public String getTenTuyenTau() {
		return tenTuyenTau;
	}
	public void setTenTuyenTau(String tenTuyenTau) {
		this.tenTuyenTau = tenTuyenTau;
	}
	public double getKhoangCach() {
		return khoangCach;
	}
	public void setKhoangCach(double khoangCach) {
		this.khoangCach = khoangCach;
	}
	public Ga getMaGaDi() {
		return maGaDi;
	}
	public void setMaGaDi(Ga maGaDi) {
		this.maGaDi = maGaDi;
	}
	public Ga getMaGaDen() {
		return maGaDen;
	}
	public void setMaGaDen(Ga maGaDen) {
		this.maGaDen = maGaDen;
	}
	
	public TuyenTau() {
	}
	public TuyenTau(String maTuyenTau, String tenTuyenTau, double khoangCach, Ga maGaDi, Ga maGaDen) {
		this.maTuyenTau = maTuyenTau;
		this.tenTuyenTau = tenTuyenTau;
		this.khoangCach = khoangCach;
		this.maGaDi = maGaDi;
		this.maGaDen = maGaDen;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maTuyenTau);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TuyenTau other = (TuyenTau) obj;
		return Objects.equals(maTuyenTau, other.maTuyenTau);
	}
	
	
	
}
