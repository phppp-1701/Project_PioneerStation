package entity;

import java.util.Objects;

public class ChoNgoi {
    private String maCho;
    private String tenCho;
    private TrangThaiCho trangThaiCho;

    public ChoNgoi() {
    }

    public ChoNgoi(String maCho, String tenCho, TrangThaiCho trangThaiCho) {
        this.maCho = maCho;
        this.tenCho = tenCho;
        this.trangThaiCho = trangThaiCho;
    }

    public String getMaCho() {
        return maCho;
    }

    public void setMaCho(String maCho) {
        this.maCho = maCho;
    }

    public String getTenCho() {
        return tenCho;
    }

    public void setTenCho(String tenCho) {
        this.tenCho = tenCho;
    }

    public TrangThaiCho getTrangThaiCho() {
        return trangThaiCho;
    }

    public void setTrangThaiCho(TrangThaiCho trangThaiCho) {
        this.trangThaiCho = trangThaiCho;
    }
    public enum TrangThaiCho {
        daDat,
        conTrong,
        dangChon
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChoNgoi choNgoi = (ChoNgoi) o;
        return Objects.equals(maCho, choNgoi.maCho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maCho);
    }

}
