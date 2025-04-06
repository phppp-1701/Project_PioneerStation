//package entity;
//
//import java.util.Date;
//import java.util.Objects;
//
//public class HoaDon {
//
//    private String maHoaDon;
//    private Date ngayTaoHoaDon;
//    private String phuongThucThanhToan;
//    private float phanTramGiamGia;
//    private double thanhTien;
//    private double tienKhachDua;
//    private double tienTraLai;
//
//    private NhanVien maNhanVien;
//    private KhachHang maKhachHang;
//    private KhuyenMai maKhuyenMai;
//
//    public HoaDon(String maHoaDon, Date ngayTaoHoaDon, String phuongThucThanhToan,
//                  float phanTramGiamGia, double thanhTien, double tienKhachDua, double tienTraLai,
//                  NhanVien maNhanVien, KhachHang maKhachHang, KhuyenMai maKhuyenMai) {
//        this.maHoaDon = maHoaDon;
//        this.ngayTaoHoaDon = ngayTaoHoaDon;
//        this.phuongThucThanhToan = phuongThucThanhToan;
//        this.phanTramGiamGia = phanTramGiamGia;
//        this.thanhTien = thanhTien;
//        this.tienKhachDua = tienKhachDua;
//        this.tienTraLai = tienTraLai;
//        this.maNhanVien = maNhanVien;
//        this.maKhachHang = maKhachHang;
//        this.maKhuyenMai = maKhuyenMai;
//    }
//
//    public String getMaHoaDon() {
//        return maHoaDon;
//    }
//
//    public void setMaHoaDon(String maHoaDon) {
//        this.maHoaDon = maHoaDon;
//    }
//
//    public Date getNgayTaoHoaDon() {
//        return ngayTaoHoaDon;
//    }
//
//    public void setNgayTaoHoaDon(Date ngayTaoHoaDon) {
//        this.ngayTaoHoaDon = ngayTaoHoaDon;
//    }
//
//    public String getPhuongThucThanhToan() {
//        return phuongThucThanhToan;
//    }
//
//    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
//        this.phuongThucThanhToan = phuongThucThanhToan;
//    }
//
//    public float getPhanTramGiamGia() {
//        return phanTramGiamGia;
//    }
//
//    public void setPhanTramGiamGia(float phanTramGiamGia) {
//        this.phanTramGiamGia = phanTramGiamGia;
//    }
//
//    public double getThanhTien() {
//        return thanhTien;
//    }
//
//    public void setThanhTien(double thanhTien) {
//        this.thanhTien = thanhTien;
//    }
//
//    public double getTienKhachDua() {
//        return tienKhachDua;
//    }
//
//    public void setTienKhachDua(double tienKhachDua) {
//        this.tienKhachDua = tienKhachDua;
//    }
//
//    public double getTienTraLai() {
//        return tienTraLai;
//    }
//
//    public void setTienTraLai(double tienTraLai) {
//        this.tienTraLai = tienTraLai;
//    }
//
//    public NhanVien getMaNhanVien() {
//        return maNhanVien;
//    }
//
//    public void setMaNhanVien(NhanVien maNhanVien) {
//        this.maNhanVien = maNhanVien;
//    }
//
//    public KhachHang getMaKhachHang() {
//        return maKhachHang;
//    }
//
//    public void setMaKhachHang(KhachHang maKhachHang) {
//        this.maKhachHang = maKhachHang;
//    }
//
//    public KhuyenMai getMaKhuyenMai() {
//        return maKhuyenMai;
//    }
//
//    public void setMaKhuyenMai(KhuyenMai maKhuyenMai) {
//        this.maKhuyenMai = maKhuyenMai;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(maHoaDon);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null || getClass() != obj.getClass())
//            return false;
//        HoaDon other = (HoaDon) obj;
//        return Objects.equals(maHoaDon, other.maHoaDon);
//    }
//}
////