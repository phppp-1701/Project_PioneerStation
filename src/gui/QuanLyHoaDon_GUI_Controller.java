package gui;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuanLyHoaDon_GUI_Controller {
    private String maNhanVien;
    
    @FXML
    private AnchorPane pnHome;
    
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
    
    @FXML private Label lblQuanLyBanVe;
    @FXML private Label lblTrangChu;
    @FXML
    public void initialize() {
        // Khởi tạo giao diện
        if (maNhanVien != null) {
            updateNhanVienInfo();
        }
        
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
        
     // Vô hiệu hóa txtMaHoaDon
        txtMaHoaDon.setDisable(true);
        txtMaHoaDon.setEditable(false);

        // Vô hiệu hóa các trường hiển thị (chỉ đọc)
        txtTenKhachHang.setEditable(false);
        txtNgayLapHoaDon.setEditable(false);
        txtLoaiKhachHang.setEditable(false);
        txtTenNhanVien.setEditable(false);
        txtKhuyenMai.setEditable(false);
        txtPTTT.setEditable(false);
        txtPhanTramGiamGia.setEditable(false);
        txtTienKhachDua.setEditable(false);
        txtThanhTien.setEditable(false);
        txtTienTraLai.setEditable(false);

        // Thiết lập các cột TableView
        colStt.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.valueOf(tbDanhSachHoaDon.getItems().indexOf(cellData.getValue()) + 1)));
        colMaHoaDon.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
        colTenKhachHang.setCellValueFactory(cellData -> {
            String maKhachHang = cellData.getValue().getMaKhachHang();
            KhachHang_DAO khachHangDAO = new KhachHang_DAO();
            KhachHang kh = khachHangDAO.timKhachHangTheoMa(maKhachHang);
            return new SimpleStringProperty(kh != null ? kh.getTenKhachHang() : "Không tìm thấy");
        });
        colNgayLapHoaDon.setCellValueFactory(cellData -> {
            LocalDate ngay = cellData.getValue().getNgayTaoHoaDon();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new SimpleStringProperty(ngay != null ? ngay.format(formatter) : "");
        });
        colLoaiKhachHang.setCellValueFactory(cellData -> {
            String maKhachHang = cellData.getValue().getMaKhachHang();
            KhachHang_DAO khachHangDAO = new KhachHang_DAO();
            KhachHang kh = khachHangDAO.timKhachHangTheoMa(maKhachHang);
            return new SimpleStringProperty(kh != null && kh.getLoaiThanhVien() != null ? kh.getLoaiThanhVien().toString() : "Không xác định");
        });
        colTenNhanVien.setCellValueFactory(cellData -> {
            String maNhanVien = cellData.getValue().getMaNhanVien();
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            NhanVien nv = nhanVienDAO.timNhanVienTheoMa(maNhanVien);
            return new SimpleStringProperty(nv != null ? nv.getTenNhanVien() : "Không tìm thấy");
        });
        colPTTT.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getPhuongThucThanhToan() != null ? 
                cellData.getValue().getPhuongThucThanhToan().toString() : ""));
        colThanhTien.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getThanhTien() != null ? 
                cellData.getValue().getThanhTien().toString() : "0"));

        // Sự kiện nhấp chuột vào TableView
        tbDanhSachHoaDon.setOnMouseClicked(event -> {
            HoaDon selectedHoaDon = tbDanhSachHoaDon.getSelectionModel().getSelectedItem();
            if (selectedHoaDon != null) {
                populateHoaDonFields(selectedHoaDon);
            }
        });
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        updateNhanVienInfo(); // Cập nhật thông tin sau khi gán
    }
    
    @FXML 
    private Label lblMaNhanVien;
    
    @FXML
    private Label lblTenNhanVien;
    
    @FXML
    private Label lblChucVu;
    
    @FXML 
    private ImageView imgAnhNhanVien;
    
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
                try {
                    File imageFile = new File(nv.getLinkAnh());
                    Image image = new Image(imageFile.toURI().toString());
                    imgAnhNhanVien.setImage(image);
                } catch (Exception e) {
                    System.err.println("Không thể tải ảnh nhân viên: " + e.getMessage());
                }
            } else {
                lblMaNhanVien.setText("Mã nhân viên: Không tìm thấy");
                lblTenNhanVien.setText("Tên nhân viên: Không tìm thấy");
                lblChucVu.setText("Chức vụ: Không xác định");
            }
        }
    }
    
    @FXML
    private TextField txtTimTenKhachHang;
    @FXML
    private DatePicker dpTimNgayLapHoaDon;
    @FXML
    private TextField txtTimSoDienThoai;
    @FXML
    private Button btnTimHoaDon;

    
    @FXML
    private TextField txtMaHoaDon;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtNgayLapHoaDon;
    @FXML
    private TextField txtLoaiKhachHang;
    @FXML
    private TextField txtTenNhanVien;
    @FXML
    private TextField txtKhuyenMai;
    @FXML
    private TextField txtPTTT;
    @FXML
    private TextField txtPhanTramGiamGia;
    @FXML
    private TextField txtTienKhachDua;
    @FXML
    private TextField txtThanhTien;
    @FXML
    private TextField txtTienTraLai;

    @FXML
    private TableView<HoaDon> tbDanhSachHoaDon;
    @FXML
    private TableColumn<HoaDon, String> colStt;
    @FXML
    private TableColumn<HoaDon, String> colMaHoaDon;
    @FXML
    private TableColumn<HoaDon, String> colTenKhachHang;
    @FXML
    private TableColumn<HoaDon, String> colNgayLapHoaDon;
    @FXML
    private TableColumn<HoaDon, String> colLoaiKhachHang;
    @FXML
    private TableColumn<HoaDon, String> colTenNhanVien;
    @FXML
    private TableColumn<HoaDon, String> colPTTT;
    @FXML
    private TableColumn<HoaDon, String> colThanhTien;


    @FXML
    private void btnTimHoaDonClicked() {
        String tenKhachHang = txtTimTenKhachHang.getText().trim();
        LocalDate ngayTaoHoaDon = dpTimNgayLapHoaDon.getValue();
        String soDienThoai = txtTimSoDienThoai.getText().trim();

        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
        List<HoaDon> danhSachHoaDon;

        // Kiểm tra tiêu chí tìm kiếm
        if (!tenKhachHang.isEmpty() && ngayTaoHoaDon == null && soDienThoai.isEmpty()) {
            danhSachHoaDon = hoaDonDAO.timHoaDonTheoTenKhachHang(tenKhachHang);
        } else if (tenKhachHang.isEmpty() && ngayTaoHoaDon != null && soDienThoai.isEmpty()) {
            danhSachHoaDon = hoaDonDAO.timHoaDonTheoNgayTaoHoaDon(ngayTaoHoaDon);
        } else if (tenKhachHang.isEmpty() && ngayTaoHoaDon == null && !soDienThoai.isEmpty()) {
            danhSachHoaDon = hoaDonDAO.timHoaDonTheoSoDienThoai(soDienThoai);
        } else if (tenKhachHang.isEmpty() && ngayTaoHoaDon == null && soDienThoai.isEmpty()) {
            showWarningAlert("Vui lòng nhập ít nhất một tiêu chí tìm kiếm!", "image/canhBao.png");
            return;
        } else {
            danhSachHoaDon = hoaDonDAO.timHoaDonTheoTenKhachHangNgayLapSoDienThoai(
                tenKhachHang.isEmpty() ? null : tenKhachHang,
                ngayTaoHoaDon,
                soDienThoai.isEmpty() ? null : soDienThoai
            );
        }

        // Kiểm tra kết quả
        if (danhSachHoaDon.isEmpty()) {
            showWarningAlert("Không tìm thấy hóa đơn nào phù hợp!", "image/canhBao.png");
            tbDanhSachHoaDon.getItems().clear();
            return;
        }

        // Hiển thị lên TableView
        tbDanhSachHoaDon.setItems(FXCollections.observableArrayList(danhSachHoaDon));
    }

    private void populateHoaDonFields(HoaDon hoaDon) {
        try {
            txtMaHoaDon.setText(hoaDon.getMaHoaDon() != null ? hoaDon.getMaHoaDon() : "");
            
            // Lấy thông tin khách hàng
            KhachHang_DAO khachHangDAO = new KhachHang_DAO();
            KhachHang khachHang = khachHangDAO.timKhachHangTheoMa(hoaDon.getMaKhachHang());
            txtTenKhachHang.setText(khachHang != null ? khachHang.getTenKhachHang() : "Không tìm thấy");
            txtLoaiKhachHang.setText(khachHang != null && khachHang.getLoaiThanhVien() != null ? 
                khachHang.getLoaiThanhVien().toString() : "Không xác định");

            // Ngày tạo hóa đơn
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtNgayLapHoaDon.setText(hoaDon.getNgayTaoHoaDon() != null ? 
                hoaDon.getNgayTaoHoaDon().format(formatter) : "");

            // Lấy thông tin nhân viên
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            NhanVien nhanVien = nhanVienDAO.timNhanVienTheoMa(hoaDon.getMaNhanVien());
            txtTenNhanVien.setText(nhanVien != null ? nhanVien.getTenNhanVien() : "Không tìm thấy");

            txtKhuyenMai.setText(hoaDon.getMaKhuyenMai() != null ? hoaDon.getMaKhuyenMai() : "");
            txtPTTT.setText(hoaDon.getPhuongThucThanhToan() != null ? 
                hoaDon.getPhuongThucThanhToan().toString() : "");
            txtPhanTramGiamGia.setText(hoaDon.getPhanTramGiamGia() != 0 ? 
                String.valueOf(hoaDon.getPhanTramGiamGia()) : "0");
            txtTienKhachDua.setText(hoaDon.getTienKhachDua() != null ? 
                hoaDon.getTienKhachDua().toString() : "0");
            txtThanhTien.setText(hoaDon.getThanhTien() != null ? 
                hoaDon.getThanhTien().toString() : "0");
            txtTienTraLai.setText(hoaDon.getTienTraLai() != null ? 
                hoaDon.getTienTraLai().toString() : "0");
        } catch (Exception e) {
            showErrorAlert("Lỗi khi hiển thị thông tin hóa đơn: " + e.getMessage(), "image/loi.png");
        }
    }

    private void showErrorAlert(String message, String icon) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert, icon);
        alert.showAndWait();
    }

    private void showWarningAlert(String message, String icon) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert, icon);
        alert.showAndWait();
    }

    private void setAlertIcon(Alert alert, String icon) {
        File file = new File(icon);
        if (file.exists()) {
            try {
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(file.toURI().toString()));
            } catch (Exception e) {
                System.err.println("Lỗi khi tải ảnh: " + e.getMessage());
            }
        } else {
            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
        }
    }
    
}