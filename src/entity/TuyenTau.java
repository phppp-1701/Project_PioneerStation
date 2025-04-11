package entity;

import java.util.Objects;

public class TuyenTau {
    private String maTuyen;
    private Ga gaDi;
    private Ga gaDen;
    private double khoangCach;

    public TuyenTau() {
    }

    public TuyenTau(String maTuyen, Ga gaDi, Ga gaDen, double khoangCach) {
        this.maTuyen = maTuyen;
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.khoangCach = khoangCach;
    }

    public String getMaTuyen() {
        return maTuyen;
    }

    public void setMaTuyen(String maTuyen) {
        this.maTuyen = maTuyen;
    }

    public Ga getGaDi() {
        return gaDi;
    }

    public void setGaDi(Ga gaDi) {
        this.gaDi = gaDi;
    }

    public Ga getGaDen() {
        return gaDen;
    }

    public void setGaDen(Ga gaDen) {
        this.gaDen = gaDen;
    }

    public double getKhoangCach() {
        return khoangCach;
    }

    public void setKhoangCach(double khoangCach) {
        if (khoangCach <= 0) {
            throw new IllegalArgumentException("Khoảng cách phải lớn hơn 0");
        }
        this.khoangCach = khoangCach;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maTuyen);
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
        return Objects.equals(maTuyen, other.maTuyen);
    }
}