package gui;

import java.util.Arrays;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.Tau_DAO;
import dao.Toa_DAO;
import entity.NhanVien;
import entity.Tau;
import entity.Tau.LoaiTau;
import entity.Tau.TrangThaiTau;
import entity.Toa;
import javafx.beans.property.SimpleStringProperty;
import dao.Ga_DAO;
import entity.Ga;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuanLyChuyenTau_GUI_Controller {
    public String maNhanVien;

    @FXML
    private AnchorPane pnQuanLyChuyenTau;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuTroGiup;
    @FXML
    private MenuItem itemThongTinPhanMem;
    @FXML
    private MenuItem itemHuongDanSuDung;
    @FXML
    private Label lblMaNhanVien;
    @FXML
    private Label lblTenNhanVien;
    @FXML
    private Label lblChucVu;
    @FXML
    private ImageView imgAnhNhanVien;
    
    // Các thành phần quản lý chuyến tàu
    @FXML
    private TextField txtMaChuyenTau;
    @FXML
    private TextField txtTenChuyenTau;
    @FXML
    private TextField txtGaDi;
    @FXML
    private TextField txtGaDen;
    @FXML
    private TextField txtThoiGianDi;
    @FXML
    private TextField txtThoiGianDen;
    @FXML
    private TextField txtGiaVe;
    
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
    
    @FXML private AnchorPane pnHome;
    
    @FXML
    private Label lblQuanLyBanVe;
    
    @FXML
    private Label lblTrangChu;
    
    @FXML
    public void initialize() {
    	
    	// Trong phương thức initialize()
    	cboLoaiToa.setItems(FXCollections.observableArrayList(
    	    java.util.Arrays.stream(Toa.LoaiToa.values())
    	        .map(Toa.LoaiToa::getDisplayName)
    	        .collect(java.util.stream.Collectors.toList())
    	));
    	cboTrangThaiToa.setItems(FXCollections.observableArrayList(
    	    java.util.Arrays.stream(Toa.TrangThaiToa.values())
    	        .map(Toa.TrangThaiToa::getDisplayName)
    	        .collect(java.util.stream.Collectors.toList())
    	));
        
    	// Khởi tạo thông tin nhân viên
        lblMaNhanVien.setText("Mã nhân viên: Chưa tải");
        lblTenNhanVien.setText("Tên nhân viên: Chưa tải");
        lblChucVu.setText("Chức vụ: Chưa tải");
        
        txtMaTau.setDisable(true);
        txtMaTau.setEditable(false);
        
        cboTrangThaiTau.setDisable(true);
        cboTrangThaiTau.setEditable(false);
        
        // Thêm tất cả giá trị enum vào ComboBox
        cboLoaiTau.setItems(FXCollections.observableArrayList(LoaiTau.values()));
        cboLoaiTau.setValue(LoaiTau.TN);
        
        cboTrangThaiTau.setItems(FXCollections.observableArrayList(TrangThaiTau.values()));
        cboTrangThaiTau.setValue(TrangThaiTau.hoatDong);
        
     // Xử lý sự kiện thay đổi loại tàu
        cboLoaiTau.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                updateSoLuongToa(newValue);  // Gọi hàm cập nhật số toa
                // Cập nhật cboLoaiToa dựa trên loại tàu
                if (newValue == LoaiTau.TN) {
                    // Chỉ hiển thị "Ngồi mềm điều hòa" và "Ghế cứng điều hòa" cho tàu TN
                    ObservableList<String> loaiToaTN = FXCollections.observableArrayList(
                        Toa.LoaiToa.ngoiMemDieuHoa.getDisplayName(),
                        Toa.LoaiToa.gheCungDieuHoa.getDisplayName()
                    );
                    cboLoaiToa.setItems(loaiToaTN);
                } else {
                    // Hiển thị đầy đủ các loại toa cho các loại tàu khác (SE, DP)
                    cboLoaiToa.setItems(FXCollections.observableArrayList(
                        Arrays.stream(Toa.LoaiToa.values())
                            .map(Toa.LoaiToa::getDisplayName)
                            .collect(java.util.stream.Collectors.toList())
                    ));
                }
            }
        );

        // Khởi tạo giá trị ban đầu cho cboSoLuongToa
        updateSoLuongToa(cboLoaiTau.getValue());
        
     // Sự kiện khi nhấp vào một tàu trong TableView
        tbDanhSachTau.setOnMouseClicked(event -> {
            Tau tau = tbDanhSachTau.getSelectionModel().getSelectedItem();
            if (tau != null) {
                txtMaTau.setText(tau.getMaTau());
                txtTenTau.setText(tau.getTenTau());
                cboLoaiTau.setValue(tau.getLoaiTau());
                cboTrangThaiTau.setValue(tau.getTrangThaiTau());
                cboTrangThaiTau.setEditable(true);
                cboTrangThaiTau.setDisable(false);

                Toa_DAO toa_DAO = new Toa_DAO();
                try {
                    int soLuongToaHienTai = toa_DAO.laySoLuongToaTheoMaTau(tau.getMaTau());
                    updateSoLuongToaTheoTau(tau.getLoaiTau(), soLuongToaHienTai);
                    cboSoLuongToa.setValue(soLuongToaHienTai);

                    // Lấy danh sách toa và cập nhật cboToa
                    List<Toa> danhSachToa = toa_DAO.getToaByMaTau(tau.getMaTau());
                    ObservableList<String> tenToaList = FXCollections.observableArrayList(
                        danhSachToa.stream().map(Toa::getTenToa).collect(java.util.stream.Collectors.toList())
                    );
                    cboToa.setItems(tenToaList);
                    if (!tenToaList.isEmpty()) {
                        cboToa.setValue(tenToaList.get(0)); // Chọn toa đầu tiên mặc định
                        capNhatThongTinToa(danhSachToa, tenToaList.get(0));
                    } else {
                        cboLoaiToa.setValue(null);
                        cboTrangThaiToa.setValue(null);
                    }

                    // Cập nhật cboLoaiToa dựa trên loại tàu
                    if (tau.getLoaiTau() == LoaiTau.TN) {
                        // Chỉ hiển thị "Ngồi mềm điều hòa" và "Ghế cứng điều hòa" cho tàu TN
                        ObservableList<String> loaiToaTN = FXCollections.observableArrayList(
                            Toa.LoaiToa.ngoiMemDieuHoa.getDisplayName(),
                            Toa.LoaiToa.gheCungDieuHoa.getDisplayName()
                        );
                        cboLoaiToa.setItems(loaiToaTN);
                    } else {
                        // Hiển thị đầy đủ các loại toa cho các loại tàu khác (SE, DP)
                        cboLoaiToa.setItems(FXCollections.observableArrayList(
                            Arrays.stream(Toa.LoaiToa.values())
                                .map(Toa.LoaiToa::getDisplayName)
                                .collect(java.util.stream.Collectors.toList())
                        ));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showErrorAlert("Lỗi khi lấy thông tin toa: " + e.getMessage(), "image/loi.png");
                }
            }
        });
        
     // Listener cho cboToa để cập nhật cboLoaiToa và cboTrangThaiToa
        cboToa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !txtMaTau.getText().isEmpty()) {
                Toa_DAO toa_DAO = new Toa_DAO();
                try {
                    List<Toa> danhSachToa = toa_DAO.getToaByMaTau(txtMaTau.getText());
                    capNhatThongTinToa(danhSachToa, newValue);
                } catch (SQLException e) {
                    e.printStackTrace();
                    showErrorAlert("Lỗi khi cập nhật thông tin toa: " + e.getMessage(), "image/loi.png");
                }
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
    
    private void capNhatThongTinToa(List<Toa> danhSachToa, String tenToa) {
        Toa toaDuocChon = danhSachToa.stream()
            .filter(toa -> toa.getTenToa().equals(tenToa))
            .findFirst()
            .orElse(null);
        
        if (toaDuocChon != null) {
            cboLoaiToa.setValue(toaDuocChon.getLoaiToa().getDisplayName());
            cboTrangThaiToa.setValue(toaDuocChon.getTrangThai().getDisplayName());
        } else {
            cboLoaiToa.setValue(null);
            cboTrangThaiToa.setValue(null);
        }
    }
    
    private void updateSoLuongToaTheoTau(LoaiTau loaiTau, int soLuongToaHienTai) {
        ObservableList<Integer> soLuongToaList = FXCollections.observableArrayList();
        
        switch (loaiTau) {
            case SE -> {
                for (int i = Math.max(soLuongToaHienTai, 8); i <= 12; i++) {
                    soLuongToaList.add(i);
                }
            }
            case TN -> {
                for (int i = Math.max(soLuongToaHienTai, 4); i <= 6; i++) {
                    soLuongToaList.add(i);
                }
            }
            case DP -> {
                for (int i = Math.max(soLuongToaHienTai, 4); i <= 6; i++) {
                    soLuongToaList.add(i);
                }
            }
        }
        cboSoLuongToa.setItems(soLuongToaList);
    }

    private void updateSoLuongToa(LoaiTau loaiTau) {
        ObservableList<Integer> soLuongToaList = FXCollections.observableArrayList();
        
        switch (loaiTau) {
            case SE -> {
                for (int i = 8; i <= 12; i++) {
                    soLuongToaList.add(i);
                }
            }
            case TN -> {
                for (int i = 4; i <= 6; i++) {
                    soLuongToaList.add(i);
                }
            }
            case DP -> {
                for (int i = 4; i <= 6; i++) {
                    soLuongToaList.add(i);
                }
            }
        }
        cboSoLuongToa.setItems(soLuongToaList);
        cboSoLuongToa.setValue(soLuongToaList.get(0));
    }
    
    @FXML
    private ComboBox<Integer> cboSoLuongToa;
    
    // Phương thức để cập nhật thông tin nhân viên
    public void updateNhanVienInfo() {
        if (maNhanVien != null && !maNhanVien.isEmpty()) {
            NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
            NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien);
            if (nv != null) {
                lblMaNhanVien.setText("Mã NV: " + nv.getMaNhanVien());
                lblTenNhanVien.setText("Tên NV: " + nv.getTenNhanVien());
                lblChucVu.setText("Chức vụ: " + (nv.getChucVu().equals(NhanVien.ChucVu.banVe) ? "Bán vé" : "Quản lý"));
                
                File imageFile = new File(nv.getLinkAnh());
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imgAnhNhanVien.setImage(image);
                }
            } else {
                lblMaNhanVien.setText("Mã nhân viên: Không tìm thấy");
                lblTenNhanVien.setText("Tên nhân viên: Không tìm thấy");
                lblChucVu.setText("Chức vụ: Không xác định");
            }
        }
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        updateNhanVienInfo();
    }
    
    
    @FXML
	private TableView<Tau> tbDanhSachTau;
	
	@FXML
	private TableColumn<Tau, String> colStt; 
	
	@FXML 
	private TableColumn<Tau, String> colMaTau;
	
	@FXML 
	private TableColumn<Tau, String> colTenTau;
	
	@FXML 
	private TableColumn<Tau, String> colLoaiTau;
	
	@FXML
	private TableColumn<Tau, String> colTrangThaiTau;
	
	@FXML
	private TextField txtTimTenTau;
	
	@FXML
	private TextField txtMaTau;
	
	@FXML
	private TextField txtTenTau;
	
	@FXML
	private ComboBox<LoaiTau> cboLoaiTau;
	
	
	@FXML
	private ComboBox<TrangThaiTau> cboTrangThaiTau;

	@FXML
	private Button btnTimTau;

	@FXML
	private void btnTimTauClicked() {
	    String tenTau = txtTimTenTau.getText().toString();
    	if(tenTau.trim().equals("")) {
    		showWarningAlert("Bạn chưa nhập tên tàu để tìm!", "image/canhBao.png");
    		txtTimGa.requestFocus();
    	}else {
    		Tau_DAO tau_dao = new Tau_DAO();
    		List<Tau> dstau = tau_dao.timTauTheoTen(tenTau);
    		if(dstau.isEmpty()) {
    			showWarningAlert("Không tìm thấy tàu theo tên được nhập!", "image/canhBao.png");
    			txtTimTenTau.requestFocus();
    			txtTimTenTau.selectAll();
    		}else {
    			tbDanhSachTau.getItems().clear();
                
                // Tạo ObservableList từ danh sách ga
                ObservableList<Tau> data = FXCollections.observableArrayList(dstau);
                
                // Liên kết dữ liệu với các cột

                colStt.setCellValueFactory(cellData -> 
	            new SimpleStringProperty(String.valueOf(tbDanhSachTau.getItems().indexOf(cellData.getValue()) + 1)));
                
                colMaTau.setCellValueFactory(new PropertyValueFactory<>("maTau"));
                colTenTau.setCellValueFactory(new PropertyValueFactory<>("tenTau"));
                colLoaiTau.setCellValueFactory(cellData -> {
                	LoaiTau loaiTau = cellData.getValue().getLoaiTau();
                	String displayValue = "";
                	if (loaiTau == LoaiTau.SE) {
                	    displayValue = "SE";
                	} else if (loaiTau == LoaiTau.TN) {
                	    displayValue = "TN";
                	} else if (loaiTau == LoaiTau.DP) {
                	    displayValue = "DP";
                	}
                	return new SimpleStringProperty(displayValue);
                });
                colTrangThaiTau.setCellValueFactory(cellData -> {
                    TrangThaiTau trangThaiTau = cellData.getValue().getTrangThaiTau();
                    String displayValue = "";
                    if (trangThaiTau == TrangThaiTau.hoatDong) {
                        displayValue = "Hoạt động";
                    } else if (trangThaiTau == TrangThaiTau.baoTri) {
                        displayValue = "Bảo trì";
                    }
                    return new SimpleStringProperty(displayValue);
                });                
                // Đặt dữ liệu vào table
                tbDanhSachTau.setItems(data);
    		}
    	}
	}
	
	
    @FXML
    private TextField txtTimGa;
    
    @FXML
    private Button btnTimGa;
    
    @FXML
    private TableView<Ga> tbDanhSachGa; 
    
    @FXML
    private TableColumn<Ga, String> colSTTGa;
    
    @FXML
    private TableColumn<Ga, String> colMaGa;
    
    @FXML
    private TableColumn<Ga, String> colTenGa;
    
    @FXML
    private	TableColumn<Ga, String> colDiaChi;
    
    @FXML
    private void btnTimGaClicked() {
    	String tenGa = txtTimGa.getText().toString();
    	if(tenGa.trim().equals("")) {
    		showWarningAlert("Bạn chưa nhập tên ga để tìm!", "image/canhBao.png");
    		txtTimGa.requestFocus();
    	}else {
    		Ga_DAO ga_dao = new Ga_DAO();
    		List<Ga> dsga = ga_dao.timGaTheoTen(tenGa);
    		if(dsga.isEmpty()) {
    			showWarningAlert("Không tìm thấy ga theo tên được nhập!", "image/canhBao.png");
    			txtTimGa.requestFocus();
    			txtTimGa.selectAll();
    		}else {
                tbDanhSachGa.getItems().clear();
                
                // Tạo ObservableList từ danh sách ga
                ObservableList<Ga> data = FXCollections.observableArrayList(dsga);
                
                // Liên kết dữ liệu với các cột

                colSTTGa.setCellValueFactory(cellData -> 
	            new SimpleStringProperty(String.valueOf(tbDanhSachGa.getItems().indexOf(cellData.getValue()) + 1)));
                
                colMaGa.setCellValueFactory(new PropertyValueFactory<>("maGa"));
                colTenGa.setCellValueFactory(new PropertyValueFactory<>("tenGa"));
                colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
                
                // Đặt dữ liệu vào table
                tbDanhSachGa.setItems(data);
    		}
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
    
    @FXML
    private Button btnLamRong;
    
    @FXML
    private void btnLamRongClicked() {
    	cboLoaiTau.setValue(LoaiTau.SE);
    	cboTrangThaiTau.setValue(TrangThaiTau.hoatDong);
    	cboTrangThaiTau.setDisable(true);
    	cboTrangThaiTau.setEditable(true);
    	txtTenTau.setText("");
    	txtMaTau.setText("");
    }
    
    private boolean kiemTraTxtTau() {
        String tenTau = txtTenTau.getText().toString();
        if(tenTau.trim().equals("")) {
            showWarningAlert("Bạn chưa nhập tên tàu!", "image/canhBao.png");
            txtTenTau.requestFocus();
            txtTenTau.selectAll();
            return false;
        } else {
            Tau_DAO tau_DAO = new Tau_DAO();
            if(tau_DAO.kiemTraTonTaiTauTheoTen(tenTau)) {
                // Nếu tàu tồn tại, hiển thị lên table
                List<Tau> dsTau = tau_DAO.timTauTheoTen(tenTau);
                if(!dsTau.isEmpty()) {
                    // Xóa dữ liệu cũ trong table
                    tbDanhSachTau.getItems().clear();
                    
                    // Tạo ObservableList từ danh sách tàu tìm được
                    ObservableList<Tau> data = FXCollections.observableArrayList(dsTau);
                    
                    // Cập nhật dữ liệu cho các cột
                    colStt.setCellValueFactory(cellData -> 
                        new SimpleStringProperty(String.valueOf(tbDanhSachTau.getItems().indexOf(cellData.getValue()) + 1)));
                    colMaTau.setCellValueFactory(new PropertyValueFactory<>("maTau"));
                    colTenTau.setCellValueFactory(new PropertyValueFactory<>("tenTau"));
                    colLoaiTau.setCellValueFactory(cellData -> {
                        LoaiTau loaiTau = cellData.getValue().getLoaiTau();
                        String displayValue = "";
                        if (loaiTau == LoaiTau.SE) {
                            displayValue = "SE";
                        } else if (loaiTau == LoaiTau.TN) {
                            displayValue = "TN";
                        } else if (loaiTau == LoaiTau.DP) {
                            displayValue = "DP";
                        }
                        return new SimpleStringProperty(displayValue);
                    });
                    colTrangThaiTau.setCellValueFactory(cellData -> {
                        TrangThaiTau trangThaiTau = cellData.getValue().getTrangThaiTau();
                        String displayValue = "";
                        if (trangThaiTau == TrangThaiTau.hoatDong) {
                            displayValue = "Hoạt động";
                        } else if (trangThaiTau == TrangThaiTau.baoTri) {
                            displayValue = "Bảo trì";
                        }
                        return new SimpleStringProperty(displayValue);
                    });
                    
                    // Đặt dữ liệu vào table
                    tbDanhSachTau.setItems(data);
                    
                    // Hiển thị cảnh báo
                    showWarningAlert("Tàu đã tồn tại! Đã hiển thị thông tin tàu tương ứng.", "image/canhBao.png");
                }
                return false;
            }
        }
        return true;
    }
    
    @FXML
    private Button btnThemTau;
    
    @FXML
    private void btnThemTauClicked() {
        if (!txtMaTau.getText().toString().trim().equals("")) {
            showWarningAlert("Bạn đang chọn một tàu, vui lòng nhấn làm rỗng để nhập thông tin tàu cần thêm!", "image/canhBao.png");
            return;
        }

        if (kiemTraTxtTau()) {
            try {
                String tenTau = txtTenTau.getText().trim();
                LoaiTau loaiTau = cboLoaiTau.getValue();
                TrangThaiTau trangThai = cboTrangThaiTau.getValue();
                int soLuongToa = cboSoLuongToa.getValue();

                // Tạo DAO và mã tàu mới
                Tau_DAO tau_DAO = new Tau_DAO();
                String maTauMoi = tau_DAO.taoMaTauMoi(loaiTau);

                // Khởi tạo tàu mới
                Tau tau = new Tau();
                tau.setMaTau(maTauMoi);
                tau.setTenTau(tenTau);
                tau.setLoaiTau(loaiTau);
                tau.setTrangThaiTau(trangThai);

                // Thêm tàu vào DB
                boolean themTauThanhCong = tau_DAO.themTau(tau);

                if (themTauThanhCong) {
                    // Tạo toa tương ứng
                    Toa_DAO toa_DAO = new Toa_DAO();
                    boolean taoToaThanhCong = toa_DAO.taoToaTheoSoLuong(soLuongToa, maTauMoi);

                    if (taoToaThanhCong) {
                        showInformationAlert("Thêm tàu thành công với " + soLuongToa + " toa!", "image/thanhCong.png");
                    } else {
                        showWarningAlert("Thêm tàu thành công nhưng tạo toa không thành công!", "image/canhBao.png");
                    }

                    btnLamRongClicked();
                    btnTimTauClicked();
                } else {
                    showErrorAlert("Thêm tàu không thành công!", "image/loi.png");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("Lỗi khi thêm tàu: " + e.getMessage(), "image/loi.png");
            }
        }
    }

    
    @FXML
    private Button btnCapNhatToa;
    
    @FXML 
    private Button btnCapNhatTau;
    
    @FXML
    private ComboBox<String> cboToa;
    
    @FXML
    private ComboBox<String> cboLoaiToa;
    
    @FXML
    private ComboBox<String> cboTrangThaiToa;
    
    @FXML
    private void btnCapNhatTauClicked() {
        // Kiểm tra xem đã chọn tàu để cập nhật chưa
        if (txtMaTau.getText().trim().isEmpty()) {
            showWarningAlert("Vui lòng chọn một tàu từ danh sách để cập nhật!", "image/canhBao.png");
            return;
        }

        // Lấy thông tin từ giao diện
        String maTau = txtMaTau.getText().trim();
        String tenTau = txtTenTau.getText().trim();
        LoaiTau loaiTau = cboLoaiTau.getValue();
        TrangThaiTau trangThaiTau = cboTrangThaiTau.getValue();
        int soLuongToaMoi = cboSoLuongToa.getValue();

        // Kiểm tra tên tàu
        if (tenTau.isEmpty()) {
            showWarningAlert("Tên tàu không được để trống!", "image/canhBao.png");
            txtTenTau.requestFocus();
            return;
        }

        try {
            Tau_DAO tau_DAO = new Tau_DAO();
            Toa_DAO toa_DAO = new Toa_DAO();

            // Lấy số lượng toa hiện tại
            int soLuongToaHienTai = toa_DAO.laySoLuongToaTheoMaTau(maTau);

            // Tạo đối tượng Tau để cập nhật
            Tau tau = new Tau();
            tau.setMaTau(maTau);
            tau.setTenTau(tenTau);
            tau.setLoaiTau(loaiTau);
            tau.setTrangThaiTau(trangThaiTau);

            // Cập nhật thông tin tàu
            boolean capNhatThanhCong = tau_DAO.capNhatTau(tau);

            if (capNhatThanhCong) {
                // Xử lý số lượng toa nếu có thay đổi
                if (soLuongToaMoi > soLuongToaHienTai) {
                    // Thêm toa mới với số lượng cần thêm
                    int soToaCanThem = soLuongToaMoi - soLuongToaHienTai;
                    boolean themToaThanhCong = toa_DAO.taoToaTheoSoLuong(soToaCanThem, maTau);
                    if (!themToaThanhCong) {
                        showWarningAlert("Cập nhật tàu thành công nhưng thêm toa mới thất bại!", "image/canhBao.png");
                    }
                } else if (soLuongToaMoi < soLuongToaHienTai) {
                    // Xóa toa (xóa từ toa cuối cùng)
                    List<Toa> danhSachToa = toa_DAO.getToaByMaTau(maTau);
                    int soToaCanXoa = soLuongToaHienTai - soLuongToaMoi;
                    for (int i = 0; i < soToaCanXoa; i++) {
                        Toa toaXoa = danhSachToa.get(danhSachToa.size() - 1 - i);
                        String sql = "DELETE FROM Toa WHERE maToa = ?";
                        Connection con = ConnectDB.getConnection();
                        PreparedStatement stmt = con.prepareStatement(sql);
                        stmt.setString(1, toaXoa.getMaToa());
                        stmt.executeUpdate();
                        stmt.close();
                    }
                }

                showInformationAlert("Cập nhật tàu thành công!", "image/thanhCong.png");

                // Cập nhật lại giao diện
                List<Toa> danhSachToaMoi = toa_DAO.getToaByMaTau(maTau);
                ObservableList<String> tenToaList = FXCollections.observableArrayList(
                    danhSachToaMoi.stream().map(Toa::getTenToa).collect(java.util.stream.Collectors.toList())
                );
                cboToa.setItems(tenToaList);
                if (!tenToaList.isEmpty()) {
                    cboToa.setValue(tenToaList.get(0));
                    capNhatThongTinToa(danhSachToaMoi, tenToaList.get(0));
                }
            } else {
                showErrorAlert("Cập nhật tàu thất bại!", "image/loi.png");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khi cập nhật tàu: " + e.getMessage(), "image/loi.png");
        }
    }
    
    @FXML
    private void btnCapNhatToaClicked() {
        // Kiểm tra xem đã chọn tàu và toa chưa
        if (txtMaTau.getText().trim().isEmpty()) {
            showWarningAlert("Vui lòng chọn một tàu từ danh sách trước!", "image/canhBao.png");
            return;
        }
        if (cboToa.getValue() == null) {
            showWarningAlert("Vui lòng chọn một toa để cập nhật!", "image/canhBao.png");
            return;
        }

        // Lấy thông tin từ giao diện
        String maTau = txtMaTau.getText().trim();
        String tenToa = cboToa.getValue();
        String loaiToaStr = cboLoaiToa.getValue();
        String trangThaiToaStr = cboTrangThaiToa.getValue();

        try {
            Toa_DAO toa_DAO = new Toa_DAO();
            List<Toa> danhSachToa = toa_DAO.getToaByMaTau(maTau);

            // Tìm toa cần cập nhật
            Toa toaCapNhat = danhSachToa.stream()
                .filter(toa -> toa.getTenToa().equals(tenToa))
                .findFirst()
                .orElse(null);

            if (toaCapNhat == null) {
                showErrorAlert("Không tìm thấy toa để cập nhật!", "image/loi.png");
                return;
            }

            // Chuyển đổi giá trị từ tiếng Việt sang enum
            Toa.LoaiToa loaiToa = Arrays.stream(Toa.LoaiToa.values())
                .filter(lt -> lt.getDisplayName().equals(loaiToaStr))
                .findFirst()
                .orElse(Toa.LoaiToa.ngoiMemDieuHoa); // Mặc định nếu không tìm thấy

            Toa.TrangThaiToa trangThaiToa = Arrays.stream(Toa.TrangThaiToa.values())
                .filter(tt -> tt.getDisplayName().equals(trangThaiToaStr))
                .findFirst()
                .orElse(Toa.TrangThaiToa.hoatDong); // Mặc định nếu không tìm thấy

            // Cập nhật thông tin toa
            toaCapNhat.setTenToa(tenToa); // Tên toa không thay đổi trong trường hợp này
            toaCapNhat.setLoaiToa(loaiToa);
            toaCapNhat.setTrangThai(trangThaiToa);
            toaCapNhat.setMaTau(maTau);

            // Thực hiện cập nhật trong DB
            boolean capNhatThanhCong = false;
            Connection con = ConnectDB.getConnection();
            String sql = "UPDATE Toa SET loaiToa = ?, trangThai = ? WHERE maToa = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, loaiToa.name());
            stmt.setString(2, trangThaiToa.name());
            stmt.setString(3, toaCapNhat.getMaToa());
            int rowsAffected = stmt.executeUpdate();
            stmt.close();
            capNhatThanhCong = rowsAffected > 0;

            if (capNhatThanhCong) {
                showInformationAlert("Cập nhật toa thành công!", "image/thanhCong.png");
                // Cập nhật lại giao diện nếu cần
                capNhatThongTinToa(danhSachToa, tenToa);
            } else {
                showErrorAlert("Cập nhật toa thất bại!", "image/loi.png");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khi cập nhật toa: " + e.getMessage(), "image/loi.png");
        }
    }
}