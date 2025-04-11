package gui;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.ChuyenTau_DAO;
import dao.Ga_DAO;
import dao.NhanVien_DAO;
import dao.Tau_DAO;
import dao.TuyenTau_DAO;
import entity.ChuyenTau;
import entity.Ga;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.Tau;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.control.DateCell;

public class QuanLyBanVe_GUI_Controller {
    private String maNhanVien;

    @FXML
    private Label lblQuanLyVe;
    
    @FXML
    private Label lblTrangChu;
    
    @FXML
    private Label lblQuanLyHoaDon;
    
    @FXML
    private Label lblQuanLyKhachHang;
    
    @FXML
    private Label lblQuanLyNhanVien;
    
    @FXML
    private Label lblThongKe;
    
    @FXML
    private Label lblQuanLyChuyenTau;
    
    @FXML
    public void initialize() {
    	// Initialize DatePickers and CheckBox
        initializeDatePickersAndCheckBox();
    	// Initialize ComboBoxes for gaDi and gaDen
        initializeComboBoxes();
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
        
        // Handler cho thống kê
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
    	
    }

    private void initializeComboBoxes() {
        Ga_DAO gaDAO = new Ga_DAO();
        TuyenTau_DAO tuyenTauDAO = new TuyenTau_DAO();

        // Load all stations into both ComboBoxes
        List<Ga> allGa = gaDAO.timGaTheoTen(""); // Get all stations by using empty search
        cboGaDi.setItems(FXCollections.observableArrayList(allGa));
        cboGaDen.setItems(FXCollections.observableArrayList(allGa));

        // Set StringConverter to display tenGa
        cboGaDi.setConverter(new StringConverter<Ga>() {
            @Override
            public String toString(Ga ga) {
                return ga != null ? ga.getTenGa() : "";
            }

            @Override
            public Ga fromString(String string) {
                return null; // Not needed for selection
            }
        });

        cboGaDen.setConverter(new StringConverter<Ga>() {
            @Override
            public String toString(Ga ga) {
                return ga != null ? ga.getTenGa() : "";
            }

            @Override
            public Ga fromString(String string) {
                return null; // Not needed for selection
            }
        });

        // Listener for cboGaDi
        cboGaDi.getSelectionModel().selectedItemProperty().addListener((obs, oldGa, newGa) -> {
            if (newGa != null) {
                // Get valid gaDen for selected gaDi
                List<Ga> validGaDen = tuyenTauDAO.getDanhSachGaDenTheoGaDi(newGa.getMaGa());
                // Remove selected gaDi from validGaDen to enforce maGaDi ≠ maGaDen
                validGaDen.removeIf(ga -> ga.getMaGa().equals(newGa.getMaGa()));
                cboGaDen.setItems(FXCollections.observableArrayList(validGaDen));
                // Clear cboGaDen selection if it's no longer valid
                if (cboGaDen.getValue() != null && 
                    !validGaDen.contains(cboGaDen.getValue())) {
                    cboGaDen.getSelectionModel().clearSelection();
                }
            } else {
                // Reset cboGaDen to all stations if no gaDi selected
                cboGaDen.setItems(FXCollections.observableArrayList(allGa));
            }
        });

        // Listener for cboGaDen
        cboGaDen.getSelectionModel().selectedItemProperty().addListener((obs, oldGa, newGa) -> {
            if (newGa != null) {
                // Get valid gaDi for selected gaDen
                List<Ga> validGaDi = tuyenTauDAO.getDanhSachGaDiTheoGaDen(newGa.getMaGa());
                // Remove selected gaDen from validGaDi to enforce maGaDi ≠ maGaDen
                validGaDi.removeIf(ga -> ga.getMaGa().equals(newGa.getMaGa()));
                cboGaDi.setItems(FXCollections.observableArrayList(validGaDi));
                // Clear cboGaDi selection if it's no longer valid
                if (cboGaDi.getValue() != null && 
                    !validGaDi.contains(cboGaDi.getValue())) {
                    cboGaDi.getSelectionModel().clearSelection();
                }
            } else {
                // Reset cboGaDi to all stations if no gaDen selected
                cboGaDi.setItems(FXCollections.observableArrayList(allGa));
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
    
    @FXML
    private ComboBox<Ga> cboGaDi;
    
    @FXML
    private ComboBox<Ga> cboGaDen;
    
    @FXML
    private DatePicker dpNgayDi;
    
    @FXML
    private DatePicker dpNgayVe;
    
    @FXML
    private CheckBox ckcKhuHoi;
    
    private void initializeDatePickersAndCheckBox() {
        // Restrict dpNgayDi to today or future dates
        Callback<DatePicker, DateCell> dayCellFactoryNgayDi = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        };
        dpNgayDi.setDayCellFactory(dayCellFactoryNgayDi);

        // Restrict dpNgayVe to today or future dates AND after dpNgayDi
        Callback<DatePicker, DateCell> dayCellFactoryNgayVe = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate ngayDi = dpNgayDi.getValue();
                // Disable if: empty OR before today OR before dpNgayDi (if dpNgayDi is selected)
                setDisable(empty || date.isBefore(LocalDate.now()) || 
                           (ngayDi != null && date.isBefore(ngayDi)));
            }
        };
        dpNgayVe.setDayCellFactory(dayCellFactoryNgayVe);

        // Initialize dpNgayVe as disabled and non-editable by default
        dpNgayVe.setDisable(true);
        dpNgayVe.setEditable(false);

        // Listener for ckcKhuHoi to toggle dpNgayVe
        ckcKhuHoi.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            dpNgayVe.setDisable(!isSelected);
            dpNgayVe.setEditable(isSelected);
            if (!isSelected) {
                dpNgayVe.setValue(null); // Clear selection when disabled
            }
        });

