package entity;

import java.util.Objects;

public class Ga {
	private String maGa;
	private String tenGa;
	private String diaChi;
	
	public String getMaGa() {
		return maGa;
	}
	public void setMaGa(String maGa) {
		this.maGa = maGa;
	}
	public String getTenGa() {
		return tenGa;
	}
	public void setTenGa(String tenGa) {
		this.tenGa = tenGa;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	
	public Ga() {
	}
	
	public Ga(String maGa, String tenGa, String diaChi) {
		super();
		this.maGa = maGa;
		this.tenGa = tenGa;
		this.diaChi = diaChi;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maGa);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ga other = (Ga) obj;
		return Objects.equals(maGa, other.maGa);
	}
	
	
	
	
}
