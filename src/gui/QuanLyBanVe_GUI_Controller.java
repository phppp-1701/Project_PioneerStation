package gui;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.ChoNgoi_DAO;
import dao.ChuyenTau_DAO;
import dao.Ga_DAO;
import dao.NhanVien_DAO;
import dao.Tau_DAO;
import dao.Toa_DAO;
import dao.TuyenTau_DAO;
import entity.ChoNgoi;
import entity.ChoNgoi.TrangThaiChoNgoi;
import entity.ChuyenTau;
import entity.Ga;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.Tau;
import entity.Tau.LoaiTau;
import entity.Toa;
import entity.VeTam;
import entity.VeTam.LoaiKhachHang;
import entity.Toa.LoaiToa;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
    private Button btnThemVe;
    
    public void initialize() {
    	
    	cboLoaiKhachHang.setItems(FXCollections.observableArrayList(VeTam.LoaiKhachHang.values()));
    	cboLoaiKhachHang.setConverter(new StringConverter<VeTam.LoaiKhachHang>() {
    	    @Override
    	    public String toString(VeTam.LoaiKhachHang loai) {
    	        if (loai == null) return "";
    	        switch (loai) {
    	            case treEm:
    	                return "Trẻ em";
    	            case nguoiLon:
    	                return "Người lớn";
    	            case nguoiCaoTuoi:
    	                return "Người cao tuổi";
    	            case sinhVien:
    	                return "Sinh viên";
    	            default:
    	                return loai.toString();
    	        }
    	    }

    	    @Override
    	    public VeTam.LoaiKhachHang fromString(String string) {
    	        return null; // Không cần thiết vì người dùng không nhập trực tiếp
    	    }
    	});
    	cboLoaiKhachHang.setValue(VeTam.LoaiKhachHang.nguoiLon);
    	
    	// Listener cho cboLoaiKhachHang
    	cboLoaiKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
    	    if (newValue != null && !danhSachVeTam.isEmpty()) {
    	        // Cập nhật loại khách hàng cho tất cả vé tạm
    	        List<VeTam> newDanhSachVeTam = new ArrayList<>();
    	        for (VeTam veTam : danhSachVeTam) {
    	            ChoNgoi choNgoi = danhSachChoNgoi.stream()
    	                .filter(cn -> cn.getTenChoNgoi().equals(veTam.getMaChoNgoi()))
    	                .findFirst().orElse(null);
    	            if (choNgoi != null) {
    	                VeTam newVeTam = new VeTam(veTam.getMaChoNgoi(), choNgoi.getGiaCho(), newValue);
    	                newDanhSachVeTam.add(newVeTam);
    	            }
    	        }
    	        danhSachVeTam.clear();
    	        danhSachVeTam.addAll(newDanhSachVeTam);
    	        // Cập nhật giá tiền tạm tính
    	        capNhatGiaTamTinh();
    	        // Log để kiểm tra
    	        kiemTraDanhSachVeTam();
    	    }
    	});
    	
    	txtGiaTamTinh.setEditable(false);
    	dpNgayDi.setEditable(false);
    	dpNgayVe.setEditable(false);
        // Initialize DatePickers and CheckBox
        initializeDatePickersAndCheckBox();
        // Initialize ComboBoxes for gaDi and gaDen
        initializeComboBoxes();
        // Vô hiệu hóa các nút "Chọn tất cả" và "Bỏ chọn tất cả" ban đầu
        btnChonTatCa.setDisable(true);
        btnBoChonTatCa.setDisable(true);
        btnThemVe.setDisable(true);
        
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
    private void btnTimClicked() throws SQLException {
        if(kiemTraThongTinTim()) {
            pnToa.getChildren().clear();
            pnChoNgoi.getChildren().clear(); // Xóa sơ đồ chỗ ngồi cũ khi tìm chuyến mới
            selectedChoNgoiAnchorPanes.clear(); // Reset danh sách chỗ ngồi được chọn

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
    
    @FXML
    private Pane pnToa;
    
    private List<Toa> dstoa;
    
    private double tinhPaneToa(List<Toa> toaList) {
        // Khai báo các hằng số
        double widthPerPane = 200.0; // Chiều rộng của mỗi toa
        double spacing = 5.0;        // Khoảng cách giữa các toa
        double initialX = 82.0;      // Tọa độ X ban đầu
        double finalSpacing = 10.0;  // Khoảng cách thêm ở cuối

        // Tính số lượng toa (nếu toaList null thì số lượng bằng 0)
        int soLuongToa = (toaList != null) ? toaList.size() : 0;

        // Tính tổng chiều rộng:
        // initialX + (số toa * chiều rộng mỗi toa) + (khoảng cách giữa các toa) + khoảng cách cuối
        double totalWidth = initialX + (soLuongToa * widthPerPane) + (Math.max(0, soLuongToa - 1) * spacing) + finalSpacing;

        return totalWidth;
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
    
    // Biến instance để theo dõi AnchorPane và ChuyenTau đã chọn
    private AnchorPane selectedAnchorPane = null;
    private ChuyenTau chuyenTauDangChon = null; // Biến để lưu chuyến tàu đang chọn
    private AnchorPane selectedToaAnchorPane = null; // Biến để theo dõi AnchorPane Toa đã chọn
    private List<AnchorPane> selectedChoNgoiAnchorPanes = new ArrayList<>(); // Danh sách các chỗ ngồi đã chọn
    private int chonTheoDayCount = 0; // Đếm số chỗ đã chọn khi ckcChonTheoDay được bật
    private List<ChoNgoi> chonTheoDayChoNgois = new ArrayList<>(); // Lưu tạm 2 chỗ ngồi để chọn theo dãy

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

        // Đường dẫn ảnh
        String imagePathUnselected = "image/QuanLyBanVe_TauLuaChuaChon.png";
        String imagePathSelected = "image/QuanLyBanVe_TauLuaDaChon.png";

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
            try {
                // Load ảnh mặc định (chưa chọn) từ file hệ thống
                File file = new File(imagePathUnselected);
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
                System.err.println("Lỗi khi tải ảnh tại " + imagePathUnselected + ": " + e.getMessage());
                anchorPane.setStyle("-fx-background-color: red;"); // Màu đỏ để dễ thấy
            }

            // Tạo Label 1: Ký hiệu tàu
            Label kyHieuLabel = new Label();
            Tau tau = tauDAO.timTauTheoMa(chuyenTau.getMaTau());
            if (tau != null) {
                kyHieuLabel.setText(tau.getKyHieuTau());
            } else {
                kyHieuLabel.setText("N/A");
            }
            kyHieuLabel.setLayoutX(68); // Đặt tại tọa độ x = 68
            kyHieuLabel.setLayoutY(52); // Đặt tại tọa độ y = 52

            // Tạo Label 2: Giờ khởi hành
            Label gioKhoiHanhLabel = new Label();
            gioKhoiHanhLabel.setText(chuyenTau.getGioKhoiHanh().toString()); // Giả định getGioKhoiHanh trả về LocalTime
            gioKhoiHanhLabel.setLayoutX(68); // Đặt tại tọa độ x = 68
            gioKhoiHanhLabel.setLayoutY(135); // Đặt tại tọa độ y = 135

            // Thêm ImageView và các Label vào AnchorPane
            anchorPane.getChildren().addAll(imageView, kyHieuLabel, gioKhoiHanhLabel);

            // Thêm hiệu ứng hover: đổi con trỏ thành hình bàn tay khi di chuột vào
            anchorPane.setOnMouseEntered(event -> anchorPane.setCursor(Cursor.HAND));
            anchorPane.setOnMouseExited(event -> anchorPane.setCursor(Cursor.DEFAULT));

            // Thêm sự kiện click cho AnchorPane
            anchorPane.setOnMouseClicked(event -> {
                // Nếu có AnchorPane đã chọn trước đó, đổi ảnh của nó về "chưa chọn"
                if (selectedAnchorPane != null && selectedAnchorPane != anchorPane) {
                    ImageView previousImageView = (ImageView) selectedAnchorPane.getChildren().get(0); // Lấy ImageView đầu tiên
                    try {
                        File file = new File(imagePathUnselected);
                        if (!file.exists()) {
                            throw new IllegalArgumentException("Không tìm thấy file ảnh tại: " + file.getAbsolutePath());
                        }
                        Image image = new Image(file.toURI().toString());
                        previousImageView.setImage(image);
                        System.out.println("Đã đổi ảnh về chưa chọn cho AnchorPane trước đó");
                    } catch (Exception e) {
                        System.err.println("Lỗi khi tải ảnh chưa chọn: " + e.getMessage());
                    }
                }

                // Đổi ảnh của AnchorPane hiện tại sang "đã chọn"
                try {
                    File file = new File(imagePathSelected);
                    if (!file.exists()) {
                        throw new IllegalArgumentException("Không tìm thấy file ảnh tại: " + file.getAbsolutePath());
                    }
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                    System.out.println("Đã đổi ảnh thành công cho ChuyenTau " + chuyenTau.getMaChuyenTau());
                } catch (Exception e) {
                    System.err.println("Lỗi khi tải ảnh đã chọn tại " + imagePathSelected + ": " + e.getMessage());
                }

                // Cập nhật AnchorPane được chọn
                selectedAnchorPane = anchorPane;

                // Lưu chuyến tàu được chọn vào biến chuyenTauDangChon
                chuyenTauDangChon = chuyenTau;
                System.out.println("Đã chọn chuyến tàu: " + chuyenTauDangChon.getMaChuyenTau());
                
                
                // Vô hiệu hóa các nút khi chọn chuyến tàu mới
                btnBoChonTatCa.setDisable(true);
                btnChonTatCa.setDisable(true);
                btnThemVe.setDisable(true);
                
                // Lấy danh sách toa cho tàu của chuyến tàu được chọn
                try {
                    Toa_DAO toa_DAO = new Toa_DAO();
                    dstoa = toa_DAO.getToaByMaTau(chuyenTauDangChon.getMaTau());
                    if (dstoa == null || dstoa.isEmpty()) {
                        showWarningAlert("Không tìm thấy toa cho chuyến tàu này!", "image/canhBao.png");
                        return;
                    }
                    taoPaneToa(dstoa);
                } catch (SQLException e) {
                    e.printStackTrace();
                    showErrorAlert("Lỗi khi lấy danh sách toa: " + e.getMessage(), "image/canhBao.png");
                }
            });

            // Thêm AnchorPane vào pnChuyenTau
            pnChuyenTau.getChildren().add(anchorPane);
            System.out.println("Đã thêm AnchorPane " + index + " vào pnChuyenTau");

            index++;
        }
    }
    
    @FXML
    private Pane pnChuyenTau;
    
    private void taoPaneToa(List<Toa> dstoa) throws SQLException {
        // Xóa các node cũ trong pnToa để tránh trùng lặp
    	
        pnToa.getChildren().clear();

        // Đặt selectedToaAnchorPane về null khi tạo mới danh sách toa
        selectedToaAnchorPane = null;
        selectedChoNgoiAnchorPanes.clear(); // Reset danh sách chỗ ngồi được chọn khi tạo mới danh sách toa
        chonTheoDayCount = 0; // Reset đếm chọn theo dãy
        chonTheoDayChoNgois.clear(); // Reset danh sách tạm chọn theo dãy

        // Tính chiều rộng của pnToa dựa trên danh sách toa được truyền vào
        pnToa.setPrefWidth(tinhPaneToa(dstoa));

        // Thêm ảnh QuanLyBanVe_ToaTauLai.png một lần duy nhất tại tọa độ (0, 5)
        try {
            File file = new File("image/QuanLyBanVe_ToaTauLai.png");
            if (!file.exists()) {
                throw new IllegalArgumentException("Không tìm thấy file ảnh tại: " + file.getAbsolutePath());
            }
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);

            // Đặt kích thước ImageView thành 70x70
            imageView.setFitWidth(70.0);
            imageView.setFitHeight(70.0);
            imageView.setPreserveRatio(false);

            // Đặt tọa độ (0, 5) trong pnToa
            imageView.setLayoutX(0.0);
            imageView.setLayoutY(5.0);
            // Xoay ảnh 180 độ để đầu xe hướng sang trái
            imageView.setScaleX(-1);

            // Thêm ImageView vào pnToa
            pnToa.getChildren().add(imageView);
        } catch (Exception e) {
            System.err.println("Lỗi khi tải ảnh QuanLyBanVe_ToaTauLai.png: " + e.getMessage());
            // Thêm Rectangle màu đỏ để báo lỗi
            Rectangle errorRect = new Rectangle(70.0, 70.0); // Cập nhật kích thước Rectangle để khớp với ảnh
            errorRect.setFill(javafx.scene.paint.Color.RED);
            errorRect.setLayoutX(0.0);
            errorRect.setLayoutY(5.0);
            pnToa.getChildren().add(errorRect);
        }

        // Nếu dstoa rỗng hoặc null, không tạo AnchorPane
        if (dstoa == null || dstoa.isEmpty()) {
            return;
        }

        // Khởi tạo tọa độ x ban đầu cho các AnchorPane
        double x = 82.0;
        double y = 6.0;
        double width = 200.0;
        double height = 70.0;
        double spacing = 5.0;

        // Tạo AnchorPane cho từng Toa
        for (Toa toa : dstoa) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(width, height);
            anchorPane.setLayoutX(x);
            anchorPane.setLayoutY(y);

            // Tạo Rectangle màu trắng có kích thước y hệt AnchorPane
            Rectangle whiteRect = new Rectangle(width, height);
            whiteRect.setFill(javafx.scene.paint.Color.WHITE); // Đặt màu trắng

            // Tạo Rectangle màu #ccdaf5, kích thước 200x15, tại tọa độ (0, 0)
            Rectangle blueRect = new Rectangle(200.0, 15.0);
            blueRect.setFill(javafx.scene.paint.Color.web("#ccdaf5")); // Màu mặc định
            blueRect.setLayoutX(0.0);
            blueRect.setLayoutY(0.0);

            // Thêm cả hai Rectangle vào AnchorPane (whiteRect trước, blueRect sau để nằm trên)
            anchorPane.getChildren().addAll(whiteRect, blueRect);

            // Thêm hiệu ứng hover: đổi con trỏ thành hình bàn tay khi di chuột vào
            anchorPane.setOnMouseEntered(event -> anchorPane.setCursor(Cursor.HAND));
            anchorPane.setOnMouseExited(event -> anchorPane.setCursor(Cursor.DEFAULT));

            // Thêm sự kiện click cho AnchorPane
            anchorPane.setOnMouseClicked(event -> {
            	btnChonTatCa.setDisable(false);
                toaDangChon = toa;
                ChoNgoi_DAO choNgoi_DAO = new ChoNgoi_DAO();
                danhSachChoNgoi = choNgoi_DAO.timChoNgoiTheoMaToaVaMaChuyenTau(toaDangChon.getMaToa(), chuyenTauDangChon.getMaChuyenTau());
                Tau_DAO tau_DAO = new Tau_DAO();
                Tau tauDangChon = tau_DAO.timTauTheoMa(chuyenTauDangChon.getMaTau());
                if(tauDangChon.getLoaiTau() == LoaiTau.SE) {
                	if(toaDangChon.getLoaiToa() == LoaiToa.giuongNamDieuHoa) {
                		taoChoNgoi7x5(danhSachChoNgoi);
                	}else {
                		taoChoNgoi5x10(danhSachChoNgoi);
                	}
                }else {
                	if(toaDangChon.getLoaiToa() == LoaiToa.gheCungDieuHoa) {
                		taoChoNgoi5x10(danhSachChoNgoi);
                	}else {
                		taoChoNgoi4x10(danhSachChoNgoi);
                	}
                }
                
                
                // Nếu có AnchorPane Toa đã chọn trước đó, đổi màu của nó về #ccdaf5
                if (selectedToaAnchorPane != null && selectedToaAnchorPane != anchorPane) {
                    Rectangle previousBlueRect = (Rectangle) selectedToaAnchorPane.getChildren().get(1); // Lấy blueRect (thứ 2 trong danh sách children)
                    previousBlueRect.setFill(javafx.scene.paint.Color.web("#ccdaf5"));
                    System.out.println("Đã đổi màu về #ccdaf5 cho AnchorPane Toa trước đó");
                }

                // Đổi màu của blueRect trong AnchorPane hiện tại sang #2e7d32
                blueRect.setFill(javafx.scene.paint.Color.web("#2e7d32"));
                System.out.println("Đã đổi màu thành #2e7d32 cho AnchorPane Toa hiện tại");

                // Cập nhật AnchorPane Toa được chọn
                selectedToaAnchorPane = anchorPane;

                // Vô hiệu hóa các nút khi chọn toa mới
                btnBoChonTatCa.setDisable(true);
                btnThemVe.setDisable(true);
            });
            
            String tenToa = "Toa: " + toa.getTenToa(); 
            Label lblTenToa = new Label(tenToa);
            lblTenToa.setLayoutX(14);
            lblTenToa.setLayoutY(19);
            lblTenToa.setFont(Font.font("Tahoma"));
            anchorPane.getChildren().add(lblTenToa);
            
            String loaiToa = "";
            if(toa.getLoaiToa().equals(LoaiToa.gheCungDieuHoa)) {
                loaiToa = "Ghế cứng điều hòa";
            } else if(toa.getLoaiToa().equals(LoaiToa.ngoiMemDieuHoa)) {
                loaiToa = "Ngồi mềm điều hòa";
            } else {
                loaiToa = "Giường nằm điều hòa";
            }
            Label lblLoaiToa = new Label(loaiToa);
            lblLoaiToa.setFont(Font.font("Tahoma"));
            lblLoaiToa.setLayoutX(14);
            lblLoaiToa.setLayoutY(37);
            anchorPane.getChildren().add(lblLoaiToa);
            
            ChoNgoi_DAO choNgoi_DAO = new ChoNgoi_DAO();
            List<ChoNgoi> tinhGia = choNgoi_DAO.timChoNgoiTheoMaToaVaMaChuyenTau(toa.getMaToa(), chuyenTauDangChon.getMaChuyenTau());
            BigDecimal giaCho = tinhGia.get(0).getGiaCho();
            Label lblGiaCho = new Label(giaCho+ " vnđ");
            lblGiaCho.setFont(Font.font("Tahoma"));
            lblGiaCho.setLayoutX(14);
            lblGiaCho.setLayoutY(53);
            anchorPane.getChildren().add(lblGiaCho);
            
            int soCho = choNgoi_DAO.demSoChoNgoiChuaDat(chuyenTauDangChon.getMaChuyenTau(), toa.getMaToa());
            Label lblSoCho = new Label("Còn: " + soCho + " chỗ");
            lblSoCho.setFont(Font.font("Tahoma"));
            lblSoCho.setLayoutX(119);
            lblSoCho.setLayoutY(18);
            anchorPane.getChildren().add(lblSoCho);
            
            // Thêm AnchorPane vào pnToa
            pnToa.getChildren().add(anchorPane);

            // Cập nhật tọa độ x cho AnchorPane tiếp theo
            x += width + spacing;
        }
    }
    
    private Toa toaDangChon = null;
    
    @FXML
    private AnchorPane pnChoNgoi;
    
    @FXML
    private Button btnChonTatCa;
    
    @FXML
    private Button btnBoChonTatCa;
    
    @FXML
    private CheckBox ckcChonTheoDay;
    
    private List<ChoNgoi> danhSachChoNgoi = null;
    
    private void taoChoNgoi7x5(List<ChoNgoi> danhSachChoNgoi) {
        pnChoNgoi.getChildren().clear(); // Xóa các node cũ
        selectedChoNgoiAnchorPanes.clear(); // Reset danh sách chỗ ngồi được chọn
        chonTheoDayCount = 0;
        chonTheoDayChoNgois.clear();

        // Kích thước và khoảng cách được tính toán để phù hợp với 370x160
        double width = 38.0; // Chiều rộng mỗi chỗ
        double height = 18.0; // Chiều cao mỗi chỗ
        double spacingX = 8.0; // Khoảng cách ngang
        double spacingY = 5.0; // Khoảng cách dọc
        double startX = 15.0; // Tọa độ X bắt đầu
        double startY = 10.0; // Tọa độ Y bắt đầu

        int rows = 7;
        int cols = 5;
        int choNgoiIndex = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (choNgoiIndex >= danhSachChoNgoi.size()) {
                    break; // Thoát nếu hết chỗ ngồi
                }
                ChoNgoi choNgoi = danhSachChoNgoi.get(choNgoiIndex);

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefSize(width, height);
                double x = startX + col * (width + spacingX);
                double y = startY + row * (height + spacingY);
                anchorPane.setLayoutX(x);
                anchorPane.setLayoutY(y);

                // Tạo hình chữ nhật đại diện chỗ ngồi
                Rectangle rect = new Rectangle(width, height);
                switch (choNgoi.getTrangThai()) {
                    case daDat:
                        rect.setFill(javafx.scene.paint.Color.web("#992b15")); // Đã đặt
                        break;
                    case dangDat:
                        rect.setFill(javafx.scene.paint.Color.web("#2e7d32")); // Đang đặt
                        break;
                    case chuaDat:
                        rect.setFill(javafx.scene.paint.Color.web("#ccdaf5")); // Chưa đặt
                        break;
                }
                rect.setStroke(javafx.scene.paint.Color.BLACK);
                rect.setStrokeWidth(1.0);

                // Label số ghế
                Label lblSoGhe = new Label(choNgoi.getTenChoNgoi());
                lblSoGhe.setLayoutX(5);
                lblSoGhe.setLayoutY(1);
                lblSoGhe.setFont(Font.font("Tahoma", 10));

                anchorPane.getChildren().addAll(rect, lblSoGhe);
                anchorPane.setUserData(choNgoi); // Lưu ChoNgoi vào userData

                // Hiệu ứng hover
                anchorPane.setOnMouseEntered(event -> anchorPane.setCursor(Cursor.HAND));
                anchorPane.setOnMouseExited(event -> anchorPane.setCursor(Cursor.DEFAULT));

                // Sự kiện click
                if (choNgoi.getTrangThai() == TrangThaiChoNgoi.chuaDat) { // Chỉ cho phép chọn chỗ chưa đặt
                    anchorPane.setOnMouseClicked(event -> xuLyChonChoNgoi(anchorPane, choNgoi));
                }

                pnChoNgoi.getChildren().add(anchorPane);
                choNgoiIndex++;
            }
        }

        // Cập nhật trạng thái các nút
        capNhatTrangThaiNut();
    }
    
    private void taoChoNgoi5x10(List<ChoNgoi> danhSachChoNgoi) {
        pnChoNgoi.getChildren().clear(); // Xóa các node cũ
        selectedChoNgoiAnchorPanes.clear(); // Reset danh sách chỗ ngồi được chọn
        chonTheoDayCount = 0;
        chonTheoDayChoNgois.clear();

        // Kích thước và khoảng cách được tính toán để phù hợp với 370x160
        double width = 30.0; // Chiều rộng mỗi chỗ
        double height = 22.0; // Chiều cao mỗi chỗ
        double spacingX = 6.0; // Khoảng cách ngang
        double spacingY = 6.0; // Khoảng cách dọc
        double startX = 10.0; // Tọa độ X bắt đầu
        double startY = 15.0; // Tọa độ Y bắt đầu

        int rows = 5;
        int cols = 10;
        int choNgoiIndex = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (choNgoiIndex >= danhSachChoNgoi.size()) {
                    break; // Thoát nếu hết chỗ ngồi
                }
                ChoNgoi choNgoi = danhSachChoNgoi.get(choNgoiIndex);

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefSize(width, height);
                double x = startX + col * (width + spacingX);
                double y = startY + row * (height + spacingY);
                anchorPane.setLayoutX(x);
                anchorPane.setLayoutY(y);

                // Tạo hình chữ nhật đại diện chỗ ngồi
                Rectangle rect = new Rectangle(width, height);
                switch (choNgoi.getTrangThai()) {
                    case daDat:
                        rect.setFill(javafx.scene.paint.Color.web("#992b15")); // Đã đặt
                        break;
                    case dangDat:
                        rect.setFill(javafx.scene.paint.Color.web("#2e7d32")); // Đang đặt
                        break;
                    case chuaDat:
                        rect.setFill(javafx.scene.paint.Color.web("#ccdaf5")); // Chưa đặt
                        break;
                }
                rect.setStroke(javafx.scene.paint.Color.BLACK);
                rect.setStrokeWidth(1.0);

                // Label số ghế
                Label lblSoGhe = new Label(choNgoi.getTenChoNgoi());
                lblSoGhe.setLayoutX(5);
                lblSoGhe.setLayoutY(3);
                lblSoGhe.setFont(Font.font("Tahoma", 9));

                anchorPane.getChildren().addAll(rect, lblSoGhe);
                anchorPane.setUserData(choNgoi); // Lưu ChoNgoi vào userData

                // Hiệu ứng hover
                anchorPane.setOnMouseEntered(event -> anchorPane.setCursor(Cursor.HAND));
                anchorPane.setOnMouseExited(event -> anchorPane.setCursor(Cursor.DEFAULT));

                // Sự kiện click
                if (choNgoi.getTrangThai() == TrangThaiChoNgoi.chuaDat) { // Chỉ cho phép chọn chỗ chưa đặt
                    anchorPane.setOnMouseClicked(event -> xuLyChonChoNgoi(anchorPane, choNgoi));
                }

                pnChoNgoi.getChildren().add(anchorPane);
                choNgoiIndex++;
            }
        }

        // Cập nhật trạng thái các nút
        capNhatTrangThaiNut();
    }
    
    private void taoChoNgoi4x10(List<ChoNgoi> danhSachChoNgoi) {
        pnChoNgoi.getChildren().clear(); // Xóa các node cũ
        selectedChoNgoiAnchorPanes.clear(); // Reset danh sách chỗ ngồi được chọn
        chonTheoDayCount = 0;
        chonTheoDayChoNgois.clear();

        // Kích thước và khoảng cách được tính toán để phù hợp với 370x160
        double width = 32.0; // Chiều rộng mỗi chỗ
        double height = 28.0; // Chiều cao mỗi chỗ
        double spacingX = 5.0; // Khoảng cách ngang
        double spacingY = 6.0; // Khoảng cách dọc
        double startX = 10.0; // Tọa độ X bắt đầu
        double startY = 15.0; // Tọa độ Y bắt đầu

        int rows = 4;
        int cols = 10;
        int choNgoiIndex = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (choNgoiIndex >= danhSachChoNgoi.size()) {
                    break; // Thoát nếu hết chỗ ngồi
                }
                ChoNgoi choNgoi = danhSachChoNgoi.get(choNgoiIndex);

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefSize(width, height);
                double x = startX + col * (width + spacingX);
                double y = startY + row * (height + spacingY);
                anchorPane.setLayoutX(x);
                anchorPane.setLayoutY(y);

                // Tạo hình chữ nhật đại diện chỗ ngồi
                Rectangle rect = new Rectangle(width, height);
                switch (choNgoi.getTrangThai()) {
                    case daDat:
                        rect.setFill(javafx.scene.paint.Color.web("#992b15")); // Đã đặt
                        break;
                    case dangDat:
                        rect.setFill(javafx.scene.paint.Color.web("#2e7d32")); // Đang đặt
                        break;
                    case chuaDat:
                        rect.setFill(javafx.scene.paint.Color.web("#ccdaf5")); // Chưa đặt
                        break;
                }
                rect.setStroke(javafx.scene.paint.Color.BLACK);
                rect.setStrokeWidth(1.0);

                // Label số ghế
                Label lblSoGhe = new Label(choNgoi.getTenChoNgoi());
                lblSoGhe.setLayoutX(5);
                lblSoGhe.setLayoutY(5);
                lblSoGhe.setFont(Font.font("Tahoma", 9));

                anchorPane.getChildren().addAll(rect, lblSoGhe);
                anchorPane.setUserData(choNgoi); // Lưu ChoNgoi vào userData

                // Hiệu ứng hover
                anchorPane.setOnMouseEntered(event -> anchorPane.setCursor(Cursor.HAND));
                anchorPane.setOnMouseExited(event -> anchorPane.setCursor(Cursor.DEFAULT));

                // Sự kiện click
                if (choNgoi.getTrangThai() == TrangThaiChoNgoi.chuaDat) { // Chỉ cho phép chọn chỗ chưa đặt
                    anchorPane.setOnMouseClicked(event -> xuLyChonChoNgoi(anchorPane, choNgoi));
                }

                pnChoNgoi.getChildren().add(anchorPane);
                choNgoiIndex++;
            }
        }

        // Cập nhật trạng thái các nút
        capNhatTrangThaiNut();
    }
    
    private int extractSoCho(String tenCho) {
        // Tách số từ cuối chuỗi, ví dụ "A12" -> 12
        try {
            return Integer.parseInt(tenCho.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return -1; // nếu lỗi
        }
    }

    
    private void xuLyChonChoNgoi(AnchorPane anchorPane, ChoNgoi choNgoi) {
        // Kiểm tra nếu chỗ ngồi đã được xác nhận
        boolean daXacNhan = danhSachVeXacNhan.stream()
            .anyMatch(ve -> ve.getMaChoNgoi().equals(choNgoi.getTenChoNgoi()));
        if (daXacNhan) {
            showWarningAlert("Chỗ ngồi này đã được xác nhận!", "image/canhBao.png");
            return;
        }

        Rectangle rect = (Rectangle) anchorPane.getChildren().get(0);

        if (ckcChonTheoDay.isSelected()) {
            // Chọn theo dãy
            if (chonTheoDayCount < 2) { // Chỉ cho phép chọn tối đa 2 chỗ
                chonTheoDayChoNgois.add(choNgoi);
                VeTam veTam = new VeTam(choNgoi.getTenChoNgoi(), choNgoi.getGiaCho(), cboLoaiKhachHang.getValue());
                danhSachVeTam.add(veTam); // Thêm vào danh sách đang chọn
                chonTheoDayCount++;
                rect.setFill(javafx.scene.paint.Color.web("#ffa500")); // Màu khi chọn
                selectedChoNgoiAnchorPanes.add(anchorPane);
            }

            if (chonTheoDayCount == 2) {
                // Sắp xếp lại danh sách theo thứ tự tên chỗ ngồi
                chonTheoDayChoNgois.sort((c1, c2) -> {
                    return extractSoCho(c1.getTenChoNgoi()) - extractSoCho(c2.getTenChoNgoi());
                });

                ChoNgoi start = chonTheoDayChoNgois.get(0);
                ChoNgoi end = chonTheoDayChoNgois.get(1);

                int soStart = extractSoCho(start.getTenChoNgoi());
                int soEnd = extractSoCho(end.getTenChoNgoi());

                // Chọn toàn bộ dãy trong khoảng
                for (Node node : pnChoNgoi.getChildren()) {
                    if (node instanceof AnchorPane) {
                        AnchorPane pane = (AnchorPane) node;
                        Label lbl = (Label) pane.getChildren().get(1); // lấy tên ghế
                        int so = extractSoCho(lbl.getText());
                        if (so >= soStart && so <= soEnd) {
                            ChoNgoi cg = danhSachChoNgoi.stream()
                                .filter(cn -> cn.getTenChoNgoi().equals(lbl.getText()))
                                .findFirst().orElse(null);
                            if (cg != null && cg.getTrangThai() == TrangThaiChoNgoi.chuaDat) {
                                // Kiểm tra nếu chỗ ngồi chưa được xác nhận
                                boolean chuaXacNhan = danhSachVeXacNhan.stream()
                                    .noneMatch(ve -> ve.getMaChoNgoi().equals(cg.getTenChoNgoi()));
                                if (chuaXacNhan) {
                                    Rectangle r = (Rectangle) pane.getChildren().get(0);
                                    r.setFill(javafx.scene.paint.Color.web("#ffa500"));
                                    pane.setUserData(cg);
                                    if (!selectedChoNgoiAnchorPanes.contains(pane)) {
                                        selectedChoNgoiAnchorPanes.add(pane);
                                        VeTam veTam = new VeTam(cg.getTenChoNgoi(), cg.getGiaCho(), cboLoaiKhachHang.getValue());
                                        danhSachVeTam.add(veTam);
                                    }
                                }
                            }
                        }
                    }
                }

                // Reset tạm
                chonTheoDayCount = 0;
                chonTheoDayChoNgois.clear();
            }

        } else {
            // Chọn từng chỗ
            if (selectedChoNgoiAnchorPanes.contains(anchorPane)) {
                // Bỏ chọn
                rect.setFill(javafx.scene.paint.Color.web("#ccdaf5")); // Trở về màu chưa đặt
                selectedChoNgoiAnchorPanes.remove(anchorPane);
                // Xóa VeTam tương ứng
                danhSachVeTam.removeIf(ve -> ve.getMaChoNgoi().equals(choNgoi.getTenChoNgoi()));
            } else {
                // Chọn
                rect.setFill(javafx.scene.paint.Color.web("#ffa500")); // Màu khi chọn
                selectedChoNgoiAnchorPanes.add(anchorPane);
                VeTam veTam = new VeTam(choNgoi.getTenChoNgoi(), choNgoi.getGiaCho(), cboLoaiKhachHang.getValue());
                danhSachVeTam.add(veTam); // Thêm vào danh sách đang chọn
            }
        }

        // Cập nhật trạng thái các nút
        capNhatTrangThaiNut();

        // Cập nhật giá tiền tạm tính
        capNhatGiaTamTinh();

        // Kiểm tra và hiển thị danh sách vé tạm đang chọn
        kiemTraDanhSachVeTam();
    }
    
    private void kiemTraDanhSachVeTam() {
        System.out.println("Danh sách vé tạm đang chọn:");
        if (danhSachVeTam.isEmpty()) {
            System.out.println("  Không có vé tạm nào được chọn.");
        } else {
            for (VeTam veTam : danhSachVeTam) {
                System.out.println("  - Chỗ: " + veTam.getMaChoNgoi() +
                                   ", Loại khách hàng: " + veTam.getLoaiKhachHang() +
                                   ", Giá tiền: " + veTam.getGiaTien() +
                                   ", Toa: " + toaDangChon.getTenToa() +
                                   ", Chuyến tàu: " + chuyenTauDangChon.getMaChuyenTau());
            }
        }

        System.out.println("Danh sách vé đã xác nhận:");
        if (danhSachVeXacNhan.isEmpty()) {
            System.out.println("  Không có vé nào được xác nhận.");
        } else {
            for (VeTam veTam : danhSachVeXacNhan) {
                System.out.println("  - Chỗ: " + veTam.getMaChoNgoi() +
                                   ", Loại khách hàng: " + veTam.getLoaiKhachHang() +
                                   ", Giá tiền: " + veTam.getGiaTien() +
                                   ", Toa: " + toaDangChon.getTenToa() +
                                   ", Chuyến tàu: " + chuyenTauDangChon.getMaChuyenTau());
            }
        }
    }
    
 // Phương thức phụ trợ để kiểm tra và hiển thị nội dung của choNgoiDangChon
    private void kiemTraChoNgoiDangChon() {
        System.out.println("Danh sách chỗ ngồi đang chọn:");
        if (choNgoiDangChon.isEmpty()) {
            System.out.println("  Không có chỗ ngồi nào được chọn.");
        } else {
            for (ChoNgoi choNgoi : choNgoiDangChon) {
                System.out.println("  - Chỗ: " + choNgoi.getTenChoNgoi() +
                                   ", Toa: " + toaDangChon.getTenToa() +
                                   ", Chuyến tàu: " + chuyenTauDangChon.getMaChuyenTau());
            }
        }
    }

    private void capNhatTrangThaiNut() {
        // Kích hoạt nút "Chọn tất cả" nếu có ít nhất một chỗ ngồi chưa đặt
        boolean coChoChuaDat = danhSachChoNgoi != null && 
                              danhSachChoNgoi.stream().anyMatch(cho -> cho.getTrangThai() == TrangThaiChoNgoi.chuaDat);
        btnChonTatCa.setDisable(!coChoChuaDat);
        
        // Kích hoạt nút "Bỏ chọn tất cả" nếu có chỗ đang được chọn
        btnBoChonTatCa.setDisable(selectedChoNgoiAnchorPanes.isEmpty());
        
        // Kích hoạt nút "Thêm vé" nếu có chỗ đang được chọn
        btnThemVe.setDisable(selectedChoNgoiAnchorPanes.isEmpty());
    }
    
    private List<ChoNgoi> choNgoiDangChon = new ArrayList<ChoNgoi>();
    
    @FXML
    private void btnChonTatCaClicked() {
        if (danhSachChoNgoi == null || danhSachChoNgoi.isEmpty()) {
            showWarningAlert("Không có chỗ ngồi nào để chọn!", "image/canhBao.png");
            return;
        }

        // Xóa tất cả các chỗ đã chọn trước đó
        selectedChoNgoiAnchorPanes.clear();
        danhSachVeTam.clear();

        // Duyệt qua tất cả các chỗ ngồi trong pnChoNgoi
        for (Node node : pnChoNgoi.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane choPane = (AnchorPane) node;
                ChoNgoi choNgoi = (ChoNgoi) choPane.getUserData();
                
                // Nếu chưa có userData, tìm trong danhSachChoNgoi
                if (choNgoi == null) {
                    Label lbl = (Label) choPane.getChildren().get(1); // Lấy label số ghế
                    for (ChoNgoi cn : danhSachChoNgoi) {
                        if (cn.getTenChoNgoi().equals(lbl.getText())) {
                            choNgoi = cn;
                            choPane.setUserData(choNgoi); // Lưu vào userData
                            break;
                        }
                    }
                }

                // Chỉ chọn nếu chỗ ngồi chưa đặt
                if (choNgoi != null && choNgoi.getTrangThai() == TrangThaiChoNgoi.chuaDat) {
                    Rectangle rect = (Rectangle) choPane.getChildren().get(0);
                    rect.setFill(javafx.scene.paint.Color.web("#FFA500")); // Màu đang chọn
                    selectedChoNgoiAnchorPanes.add(choPane);
                    VeTam veTam = new VeTam(choNgoi.getTenChoNgoi(), choNgoi.getGiaCho(), cboLoaiKhachHang.getValue());
                    danhSachVeTam.add(veTam);
                }
            }
        }

        // Cập nhật trạng thái các nút
        capNhatTrangThaiNut();
        
        // Cập nhật giá tiền tạm tính
        capNhatGiaTamTinh();
        
        // Log để kiểm tra
        System.out.println("Đã chọn tất cả chỗ ngồi chưa đặt");
        kiemTraDanhSachVeTam();
    }
    
    @FXML
    private void btnBoChonTatCaClicked() {
        if (selectedChoNgoiAnchorPanes.isEmpty() && danhSachVeXacNhan.isEmpty()) {
            showWarningAlert("Không có chỗ ngồi hoặc vé nào được chọn để bỏ!", "image/canhBao.png");
            return;
        }

        // Duyệt qua tất cả các chỗ đang được chọn
        for (AnchorPane choPane : selectedChoNgoiAnchorPanes) {
            ChoNgoi choNgoi = (ChoNgoi) choPane.getUserData();
            if (choNgoi != null) {
                Rectangle rect = (Rectangle) choPane.getChildren().get(0);
                
                // Đặt lại màu theo trạng thái ban đầu
                switch (choNgoi.getTrangThai()) {
                    case chuaDat:
                        rect.setFill(javafx.scene.paint.Color.web("#ccdaf5")); // Chưa đặt
                        break;
                    case dangDat:
                        rect.setFill(javafx.scene.paint.Color.web("#2e7d32")); // Đang đặt
                        break;
                    case daDat:
                        rect.setFill(javafx.scene.paint.Color.web("#992b15")); // Đã đặt
                        break;
                }
            }
        }

        // Xóa tất cả các chỗ đã chọn và vé tạm
        selectedChoNgoiAnchorPanes.clear();
        danhSachVeTam.clear();
        chonTheoDayCount = 0;
        chonTheoDayChoNgois.clear();

        // Xóa tất cả vé xác nhận và cập nhật pnGioVe
        danhSachVeXacNhan.clear();
        pnGioVe.getChildren().clear();
        pnGioVe.setPrefHeight(0);

        // Cập nhật trạng thái các nút
        capNhatTrangThaiNut();
        
        // Cập nhật giá tiền tạm tính
        capNhatGiaTamTinh();
        
        // Log để kiểm tra
        System.out.println("Đã bỏ chọn tất cả chỗ ngồi và vé xác nhận");
        kiemTraDanhSachVeTam();
    }
    
    @FXML
    private TextField txtGiaTamTinh;
    
    private BigDecimal tinhTongGiaTien() {
        BigDecimal giaTien = BigDecimal.ZERO;
        for (VeTam veTam : danhSachVeTam) {
            giaTien = giaTien.add(veTam.getGiaTien());
        }
        return giaTien;
    }
    
    private void capNhatGiaTamTinh() {
        BigDecimal tongGia = BigDecimal.ZERO;
        // Tính tổng giá từ danhSachVeXacNhan
        for (VeTam veTam : danhSachVeXacNhan) {
            tongGia = tongGia.add(veTam.getGiaTien());
        }
        // Tính tổng giá từ danhSachVeTam
        for (VeTam veTam : danhSachVeTam) {
            tongGia = tongGia.add(veTam.getGiaTien());
        }
        // Định dạng giá tiền với dấu phẩy ngăn cách hàng nghìn
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String giaFormatted = decimalFormat.format(tongGia) + " VNĐ";
        txtGiaTamTinh.setText(giaFormatted);
    }
    
    private List<VeTam> danhSachVeTam = new ArrayList<VeTam>();
    
    @FXML
    private ComboBox<LoaiKhachHang> cboLoaiKhachHang;
    
    @FXML
    private AnchorPane pnGioVe;
    //Kích thước của pnGioVe là 470 rộng và chiều dài tự phát sinh
    
    @FXML
    private void btnThemVeClicked() {
        // Kiểm tra nếu danhSachVeTam rỗng
        if (danhSachVeTam.isEmpty()) {
            showWarningAlert("Không có vé tạm nào được chọn để thêm!", "image/canhBao.png");
            return;
        }
        
     // In mã chỗ ngồi của các vé tạm trước khi thêm
        System.out.println("Thêm các vé tạm vào giỏ vé:");
        for (VeTam veTam : danhSachVeTam) {
            System.out.println(" - Mã chỗ ngồi: " + veTam.getMaChoNgoi());
        }

        // Thêm các vé tạm vào danhSachVeXacNhan
        danhSachVeXacNhan.addAll(danhSachVeTam);
        System.out.println("Đã thêm " + danhSachVeTam.size() + " vé vào danhSachVeXacNhan");

        // Xóa các node cũ trong pnGioVe
        pnGioVe.getChildren().clear();
        System.out.println("Đã xóa các node cũ trong pnGioVe");

        // Tính chiều cao cho pnGioVe dựa trên danhSachVeXacNhan
        int soVeXacNhan = danhSachVeXacNhan.size();
        double chieuCao = 10 + (soVeXacNhan * 200) + (Math.max(0, soVeXacNhan - 1) * 10) + 10;
        pnGioVe.setPrefHeight(chieuCao);
        System.out.println("Đã đặt chiều cao pnGioVe: " + chieuCao);

        // Lấy thông tin tàu
        Tau_DAO tauDAO = new Tau_DAO();
        Tau tau = tauDAO.timTauTheoMa(chuyenTauDangChon.getMaTau());

        // Tạo các AnchorPane cho tất cả vé trong danhSachVeXacNhan
        double y = 10.0; // Tọa độ Y bắt đầu
        for (VeTam veTam : danhSachVeXacNhan) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(450, 200);
            anchorPane.setLayoutX(10); // Căn giữa trong pnGioVe (470 - 450)/2 = 10
            anchorPane.setLayoutY(y);

            // Tạo Rectangle màu trắng
            Rectangle rect = new Rectangle(450, 200);
            rect.setFill(javafx.scene.paint.Color.WHITE);
            rect.setStroke(javafx.scene.paint.Color.BLACK);
            rect.setStrokeWidth(1.0);

            // Tạo Label cho cột trái
            String loaiKhachHang = "";
            switch (veTam.getLoaiKhachHang()) {
                case treEm:
                    loaiKhachHang = "Trẻ em";
                    break;
                case nguoiLon:
                    loaiKhachHang = "Người lớn";
                    break;
                case nguoiCaoTuoi:
                    loaiKhachHang = "Người cao tuổi";
                    break;
                case sinhVien:
                    loaiKhachHang = "Sinh viên";
                    break;
            }
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String giaVe = decimalFormat.format(veTam.getGiaTien()) + " VNĐ";
            String gaDi = cboGaDi.getValue() != null ? cboGaDi.getValue().getTenGa() : "N/A";
            String ngayKhoiHanh = dpNgayDi.getValue() != null ? dpNgayDi.getValue().toString() : "N/A";
            String tenTau = (tau != null) ? tau.getKyHieuTau() : "N/A";
            String tenToa = toaDangChon != null ? toaDangChon.getTenToa() : "N/A";

            String thongTinTrai = "Ga đi: " + gaDi +
                                 "\nNgày khởi hành: " + ngayKhoiHanh +
                                 "\nTên tàu: " + tenTau +
                                 "\nTên toa: " + tenToa +
                                 "\nLoại khách hàng: " + loaiKhachHang +
                                 "\nGiá vé: " + giaVe +
                                 "\nChỗ ngồi: " + veTam.getMaChoNgoi();
            Label labelTrai = new Label(thongTinTrai);
            labelTrai.setLayoutX(10);
            labelTrai.setLayoutY(10);
            labelTrai.setFont(Font.font("Tahoma", 11));

            // Tạo Label cho cột phải
            String gaDen = cboGaDen.getValue() != null ? cboGaDen.getValue().getTenGa() : "N/A";
            String gioKhoiHanh = chuyenTauDangChon.getGioKhoiHanh() != null 
                ? chuyenTauDangChon.getGioKhoiHanh().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) 
                : "N/A";
            String thongTinPhai = "Ga đến: " + gaDen +
                                 "\nGiờ khởi hành: " + gioKhoiHanh;
            Label labelPhai = new Label(thongTinPhai);
            labelPhai.setLayoutX(300);
            labelPhai.setLayoutY(10);
            labelPhai.setFont(Font.font("Tahoma", 11));

         // Tạo nút Xóa ở góc dưới bên phải
            Button btnXoa = new Button("Xóa");
            btnXoa.setLayoutX(400); // Cách mép phải 10px
            btnXoa.setLayoutY(165); // Cách mép dưới 10px (200 - 25 - 10)
            btnXoa.setPrefSize(40, 25);
            btnXoa.setOnAction(e -> {
                // In mã chỗ ngồi của vé trước khi xóa
                System.out.println("Xóa vé khỏi giỏ vé:");
                System.out.println(" - Mã chỗ ngồi: " + veTam.getMaChoNgoi());

                // Xóa vé khỏi danhSachVeXacNhan
                danhSachVeXacNhan.remove(veTam);
                // Xóa AnchorPane tương ứng khỏi pnGioVe
                pnGioVe.getChildren().remove(anchorPane);
                // Cập nhật tọa độ Y cho các AnchorPane còn lại
                double newY = 10.0;
                for (Node node : pnGioVe.getChildren()) {
                    if (node instanceof AnchorPane) {
                        node.setLayoutY(newY);
                        newY += 200 + 10;
                    }
                }
                // Cập nhật chiều cao pnGioVe
                double newChieuCao = 10 + (danhSachVeXacNhan.size() * 200) + (Math.max(0, danhSachVeXacNhan.size() - 1) * 10) + 10;
                pnGioVe.setPrefHeight(newChieuCao);
                // Cập nhật giá tạm tính
                capNhatGiaTamTinh();
                // Cập nhật trạng thái nút
                capNhatTrangThaiNut();
                // Log
                System.out.println("Đã xóa vé: " + veTam.getMaChoNgoi());
                kiemTraDanhSachVeTam();
            });

            // Thêm Rectangle, Label và Button vào AnchorPane
            anchorPane.getChildren().addAll(rect, labelTrai, labelPhai, btnXoa);

            // Thêm AnchorPane vào pnGioVe
            pnGioVe.getChildren().add(anchorPane);
            System.out.println("Đã thêm AnchorPane cho vé xác nhận: " + veTam.getMaChoNgoi());

            // Cập nhật tọa độ Y cho AnchorPane tiếp theo
            y += 200 + 10;
        }

        // Xóa các chỗ ngồi đã chọn trong giao diện
        for (AnchorPane choPane : selectedChoNgoiAnchorPanes) {
            Rectangle rect = (Rectangle) choPane.getChildren().get(0);
            rect.setFill(javafx.scene.paint.Color.web("#2e7d32")); // Đặt màu "đang đặt"
        }

        // Reset danhSachVeTam và các biến liên quan
        selectedChoNgoiAnchorPanes.clear();
        danhSachVeTam.clear();
        chonTheoDayCount = 0;
        chonTheoDayChoNgois.clear();

        // Cập nhật trạng thái các nút
        capNhatTrangThaiNut();

        // Cập nhật giá tiền tạm tính
        capNhatGiaTamTinh();

        // Log để kiểm tra
        System.out.println("Tổng số vé xác nhận: " + soVeXacNhan);
    }
    
    private List<VeTam> danhSachVeXacNhan = new ArrayList<>();
}