        // Listener for dpNgayDi to clear dpNgayVe if it becomes invalid
        dpNgayDi.valueProperty().addListener((obs, oldDate, newDate) -> {
            LocalDate ngayVe = dpNgayVe.getValue();
            if (newDate != null && ngayVe != null && ngayVe.isBefore(newDate)) {
                dpNgayVe.setValue(null); // Clear dpNgayVe if it's before the new dpNgayDi
            }
        });
    }
    
    @FXML
    private Button btnTim;
    
    @FXML
    private void btnTimClicked() {
    	if(kiemTraThongTinTim()) {
    		String tenGaDi = cboGaDi.getValue().getTenGa();
    		String tenGaDen = cboGaDen.getValue().getTenGa();
    		LocalDate ngayKhoiHanh = dpNgayDi.getValue();
    		
    		List<ChuyenTau> dsct = timChuyenTauTheo(tenGaDi, tenGaDen, ngayKhoiHanh);
    		if(dsct.isEmpty()) {
    			showWarningAlert("Không tìm thấy chuyến tàu phù hợp!", "image/canhBao.png");
    			return;
    		}
    		int soLuongChuyenTau = dsct.size();
        	pnChuyenTau.setPrefWidth(160*soLuongChuyenTau + 14);
    		taoPaneChuyenTau(dsct);
    	}
    }
    
    private boolean kiemTraThongTinTim() {
        // Kiểm tra ga đi
        if(cboGaDi.getValue() == null) { // Kiểm tra giá trị được chọn
            showWarningAlert("Bạn chưa chọn ga đi!", "image/canhBao.png");
            return false;
        }
        
        // Kiểm tra ga đến
        if(cboGaDen.getValue() == null) { // Kiểm tra giá trị được chọn
            showWarningAlert("Bạn chưa chọn ga đến!", "image/canhBao.png");
            return false;
        }
        
        // Kiểm tra ngày đi
        if(dpNgayDi.getValue() == null) {
            showWarningAlert("Bạn chưa chọn ngày khởi hành!", "image/canhBao.png");
            return false;
        }
        
        return true;
    }
    
    private List<ChuyenTau> timChuyenTauTheo(String tenGaDi, String tenGaDen, LocalDate ngayKhoiHanh) {
		List<ChuyenTau> dsct = new ArrayList<ChuyenTau>();
		ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
		dsct = chuyenTau_DAO.timChuyenTauTheoTenGaVaNgay(tenGaDi, tenGaDen, ngayKhoiHanh);
		return dsct;
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

    private void showInformationAlert(String message, String icon) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
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
    
    private void taoPaneChuyenTau(List<ChuyenTau> dsct) {
        int layoutX = 14;
        int layoutY = 14;

        // Xóa các node cũ trong pnChuyenTau để tránh trùng lặp
        pnChuyenTau.getChildren().clear();
        System.out.println("Đã xóa các node cũ trong pnChuyenTau");

        // Kiểm tra danh sách ChuyenTau
        if (dsct == null || dsct.isEmpty()) {
            System.out.println("Danh sách ChuyenTau rỗng hoặc null");
            return;
        }
        System.out.println("Số lượng ChuyenTau: " + dsct.size());

        // Tạo đối tượng Tau_DAO để tìm tàu
        Tau_DAO tauDAO = new Tau_DAO();

        // Duyệt qua danh sách các ChuyenTau
        int index = 0;
        for (ChuyenTau chuyenTau : dsct) {
            // Tạo AnchorPane cho mỗi ChuyenTau
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(160, 160); // Giữ kích thước AnchorPane là 160x160
            anchorPane.setLayoutX(layoutX);
            anchorPane.setLayoutY(layoutY);
            System.out.println("Tạo AnchorPane " + index + " tại (" + anchorPane.getLayoutX() + ", " + anchorPane.getLayoutY() + ")");

            // Tăng layoutX để đặt AnchorPane tiếp theo
            layoutX += 160; // Giữ khoảng cách giữa các AnchorPane

            // Tạo ImageView
            ImageView imageView = new ImageView();
            String imagePath = "image/QuanLyBanVe_TauLuaChuaChon.png"; // Đường dẫn tương đối từ thư mục gốc dự án
            try {
                // Load ảnh từ file hệ thống
                File file = new File(imagePath);
                if (!file.exists()) {
                    throw new IllegalArgumentException("Không tìm thấy file ảnh tại: " + file.getAbsolutePath());
                }
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(140);  // Đặt chiều rộng là 140
                imageView.setFitHeight(140); // Đặt chiều cao là 140
                imageView.setPreserveRatio(false); // Không giữ tỷ lệ, lấp đầy 140x140
                System.out.println("Load ảnh thành công cho ChuyenTau " + chuyenTau.getMaChuyenTau());

                // Căn giữa ImageView trong AnchorPane
                AnchorPane.setTopAnchor(imageView, 0.0);    // Cách đỉnh 0px
                AnchorPane.setLeftAnchor(imageView, 10.0);  // Cách trái 10px
                AnchorPane.setRightAnchor(imageView, 10.0); // Cách phải 10px
            } catch (Exception e) {
                System.err.println("Lỗi khi tải ảnh tại " + imagePath + ": " + e.getMessage());
                anchorPane.setStyle("-fx-background-color: red;"); // Màu đỏ để dễ thấy
            }

            // Tạo Label 1: Ký hiệu tàu (tìm Tau theo maTau rồi gọi getKyHieuTau)
            Label kyHieuLabel = new Label();
            Tau tau = tauDAO.timTauTheoMa(chuyenTau.getMaTau());
            if (tau != null) {
                kyHieuLabel.setText(tau.getKyHieuTau());
            } else {
                kyHieuLabel.setText("N/A"); // Nếu không tìm thấy tàu
            }
            kyHieuLabel.setLayoutX(68); // Đặt tại tọa độ x = 59
            kyHieuLabel.setLayoutY(52); // Đặt tại tọa độ y = 57

            // Tạo Label 2: Giờ khởi hành
            Label gioKhoiHanhLabel = new Label();
            gioKhoiHanhLabel.setText(chuyenTau.getGioKhoiHanh().toString()); // Giả định getGioKhoiHanh trả về LocalTime
            gioKhoiHanhLabel.setLayoutX(68); // Đặt tại tọa độ x = 59
            gioKhoiHanhLabel.setLayoutY(135); // Đặt tại tọa độ y = 138

            // Thêm ImageView và các Label vào AnchorPane
            anchorPane.getChildren().addAll(imageView, kyHieuLabel, gioKhoiHanhLabel);

            // Thêm AnchorPane vào pnChuyenTau
            pnChuyenTau.getChildren().add(anchorPane);
            System.out.println("Đã thêm AnchorPane " + index + " vào pnChuyenTau");

            index++;
        }
    }
    
	@FXML
	private Pane pnChuyenTau;
}