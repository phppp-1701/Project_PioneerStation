package gui;

import java.io.File;

import dao.NhanVien_DAO;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

        // Không gọi DAO trong initialize, để trống ban đầu
        lblMaNhanVien.setText("Mã nhân viên: Chưa tải");
        lblTenNhanVien.setText("Tên nhân viên: Chưa tải");
        lblChucVu.setText("Chức vụ: Chưa tải");
        
     // Thêm sự kiện nhấp chuột cho lblDangXuat với xác nhận
        lblDangXuat.setOnMouseClicked(event -> {
            // Tạo hộp thoại xác nhận
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận đăng xuất");
            alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất?");
            alert.setContentText("Chọn OK để đăng xuất và quay lại màn hình đăng nhập.");
         // Thêm icon cho Alert
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            File iconFile = new File("image/hoi.png");
            if (iconFile.exists()) {
                Image icon = new Image(iconFile.toURI().toString());
                alertStage.getIcons().add(icon);
            } else {
                System.err.println("Không tìm thấy file icon: " + iconFile.getAbsolutePath());
            }
            // Hiển thị hộp thoại và chờ phản hồi
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    System.out.println("Người dùng xác nhận đăng xuất");
                    try {
                        // Tạo Stage mới cho DangNhap_GUI
                        Stage loginStage = new Stage();
                        new DangNhap_GUI(loginStage);

                        // Đóng cửa sổ hiện tại
                        Stage currentStage = (Stage) pnHome.getScene().getWindow();
                        currentStage.close();
                    } catch (Exception e) {
                        System.err.println("Lỗi khi mở DangNhap_GUI: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Người dùng hủy đăng xuất");
                }
            });
        });
        
     // Sự kiện nhấp chuột cho lblQuanLyBanVe
        lblQuanLyBanVe.setOnMouseClicked(event -> {
            System.out.println("Đã nhấp vào Quản lý bán vé");
            try {
                // Tạo Stage mới cho QuanLyBanVe_GUI
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyBanVe_GUI(currentStage, maNhanVien); // Truyền maNhanVien nếu cần
            } catch (Exception e) {
                System.err.println("Lỗi khi mở QuanLyBanVe_GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
     // Thêm vào phương thức initialize()
        lblQuanLyVe.setOnMouseClicked(event -> {
            System.out.println("Đã nhấp vào Quản lý vé");
            try {
                // Tạo Stage mới cho QuanLyVe_GUI
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyVe_GUI(currentStage, maNhanVien); // Truyền maNhanVien nếu cần
            } catch (Exception e) {
                System.err.println("Lỗi khi mở QuanLyVe_GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
     // Thêm vào phương thức initialize() trong Home_GUI_Controller.java
        lblQuanLyHoaDon.setOnMouseClicked(event -> {
            System.out.println("Đã nhấp vào Quản lý hóa đơn");
            try {
                // Tạo Stage mới cho QuanLyHoaDon_GUI
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyHoaDon_GUI(currentStage, maNhanVien); // Truyền maNhanVien nếu cần
            } catch (Exception e) {
                System.err.println("Lỗi khi mở QuanLyHoaDon_GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
     // Handler cho Quản lý khách hàng
        lblQuanLyKhachHang.setOnMouseClicked(event -> {
            System.out.println("Đã nhấp vào Quản lý khách hàng");
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyKhachHang_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                System.err.println("Lỗi khi mở QuanLyKhachHang_GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
     // Handler cho Quản lý nhân viên
        lblQuanLyNhanVien.setOnMouseClicked(event -> {
            System.out.println("Đã nhấp vào Quản lý nhân viên");
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyNhanVien_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                System.err.println("Lỗi khi mở QuanLyNhanVien_GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        
        // Handler cho thống kê
        lblThongKe.setOnMouseClicked(event -> {
            System.out.println("Đã nhấp vào Thống kê");
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new ThongKe_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                System.err.println("Lỗi khi mở ThongKe_GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        // Handler cho trang chủ
        lblTrangChu.setOnMouseClicked(event -> {
            System.out.println("Đã nhấp vào Home");
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new Home_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                System.err.println("Lỗi khi mở Home_GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        // Handler cho quản lý chuyến tàu
        lblQuanLyChuyenTau.setOnMouseClicked(event -> {
            System.out.println("Đã nhấp vào Quản lý chuyến tàu");
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyChuyenTau_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                System.err.println("Lỗi khi mở Home_GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
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