//package entity;
//
//import java.util.Date;
//import java.util.Objects;
//
//public class Ve {
//    private String maVe;
//    private Date ngayTaoVe;
//    private String trangThaiVe;
//    private String tenKhachHang;
//    private String CCCD_HoChieu;
//    private String loaiKhachHang;
//    private double giaVe;
//
//    private HoaDon maHoaDon;
//    private TuyenTau maTuyenTau;
//    private Tau maTau;
//    private LichTrinh maLichTrinh;
//
//    public Ve(String maVe, Date ngayTaoVe, String trangThaiVe, String tenKhachHang, String CCCD_HoChieu,
//              String loaiKhachHang, double giaVe, HoaDon maHoaDon, TuyenTau maTuyenTau, Tau maTau, LichTrinh maLichTrinh) {
//        this.maVe = maVe;
//        this.ngayTaoVe = ngayTaoVe;
//        this.trangThaiVe = trangThaiVe;
//        this.tenKhachHang = tenKhachHang;
//        this.CCCD_HoChieu = CCCD_HoChieu;
//        this.loaiKhachHang = loaiKhachHang;
//        this.giaVe = giaVe;
//        this.maHoaDon = maHoaDon;
//        this.maTuyenTau = maTuyenTau;
//        this.maTau = maTau;
//        this.maLichTrinh = maLichTrinh;
//    }
//
//    public String getMaVe() {
//        return maVe;
//    }
//
//    public void setMaVe(String maVe) {
//        this.maVe = maVe;
//    }
//
//    public Date getNgayTaoVe() {
//        return ngayTaoVe;
//    }
//
//    public void setNgayTaoVe(Date ngayTaoVe) {
//        this.ngayTaoVe = ngayTaoVe;
//    }
//
//    public String getTrangThaiVe() {
//        return trangThaiVe;
//    }
//
//    public void setTrangThaiVe(String trangThaiVe) {
//        this.trangThaiVe = trangThaiVe;
//    }
//
//    public String getTenKhachHang() {
//        return tenKhachHang;
//    }
//
//    public void setTenKhachHang(String tenKhachHang) {
//        this.tenKhachHang = tenKhachHang;
//    }
//
//    public String getCCCD_HoChieu() {
//        return CCCD_HoChieu;
//    }
//
//    public void setCCCD_HoChieu(String CCCD_HoChieu) {
//        this.CCCD_HoChieu = CCCD_HoChieu;
//    }
//
//    public String getLoaiKhachHang() {
//        return loaiKhachHang;
//    }
//
//    public void setLoaiKhachHang(String loaiKhachHang) {
//        this.loaiKhachHang = loaiKhachHang;
//    }
//
//    public double getGiaVe() {
//        return giaVe;
//    }
//
//    public void setGiaVe(double giaVe) {
//        this.giaVe = giaVe;
//    }
//
//    public HoaDon getMaHoaDon() {
//        return maHoaDon;
//    }
//
//    public void setMaHoaDon(HoaDon maHoaDon) {
//        this.maHoaDon = maHoaDon;
//    }
//
//    public TuyenTau getMaTuyenTau() {
//        return maTuyenTau;
//    }
//
//    public void setMaTuyenTau(TuyenTau maTuyenTau) {
//        this.maTuyenTau = maTuyenTau;
//    }
//
//    public Tau getMaTau() {
//        return maTau;
//    }
//
//    public void setMaTau(Tau maTau) {
//        this.maTau = maTau;
//    }
//
//    public ChuyenTau getMaLichTrinh() {
//        return maLichTrinh;
//    }
//
//    public void setMaLichTrinh(ChuyenTau maLichTrinh) {
//        this.maLichTrinh = maLichTrinh;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(maVe);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null || getClass() != obj.getClass())
//            return false;
//        Ve other = (Ve) obj;
//        return Objects.equals(maVe, other.maVe);
//    }
//}
