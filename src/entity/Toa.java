package entity;

import java.util.Objects;

public class Toa {
    private String maToa;
    private String tenToa;
    private LoaiToa loaiToa;

    public Toa() {
    }

    public Toa(String maToa, String tenToa, LoaiToa loaiToa) {
        this.maToa = maToa;
        this.tenToa = tenToa;
        this.loaiToa = loaiToa;
    }

    public String getMaToa() {
        return maToa;
    }

    public void setMaToa(String maToa) {
        this.maToa = maToa;
    }

    public String getTenToa() {
        return tenToa;
    }

    public void setTenToa(String tenToa) {
        this.tenToa = tenToa;
    }

    public LoaiToa getLoaiToa() {
        return loaiToa;
    }

    public void setLoaiToa(LoaiToa loaiToa) {
        this.loaiToa = loaiToa;
    }
    public enum LoaiToa {
        ngoiMemDieuHoa,
        giuongNamDieuHoa,
        gheCungDieuHoa
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toa toa = (Toa) o;
        return Objects.equals(maToa, toa.maToa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maToa);
    }
}
