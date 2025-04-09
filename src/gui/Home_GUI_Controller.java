package gui;

import java.io.File;

import dao.NhanVien_DAO;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Home_GUI_Controller {
    public String maNhanVien;

    @FXML
    private AnchorPane pnHome;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuTroGiup;
    @FXML
    private MenuItem itemThongTinPhanMem;
    @FXML
    private MenuItem itemHuongDanSuDung;
    @FXML
    private Label lblQuanLyBanVe;
    @FXML
    private ImageView imgQuanLyBanVe;
    @FXML
    private Label lblTrangChu;
    @FXML
    private Label lblMaNhanVien;
    @FXML
    private Label lblTenNhanVien;
    @FXML
    private Label lblChucVu;
    @FXML
    private ImageView imgAnhNhanVien;

    @FXML
    public void initialize() {
        pnHome.getStyleClass().add("anchor-pane");
        lblTrangChu.getStyleClass().add("hover-label");
        System.out.println("Label size: " + lblTrangChu.getWidth() + "x" + lblTrangChu.getHeight());
        System.out.println("Label insets: " + lblTrangChu.getInsets());

        // Không gọi DAO trong initialize, để trống ban đầu
        lblMaNhanVien.setText("Mã nhân viên: Chưa tải");
        lblTenNhanVien.setText("Tên nhân viên: Chưa tải");
        lblChucVu.setText("Chức vụ: Chưa tải");
        
        
    }

    // Phương thức để cập nhật thông tin nhân viên sau khi setMaNhanVien
    public void updateNhanVienInfo() {
        if (maNhanVien != null && !maNhanVien.isEmpty()) {
            NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
            NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien);
            if (nv != null) {
                lblMaNhanVien.setText(nv.getMaNhanVien());
                lblTenNhanVien.setText(nv.getTenNhanVien());
                if (nv.getChucVu().equals(ChucVu.banVe)) {
                    lblChucVu.setText("Bán vé");
                } else {
                    lblChucVu.setText("Quản lý");
                }
                File imageFile = new File(nv.getLinkAnh());
                Image image = new Image(imageFile.toURI().toString());
                imgAnhNhanVien.setImage(image);
            } else {
                lblMaNhanVien.setText("Mã nhân viên: Không tìm thấy");
                lblTenNhanVien.setText("Tên nhân viên: Không tìm thấy");
                lblChucVu.setText("Chức vụ: Không xác định");
            }
        }
    }

    // Các thành phần khác
    @FXML private ImageView imgTrangChu;
    @FXML private Label lblQuanLyLichSu;
    @FXML private ImageView imgQuanLyLichSu;
    @FXML private Label lblQuanLyVe;
    @FXML private ImageView imgQuanLyVe;
    @FXML private Label lblQuanLyHoaDon;
    @FXML private ImageView imgQuanLyHoaDon;
    @FXML private Label lblQuanLyKhachHang;
    @FXML private ImageView imgQuanLyKhachHang;
    @FXML private Label lblQuanLyNhanVien;
    @FXML private ImageView imgQuanLyNhanVien;
    @FXML private Label lblQuanLyChuyenTau;
    @FXML private ImageView imgQuanLyChuyenTau;
    @FXML private Label lblThongKe;
    @FXML private ImageView imgThongKe;
    @FXML private Label lblQuanLyTaiKhoan;
    @FXML private ImageView imgQuanLyTaiKhoan;
    @FXML private Label lblDangXuat;
    @FXML private ImageView imgDangXuat;

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        updateNhanVienInfo(); // Cập nhật thông tin sau khi gán
    }
}