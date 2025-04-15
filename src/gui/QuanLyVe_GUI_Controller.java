package gui;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import dao.ChoNgoi_DAO;
import dao.ChuyenTau_DAO;
import dao.Ga_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.Tau_DAO;
import dao.Toa_DAO;
import dao.TuyenTau_DAO;
import dao.Ve_DAO;
import entity.ChoNgoi;
import entity.ChuyenTau;
import entity.Ga;
import entity.KhachHang;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.Tau;
import entity.Toa;
import entity.Toa.LoaiToa;
import entity.TuyenTau;
import entity.Ve;
import entity.VeTam;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuanLyVe_GUI_Controller {
    private String maNhanVien;

    // Các thành phần menu
    @FXML private AnchorPane pnHome;
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
    @FXML private Label lblTrangChu;
    @FXML private Label lblQuanLyBanVe;

    // Thông tin nhân viên
    @FXML private Label lblMaNhanVien;
    @FXML private Label lblTenNhanVien;
    @FXML private Label lblChucVu;
    @FXML private ImageView imgAnhNhanVien;

    // Các thành phần quản lý vé
    @FXML private TextField txtMaVe;
    @FXML private TextField txtTenKhachHang;
    @FXML private DatePicker dpNgayTaoVe;
    @FXML private TableView<Ve> tbDanhSachVe;
    @FXML private TableColumn<Ve, Integer> colSTT;
    @FXML private TableColumn<Ve, String> colMaVe;
    @FXML private TableColumn<Ve, String> colNgayKhoiHanh;
    @FXML private TableColumn<Ve, String> colGioKhoiHanh;
    @FXML private TableColumn<Ve, String> colTenDichVu;
    @FXML private TableColumn<Ve, String> colLoaiKhachHang;
    @FXML private TableColumn<Ve, String> colGiaVe;

    @FXML
    public void initialize() {
        txtMaVe.setEditable(false);
        setupTableView();
        tbDanhSachVe.setItems(FXCollections.observableArrayList()); // Clear dữ liệu ban đầu
        setupMenuHandlers();
    }

    private void setupMenuHandlers() {
        // Handler cho quản lý chuyến tàu
        lblQuanLyChuyenTau.setOnMouseClicked(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyChuyenTau_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                showErrorAlert("Lỗi khi mở Quản lý chuyến tàu", e);
            }
        });

        // Handler đăng xuất
        lblDangXuat.setOnMouseClicked(event -> handleLogout());

        // Handler quản lý bán vé
        lblQuanLyBanVe.setOnMouseClicked(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyBanVe_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                showErrorAlert("Lỗi khi mở Quản lý bán vé", e);
            }
        });

        // Handler quản lý vé
        lblQuanLyVe.setOnMouseClicked(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyVe_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                showErrorAlert("Lỗi khi mở Quản lý vé", e);
            }
        });

        // Các handler khác...
        lblQuanLyHoaDon.setOnMouseClicked(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyHoaDon_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                showErrorAlert("Lỗi khi mở Quản lý hóa đơn", e);
            }
        });

        lblQuanLyKhachHang.setOnMouseClicked(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyKhachHang_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                showErrorAlert("Lỗi khi mở Quản lý khách hàng", e);
            }
        });

        lblQuanLyNhanVien.setOnMouseClicked(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new QuanLyNhanVien_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                showErrorAlert("Lỗi khi mở Quản lý nhân viên", e);
            }
        });

        lblThongKe.setOnMouseClicked(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new ThongKe_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                showErrorAlert("Lỗi khi mở Thống kê", e);
            }
        });

        lblTrangChu.setOnMouseClicked(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                new Home_GUI(currentStage, maNhanVien);
            } catch (Exception e) {
                showErrorAlert("Lỗi khi mở Trang chủ", e);
            }
        });
    }

    private void handleLogout() {
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
        }

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Stage loginStage = new Stage();
                    new DangNhap_GUI(loginStage);
                    Stage currentStage = (Stage) pnHome.getScene().getWindow();
                    currentStage.close();
                } catch (Exception e) {
                    showErrorAlert("Lỗi khi đăng xuất", e);
                }
            }
        });
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        updateNhanVienInfo();
    }

    private void updateNhanVienInfo() {
        if (maNhanVien != null && !maNhanVien.isEmpty()) {
            NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
            NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien);
            if (nv != null) {
                lblMaNhanVien.setText(nv.getMaNhanVien());
                lblTenNhanVien.setText(nv.getTenNhanVien());
                lblChucVu.setText(nv.getChucVu().equals(ChucVu.banVe) ? "Bán vé" : "Quản lý");
                
                File imageFile = new File(nv.getLinkAnh());
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imgAnhNhanVien.setImage(image);
                }
            } else {
                lblMaNhanVien.setText("Không tìm thấy");
                lblTenNhanVien.setText("Không tìm thấy");
                lblChucVu.setText("Không xác định");
            }
        }
    }

    private void setupTableView() {
        // Setup TableView columns
        colSTT.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(tbDanhSachVe.getItems().indexOf(cellData.getValue()) + 1).asObject());
        
        colMaVe.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getMaVe()));
        
        colNgayKhoiHanh.setCellValueFactory(cellData -> {
            String maChoNgoi = cellData.getValue().getMaChoNgoi();
			ChoNgoi choNgoi = new ChoNgoi_DAO().timChoNgoiTheoMaChoNgoi(maChoNgoi);
			ChuyenTau chuyenTau = new ChuyenTau_DAO().timChuyenTauTheoMaChuyenTau(choNgoi.getMaChuyenTau());
			return new SimpleStringProperty(chuyenTau.getNgayKhoiHanh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });

        colGioKhoiHanh.setCellValueFactory(cellData -> {
            String maChoNgoi = cellData.getValue().getMaChoNgoi();
			ChoNgoi choNgoi = new ChoNgoi_DAO().timChoNgoiTheoMaChoNgoi(maChoNgoi);
			ChuyenTau chuyenTau = new ChuyenTau_DAO().timChuyenTauTheoMaChuyenTau(choNgoi.getMaChuyenTau());
			return new SimpleStringProperty(chuyenTau.getGioKhoiHanh().format(DateTimeFormatter.ofPattern("HH:mm")));
        });

        colTenDichVu.setCellValueFactory(cellData -> {
            try {
                String maChoNgoi = cellData.getValue().getMaChoNgoi();
                ChoNgoi choNgoi = new ChoNgoi_DAO().timChoNgoiTheoMaChoNgoi(maChoNgoi);
                ChuyenTau chuyenTau = new ChuyenTau_DAO().timChuyenTauTheoMaChuyenTau(choNgoi.getMaChuyenTau());
                Tau tau = new Tau_DAO().timTauTheoMa(chuyenTau.getMaTau());
                Toa toa = new Toa_DAO().getToaByMaToa(choNgoi.getMaToa());
                return new SimpleStringProperty(String.format("Tàu: %s - Toa: %s - Loại toa: %s - Chỗ: %s",
                    tau.getTenTau(), toa.getTenToa(), loaiToaToString(toa.getLoaiToa()), choNgoi.getTenChoNgoi()));
            } catch (SQLException e) {
                return new SimpleStringProperty("N/A");
            }
        });

        colLoaiKhachHang.setCellValueFactory(cellData -> 
            new SimpleStringProperty(loaiKhachHangToString(cellData.getValue().getLoaiKhachHang())));

        colGiaVe.setCellValueFactory(cellData -> {
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return new SimpleStringProperty(currencyFormatter.format(cellData.getValue().getGiaVe()));
        });

        loadDanhSachVe();
        
     // Thêm sự kiện nhấp chuột vào TableView
        tbDanhSachVe.setOnMouseClicked(event -> {
            Ve selectedVe = tbDanhSachVe.getSelectionModel().getSelectedItem();
            if (selectedVe != null) {
                try {
                    hienThiThongTin(selectedVe.getMaVe());
                } catch (SQLException e) {
                    showErrorAlert("Lỗi khi hiển thị thông tin vé", e);
                }
            }
        });

        loadDanhSachVe();
    }

    private void loadDanhSachVe() {
        Ve_DAO veDAO = new Ve_DAO();
		List<Ve> danhSachVe = veDAO.layDanhSachVe();
		
		if (dpNgayTaoVe.getValue() != null) {
		    danhSachVe = veDAO.timVeTheoNgayTao(dpNgayTaoVe.getValue());
		}
		
		if (txtTenKhachHang.getText() != null && !txtTenKhachHang.getText().isEmpty()) {
		    danhSachVe = veDAO.timVeTheoTenKhachHang(txtTenKhachHang.getText());
		}
		
		tbDanhSachVe.setItems(FXCollections.observableArrayList(danhSachVe));
    }

    @FXML
    private void timKiemVe() {
        loadDanhSachVe();
    }

    private void showErrorAlert(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        
        // Thêm icon cảnh báo
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        File iconFile = new File("image/canhBao.png");
        if (iconFile.exists()) {
            Image icon = new Image(iconFile.toURI().toString());
            alertStage.getIcons().add(icon);
        }
        
        alert.showAndWait();
        e.printStackTrace();
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
    
    @FXML
    private Button btnTimVe;
    
    @FXML
    private void btnTimVeClicked() {
        boolean isTenKhachHangEmpty = txtTenKhachHang.getText() == null || txtTenKhachHang.getText().trim().isEmpty();
        boolean isNgayTaoVeEmpty = dpNgayTaoVe.getValue() == null;

        if (isTenKhachHangEmpty && isNgayTaoVeEmpty) {
            showWarningAlert("Vui lòng nhập ít nhất Tên khách hàng hoặc chọn Ngày tạo vé để tìm kiếm!", "image/canhBao.png");
            return;
        }

        loadDanhSachVe();
    }
    
    @FXML
    private TextField txtThongTinMaVe;
    
    @FXML
    private TextField txtThongTinMaHoaDon;
    
    @FXML
    private TextField txtThongTinNgayTaoVe;
    
    @FXML
    private TextField txtThongTinTrangThaiVe;
    
    @FXML
    private TextField txtThongTinTenKhachHang;
    
    @FXML
    private TextField txtThongTinLoaiKhachHang;
    
    @FXML
    private TextField txtThongTinCCCD_HoChieu;
    
    @FXML
    private TextField txtThongTinNgaySinh;
    
    @FXML
    private TextField txtThongTinChuyenTau;
    
    @FXML
    private TextField txtThongTinToa;
    
    @FXML
    private TextField txtThongTinLoaiToa;
    
    @FXML
    private TextField txtThongTinCho;
    
    @FXML
    private TextField txtThongTinGaDi;
    
    @FXML
    private TextField txtThongTinGaDen;
    
    @FXML
    private TextField txtThongTinNgayKhoiHanh;
    
    @FXML
    private TextField txtThongTinGioKhoiHanh;
    
    @FXML
    private TextField txtThongTinGiaVe;
    
	private void hienThiThongTin(String maVe) throws SQLException {
	    txtThongTinMaVe.setText(maVe);
	    Ve_DAO ve_DAO = new Ve_DAO();
	    Ve ve = ve_DAO.timVeTheoMa(maVe);
	    
	    // Set thông tin vé
	    txtThongTinMaHoaDon.setText(ve.getMaHoaDon() != null ? ve.getMaHoaDon() : "");
	    
	    // Định dạng ngày tạo vé (dd/MM/yyyy)
	    LocalDate ngayTaoVe = ve.getNgayTaoVe();
	    txtThongTinNgayTaoVe.setText(ngayTaoVe != null ? ngayTaoVe.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");
	    
	    // Xử lý trạng thái vé
	    txtThongTinTrangThaiVe.setText(trangThaiVeToString(ve.getTrangThaiVe()));
	    
	    // Set thông tin khách hàng
	    txtThongTinTenKhachHang.setText(ve.getTenKhachHang() != null ? ve.getTenKhachHang() : "");
	    txtThongTinLoaiKhachHang.setText(loaiKhachHangToString(ve.getLoaiKhachHang()));
	    txtThongTinCCCD_HoChieu.setText(ve.getCCCD_HoChieu() != null ? ve.getCCCD_HoChieu() : "");
	    
	    // Định dạng ngày sinh (dd/MM/yyyy)
	    LocalDate ngaySinh = ve.getNgaySinh();
	    txtThongTinNgaySinh.setText(ngaySinh != null ? ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");
	    
	    // Set thông tin chỗ ngồi và chuyến tàu
	    ChoNgoi_DAO choNgoi_DAO = new ChoNgoi_DAO();
	    ChoNgoi choNgoi = choNgoi_DAO.timChoNgoiTheoMaChoNgoi(ve.getMaChoNgoi());
	    
	    ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
	    ChuyenTau chuyenTau = chuyenTau_DAO.timChuyenTauTheoMaChuyenTau(choNgoi.getMaChuyenTau());
	    
	    Tau_DAO tau_DAO = new Tau_DAO();
	    Tau tau = tau_DAO.timTauTheoMa(chuyenTau.getMaTau());
	    txtThongTinChuyenTau.setText(tau.getTenTau() != null ? tau.getTenTau() : "");
	    
	    Toa_DAO toa_DAO = new Toa_DAO();
	    Toa toa = toa_DAO.getToaByMaToa(choNgoi.getMaToa());
	    txtThongTinToa.setText(toa.getTenToa() != null ? toa.getTenToa() : "");
	    txtThongTinLoaiToa.setText(loaiToaToString(toa.getLoaiToa()));
	    txtThongTinCho.setText(choNgoi.getTenChoNgoi() != null ? choNgoi.getTenChoNgoi() : "");
	    
	    // Set thông tin tuyến tàu
	    TuyenTau_DAO tuyenTau_DAO = new TuyenTau_DAO();
	    TuyenTau tuyenTau = tuyenTau_DAO.timTuyenTauTheoMaTuyen(chuyenTau.getMaTuyen());
	    txtThongTinGaDi.setText(tuyenTau.getGaDi() != null ? tuyenTau.getGaDi().getTenGa() : "");
	    txtThongTinGaDen.setText(tuyenTau.getGaDen() != null ? tuyenTau.getGaDen().getTenGa() : "");
	    
	    // Định dạng ngày và giờ khởi hành
	    LocalDate ngayKhoiHanh = chuyenTau.getNgayKhoiHanh();
	    txtThongTinNgayKhoiHanh.setText(ngayKhoiHanh != null ? ngayKhoiHanh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");
	    
	    LocalTime gioKhoiHanh = chuyenTau.getGioKhoiHanh();
	    txtThongTinGioKhoiHanh.setText(gioKhoiHanh != null ? gioKhoiHanh.format(DateTimeFormatter.ofPattern("HH:mm")) : "");
	    
	    // Định dạng giá vé theo tiền Việt Nam
	    BigDecimal giaVe = ve.getGiaVe();
	    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	    txtThongTinGiaVe.setText(giaVe != null ? currencyFormatter.format(giaVe) : "0 ₫");
	}
	
	private String trangThaiVeToString(Ve.TrangThaiVe trangThaiVe) {
	    if (trangThaiVe == null) {
	        return "Không xác định";
	    }
	    switch (trangThaiVe) {
	        case hoatDong:
	            return "Hoạt động";
	        case daHuy_Hoan:
	            return "Đã hủy/Hoàn";
	        case daDoi:
	            return "Đã đổi";
	        default:
	            return "Không xác định";
	    }
	}

	private String loaiToaToString(LoaiToa loaiToa) {
	    if (loaiToa == null) return "Không xác định";
	    switch (loaiToa) {
	        case giuongNamDieuHoa: return "Giường nằm điều hòa";
	        case ngoiMemDieuHoa: return "Ngồi mềm điều hòa";
	        case gheCungDieuHoa: return "Ghế cứng điều hòa";
	        default: return loaiToa.toString();
	    }
	}

	private String loaiKhachHangToString(VeTam.LoaiKhachHang loaiKhachHang) {
	    if (loaiKhachHang == null) return "Không xác định";
	    switch (loaiKhachHang) {
	        case nguoiLon: return "Người lớn";
	        case treEm: return "Trẻ em";
	        case sinhVien: return "Sinh viên";
	        case nguoiCaoTuoi: return "Người cao tuổi";
	        default: return loaiKhachHang.toString();
	    }
	}
}