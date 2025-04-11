package gui;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;

import entity.NhanVien;

import entity.NhanVien.ChucVu;
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javafx.stage.Stage;

public class QuanLyNhanVien_GUI_Controller {
	
	private String maNhanVien;

	// Thêm các Label để hiển thị thông tin nhân viên đang đăng nhập
	@FXML
	private Label lblMaNhanVien;
	@FXML
	private Label lblTenNhanVien;
	@FXML
	private Label lblChucVu;
	@FXML
	private ImageView imgAnhNhanVien;

	// Phương thức set mã nhân viên
	public void setMaNhanVien(String maNhanVien) {
	    this.maNhanVien = maNhanVien;
	    updateNhanVienInfo();
	}

	public String getMaNhanVien() {
	    return maNhanVien;
	}

	// Phương thức cập nhật thông tin nhân viên
	public void updateNhanVienInfo() {
	    if (maNhanVien != null && !maNhanVien.isEmpty()) {
	        NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
	        NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien);
	        if (nv != null) {
	            lblMaNhanVien.setText(nv.getMaNhanVien());
	            lblTenNhanVien.setText(nv.getTenNhanVien());
	            lblChucVu.setText(nv.getChucVu() == ChucVu.banVe ? "Bán vé" : "Quản lý");
	            
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
    private TableView<NhanVien> tbDanhSachNhanVien;
    
    @FXML
    private TableColumn<NhanVien, String> colStt;
    
    @FXML 
    private TableColumn<NhanVien, String> colMaNV;
    
    @FXML 
    private TableColumn<NhanVien, String> colTenNV;
    
    @FXML
    private TableColumn<NhanVien, String> colSoDienThoai;
    
    @FXML
    private TableColumn<NhanVien, String> colEmail;
    
    @FXML 
    private TableColumn<NhanVien, String> colGioiTinh;
    
    @FXML 
    private TableColumn<NhanVien, String> colChucVu;
    
    @FXML 
    private TableColumn<NhanVien, String> colTrangThai;
    
    @FXML
    private Button btnTim;

    @FXML
    private TextField txtTimTenNhanVien;

    @FXML
    private TextField txtTimSoDienThoai;

    private void setupTableColumns() {
        colStt.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.valueOf(tbDanhSachNhanVien.getItems().indexOf(cellData.getValue()) + 1)));
        colMaNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaNhanVien()));
        colTenNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenNhanVien()));
        colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colGioiTinh.setCellValueFactory(cellData -> {
            GioiTinh gioiTinh = cellData.getValue().getGioiTinh();
            return new SimpleStringProperty(gioiTinh == GioiTinh.nam ? "Nam" : "Nữ");
        });
        colChucVu.setCellValueFactory(cellData -> {
            ChucVu chucVu = cellData.getValue().getChucVu();
            return new SimpleStringProperty(chucVu == ChucVu.quanLy ? "Quản lý" : "Bán vé");
        });
        colTrangThai.setCellValueFactory(cellData -> {
            TrangThaiNhanVien trangThai = cellData.getValue().getTrangThaiNhanVien();
            return new SimpleStringProperty(trangThai == TrangThaiNhanVien.hoatDong ? "Hoạt động" : "Vô hiệu hóa");
        });
    }

    @FXML
    private void btnTimClicked() {
        // Lấy giá trị từ các TextField
        String tenNhanVien = txtTimTenNhanVien.getText().trim();
        String soDienThoai = txtTimSoDienThoai.getText().trim();
        
        // Kiểm tra nếu cả hai trường đều trống
        if (tenNhanVien.isEmpty() && soDienThoai.isEmpty()) {
        	showInformationAlert("Bạn chưa nhập thông tin tìm kiếm!","image/thongBao.png");
            txtTimTenNhanVien.requestFocus();
	        txtTimTenNhanVien.selectAll();
        }
        
        try {
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            List<NhanVien> ketQuaTimKiem;
            
            // Kiểm tra các trường nhập vào và thực hiện tìm kiếm theo yêu cầu
            if (!soDienThoai.isEmpty() && !tenNhanVien.isEmpty()) {
                ketQuaTimKiem = nhanVienDAO.timNhanVienTheoTenVaSoDienThoai(soDienThoai, tenNhanVien);
            } else if (!soDienThoai.isEmpty()) {
                ketQuaTimKiem = nhanVienDAO.timNhanVienTheoSoDienThoai(soDienThoai);
            } else {
                ketQuaTimKiem = nhanVienDAO.timNhanVienTheoTen(tenNhanVien);
            }
            
            // Kiểm tra kết quả tìm kiếm
            if (ketQuaTimKiem == null || ketQuaTimKiem.isEmpty()) {
                showInformationAlert("Không tìm thấy nhân viên phù hợp!","image/thongBao.png");
                tbDanhSachNhanVien.getSelectionModel().selectAll();
                // Di chuyển chuột đến dòng đầu tiên (hoặc dòng nào bạn muốn)
                tbDanhSachNhanVien.scrollTo(0); 
         
                return;
            }
            // Hiển thị kết quả trên TableView
            tbDanhSachNhanVien.getItems().clear();
            tbDanhSachNhanVien.setItems(FXCollections.observableArrayList(ketQuaTimKiem));
            setupTableColumns();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khi tìm kiếm nhân viên","image/canhBao.png");
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
    private Button btnThem;
    @FXML
    private Button btnXoa;
    @FXML
    private Button btnCapNhat;
    @FXML
    private TextField txtMaNV;
    @FXML
    private TextField txtTenNV;
    @FXML
    private DatePicker datePickerNgaySinh;
    @FXML
    private TextField txtSoDienThoai;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<GioiTinh> cboGioiTinh;  // ComboBox thay cho TextField
    @FXML
    private TextField txtCCCD;
    @FXML
    private ComboBox<ChucVu> cboChucVu;  // ComboBox thay cho TextField
    @FXML
    private ComboBox<TrangThaiNhanVien> cboTrangThaiNhanVien;  // ComboBox cho trạng thái nhân viên

    @FXML
    private ImageView imgNhanVien;
    
	@FXML
	private Label lblDangXuat;
	
	@FXML
	private AnchorPane pnHome;
	
	@FXML
	private Label lblQuanLyBanVe;
	
	@FXML
	private Label lblQuanLyVe;
	
	@FXML
	private Label lblQuanLyHoaDon;
	
	@FXML
	private Label lblQuanLyKhachHang;
	
	@FXML
	private Label lblQuanLyNhanVien;
	
	@FXML
	private Label lblThongKe;
	
	@FXML
	private Label lblTrangChu;
	
	@FXML 
	private Label lblQuanLyChuyenTau;
    
    @FXML
    private void initialize() {
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
    	txtTimTenNhanVien.requestFocus();
        // Thiết lập ComboBox chức vụ và trạng thái nhân viên
    	cboGioiTinh.getItems().setAll(GioiTinh.values());
    	cboGioiTinh.setValue(GioiTinh.nam);
    	
        cboChucVu.getItems().setAll(ChucVu.values());
        cboChucVu.setValue(ChucVu.banVe); 
        
        txtMaNV.setDisable(true);
        txtMaNV.setEditable(false);
        
		Platform.runLater(() -> {
	        txtTimTenNhanVien.requestFocus();
	    });
        
        cboTrangThaiNhanVien.getItems().setAll(TrangThaiNhanVien.values());
        cboTrangThaiNhanVien.setValue(TrangThaiNhanVien.hoatDong);
        cboTrangThaiNhanVien.setEditable(false);
        cboTrangThaiNhanVien.setDisable(true);

        // Thiết lập hành vi khi click vào bảng danh sách nhân viên
        tbDanhSachNhanVien.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                NhanVien selectedItem = tbDanhSachNhanVien.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Hiển thị thông tin nhân viên lên form
                    txtMaNV.setText(selectedItem.getMaNhanVien());
                    txtTenNV.setText(selectedItem.getTenNhanVien());
                    txtCCCD.setText(selectedItem.getCccd_HoChieu());
                    txtSoDienThoai.setText(selectedItem.getSoDienThoai());
                    txtEmail.setText(selectedItem.getEmail());
                    datePickerNgaySinh.setValue(selectedItem.getNgaySinh());
                    cboGioiTinh.setValue(selectedItem.getGioiTinh());
                    cboChucVu.setValue(selectedItem.getChucVu());
                    cboTrangThaiNhanVien.setValue(selectedItem.getTrangThaiNhanVien());
                    
                    // Load ảnh từ đường dẫn file
                    String imagePath = selectedItem.getLinkAnh();  
                    Image image = new Image("file:" + imagePath);

                    // Gán ảnh vào ImageView
                    imgNhanVien.setImage(image);
                    
                    cboChucVu.setEditable(true);
                    cboChucVu.setDisable(false);
                    cboTrangThaiNhanVien.setEditable(true);
                    cboTrangThaiNhanVien.setDisable(false);
                    linkAnh = selectedItem.getLinkAnh();
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
    }

	@FXML
	private Button btnThemNhanVien;
	
	@FXML
	private void btnThemNhanVienClicked() throws SQLException {
		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
		if(!txtMaNV.getText().toString().isEmpty()) {
			showWarningAlert("Hãy nhấn làm rỗng nếu muốn thêm nhân viên mới!","image/canhBao.png");
			return;
		}
		if(nhanVien_DAO.kiemTraCCCD(txtCCCD.getText().toString())) {
			showErrorAlert("CCCD/Hộ chiếu đã tồn tại!","image/canhBao.png");
			txtCCCD.requestFocus();
			txtCCCD.selectAll();
			List<NhanVien> danhSachNhanVien = nhanVien_DAO.timNhanVienTheoCCCD_HoChieu(txtCCCD.getText().toString());
		     // Xóa dữ liệu cũ trong table (nếu có)
		        tbDanhSachNhanVien.getItems().clear();
		        
		        // Thêm dữ liệu mới vào table
		        tbDanhSachNhanVien.getItems().addAll(danhSachNhanVien);
		        
		        // Thiết lập giá trị cho các cột
		        colStt.setCellValueFactory(cellData -> 
		            new SimpleStringProperty(String.valueOf(tbDanhSachNhanVien.getItems().indexOf(cellData.getValue()) + 1)));
		        colMaNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaNhanVien()));
		        colTenNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenNhanVien()));
		        colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
		        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		        colGioiTinh.setCellValueFactory(cellData -> {
		        	GioiTinh gioiTinh = cellData.getValue().getGioiTinh();
		        	String displayValue = "";
		        	if (gioiTinh == GioiTinh.nam) {
		        	    displayValue = "Nam";
		        	} else if (gioiTinh == GioiTinh.nu) {
		        	    displayValue = "Nữ";
		        	}
		        	return new SimpleStringProperty(displayValue);
		        });
		        colChucVu.setCellValueFactory(cellData -> {
		        	ChucVu chucVu = cellData.getValue().getChucVu();
		        	String displayValue = "";
		        	if (chucVu == ChucVu.banVe) {
		        	    displayValue = "Bán vé";
		        	} else if (chucVu == ChucVu.quanLy) {
		        	    displayValue = "Quản lý";
		        	}
		        	return new SimpleStringProperty(displayValue);
		        });
		        colTrangThai.setCellValueFactory(cellData -> {
		            TrangThaiNhanVien trangThai = cellData.getValue().getTrangThaiNhanVien();
		            String displayValue = "";
		            if (trangThai == TrangThaiNhanVien.hoatDong) {
		                displayValue = "Hoạt động";
		            } else if (trangThai == TrangThaiNhanVien.voHieuHoa) {
		                displayValue = "Vô hiệu hóa";
		            }
		            return new SimpleStringProperty(displayValue);
		        });
		        return;
		}
	    if(kiemTraTxt()) {
	    	String tenNhanVien = txtTenNV.getText();
			String CCCD_HoChieu = txtCCCD.getText();
			LocalDate ngaySinh = datePickerNgaySinh.getValue();
			String soDienThoai = txtSoDienThoai.getText();
			String email = txtEmail.getText();
			
			ChucVu chucVu;
			Object chucVuValue = cboChucVu.getValue();
			if (chucVuValue instanceof ChucVu) {
			    // Nếu getValue() trả về enum
			    chucVu = (ChucVu) chucVuValue;
			} else {
			    // Nếu getValue() trả về String (toString() của enum)
			    String chucVuStr = chucVuValue != null ? chucVuValue.toString().trim() : "Bán vé";
			    if (chucVuStr.equals("Quản lý")) {
			        chucVu = ChucVu.quanLy;
			    } else {
			        chucVu = ChucVu.banVe; // Mặc định là Bán vé
			    }
			}
			
			TrangThaiNhanVien trangThai;
			Object trangThaiValue = cboTrangThaiNhanVien.getValue();

			if (trangThaiValue instanceof TrangThaiNhanVien) {
			    // Nếu getValue() trả về enum
			    trangThai = (TrangThaiNhanVien) trangThaiValue;
			} else {
			    // Nếu getValue() trả về String (toString() của enum)
			    String trangThaiStr = trangThaiValue != null ? trangThaiValue.toString().trim() : "Hoạt động";
			    
			    if (trangThaiStr.equalsIgnoreCase("Hoạt động") || trangThaiStr.equalsIgnoreCase("hoatDong")) {
			        trangThai = TrangThaiNhanVien.hoatDong;
			    } else if (trangThaiStr.equalsIgnoreCase("Vô hiệu hóa") || trangThaiStr.equalsIgnoreCase("voHieuHoa")) {
			        trangThai = TrangThaiNhanVien.voHieuHoa;
			    } else {
			        trangThai = TrangThaiNhanVien.hoatDong; // Mặc định là Hoạt động
			    }
			}
			
			GioiTinh gioiTinh;
			Object gioiTinhValue = cboGioiTinh.getValue();

			if (gioiTinhValue instanceof GioiTinh) {
			    // Nếu getValue() trả về enum
			    gioiTinh = (GioiTinh) gioiTinhValue;
			} else {
			    // Nếu getValue() trả về String (từ toString() của enum hoặc hiển thị trên GUI)
			    String gioiTinhStr = gioiTinhValue != null ? gioiTinhValue.toString().trim() : "Nam"; // Mặc định là "Nam"
			    
			    if (gioiTinhStr.equalsIgnoreCase("Nam") || gioiTinhStr.equalsIgnoreCase("nam")) {
			        gioiTinh = GioiTinh.nam;
			    } else if (gioiTinhStr.equalsIgnoreCase("Nữ") || gioiTinhStr.equalsIgnoreCase("nu")) {
			        gioiTinh = GioiTinh.nu;
			    } else {
			        gioiTinh = GioiTinh.nam; // Mặc định là "Nam" nếu không khớp
			    }
			}
			String decodedPath = URLDecoder.decode(linkAnh.replace("file:/", ""), StandardCharsets.UTF_8);
			File sourceFile = new File(decodedPath);

			try {
			    String fileName = sourceFile.getName();
			    File destFile = new File("image/" + fileName);
			    Files.copy(
			        sourceFile.toPath(),
			        destFile.toPath(),
			        StandardCopyOption.REPLACE_EXISTING
			    );

			    // Cập nhật linkAnh với đường dẫn tương đối
			    linkAnh = "image/" + fileName;
			    System.out.println("Ảnh đã được sao chép đến: " + linkAnh);

			} catch (IOException e) {
			    e.printStackTrace();
			    showErrorAlert("Không thể sao chép ảnh: " + e.getMessage(),"image/canhBao.png");
			    return;
			}
			String maNhanVien = nhanVien_DAO.taoMaNhanVienMoi();
			NhanVien nv = new NhanVien(maNhanVien, tenNhanVien, ngaySinh, gioiTinh, CCCD_HoChieu, chucVu, trangThai, soDienThoai, email, linkAnh);
			if(!nhanVien_DAO.themNhanVien(nv)) {
				showErrorAlert("Thêm không thành công!","image/canhBao.png");
				return;
			}else {
				showInformationAlert("Thêm nhân viên thành công!\nMã nhân viên: "+nv.getMaNhanVien()+"\nTên nhân viên: "+nv.getTenNhanVien(),"image/thanhCong.png");
				linkAnh = "";
				txtMaNV.setText(maNhanVien);
				return;
			}
	    }
	}
	
	@FXML
	private String linkAnh = "";

	@FXML
	private Button btnThemAnh;
	
	@FXML
	private void btnThemAnhClicked() {
	    // Tạo FileChooser
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Chọn ảnh nhân viên");

	    // Chỉ cho phép chọn file ảnh (JPG, PNG)
	    fileChooser.getExtensionFilters().addAll(
	        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
	    );

	    // Mở hộp thoại chọn file
	    File selectedFile = fileChooser.showOpenDialog(null);
	    if (selectedFile != null) {
	        try {
	            // Tải ảnh từ file và hiển thị lên ImageView
	        	linkAnh = selectedFile.toURI().toString();
	            Image image = new Image(selectedFile.toURI().toString());
	            imgNhanVien.setImage(image);
	            imgNhanVien.setFitWidth(100);
	            imgNhanVien.setFitHeight(100);
	        } catch (Exception e) {
	            showErrorAlert("Không tải ảnh được!","image/canhBao.png");
	            imgNhanVien.setImage(null);
	        }
	    }
	}
	
	@FXML
	private boolean kiemTraTxt() {
		String tenNhanVien = txtTenNV.getText();
		String CCCD_HoChieu = txtCCCD.getText();
		LocalDate ngaySinh = datePickerNgaySinh.getValue();
		String soDienThoai = txtSoDienThoai.getText();
		String email = txtEmail.getText();
		
		String regexTen = "^[A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*(?: [A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*)+$";
		String regexCCCD_HoChieu = "^(\\d{12}|[A-Z]\\d{7})$";
		String regexSoDienThoai = "^(0|\\+84)(3|5|7|8|9)\\d{8}$";
		String regexEmail = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";
		LocalDate ngayHomNay = LocalDate.now();

		if(tenNhanVien.trim().equals("")) {
			showWarningAlert("Tên nhân viên không được rỗng!","image/canhBao.png");
			txtTenNV.requestFocus();
			return false;
		}else {
			Pattern pt = Pattern.compile(regexTen);
			Matcher mc = pt.matcher(tenNhanVien);
			if(!mc.matches()) {
				showWarningAlert("Tên nhân viên phải có 2 từ trở lên, viết hoa chữ cái đầu!","image/canhBao.png");
				txtTenNV.requestFocus();
				txtTenNV.selectAll();
				return false;
			}
		}
		if(CCCD_HoChieu.trim().equals("")) {
			showWarningAlert("CCCD/Hộ chiếu của nhân viên không được rỗng!","image/canhBao.png");
			txtCCCD.requestFocus();
			return false;
		}else {
			Pattern pt = Pattern.compile(regexCCCD_HoChieu);
			Matcher mc = pt.matcher(CCCD_HoChieu);
			if(!mc.matches()) {
				showWarningAlert("CCCD phải là dãy 12 chữ số trở lên. Hộ chiếu phải bắt đầu bằng 1 kí tự in hoa và dãy 7 chữ số!","image/canhBao.png");
				txtCCCD.requestFocus();
				txtCCCD.selectAll();
				return false;
			}
		}
		if(ngaySinh == null) {
			showWarningAlert("Ngày sinh của nhân viên không được rỗng!","image/canhBao.png");
			datePickerNgaySinh.requestFocus();
			return false;
		}else {
			ChucVu chucVu;
			Object chucVuValue = cboChucVu.getValue();
			if (chucVuValue instanceof ChucVu) {
			    // Nếu getValue() trả về enum
			    chucVu = (ChucVu) chucVuValue;
			} else {
			    // Nếu getValue() trả về String (toString() của enum)
			    String chucVuStr = chucVuValue != null ? chucVuValue.toString().trim() : "Bán vé";
			    if (chucVuStr.equals("Quản lý")) {
			        chucVu = ChucVu.quanLy;
			    } else {
			        chucVu = ChucVu.banVe; // Mặc định là Bán vé
			    }
			}
			// Tính khoảng cách giữa ngayHienTai và ngaySinh
			Period period = Period.between(ngaySinh, ngayHomNay);

			// Kiểm tra tuổi cho chức vụ Bán vé (phải lớn hơn 16 tuổi)
			if (chucVu.equals(ChucVu.banVe)) {
			    if (period.getYears() < 16 || (period.getYears() == 16 && period.getMonths() == 0 && period.getDays() == 0)) {
			    	showWarningAlert("Nhân viên bán vé phải lớn hơn 16 tuổi!","image/canhBao.png");
			        datePickerNgaySinh.requestFocus();
			        datePickerNgaySinh.getEditor().selectAll();
			        return false;
			    }
			}

			// Kiểm tra tuổi cho chức vụ Quản lý (phải lớn hơn 20 tuổi)
			if (chucVu.equals(ChucVu.quanLy)) {
			    if (period.getYears() < 20 || (period.getYears() == 20 && period.getMonths() == 0 && period.getDays() == 0)) {
			        showErrorAlert("Nhân viên quản lý phải lớn hơn 20 tuổi!","image/canhBao.png");
			        datePickerNgaySinh.requestFocus();
			        datePickerNgaySinh.getEditor().selectAll();
			        return false;
			    }
			}
		}
		
		if(soDienThoai.trim().equals(email)) {
			showWarningAlert("Số điện thoại của nhân viên không được rỗng!","image/canhBao.png");
			txtSoDienThoai.requestFocus();
			return false;
		}else {
			Pattern pt = Pattern.compile(regexSoDienThoai);
			Matcher mc = pt.matcher(soDienThoai);
			if(!mc.matches()) {
				showErrorAlert("Số điện thoại phải là dãy số (03|05|07|08|09) và 8 chữ số ngẫu nhiên!","image/canhBao.png");
				txtSoDienThoai.requestFocus();
				txtSoDienThoai.selectAll();
				return false;
			}
		}
		if(email.trim().equals("")) {
			showWarningAlert("Email của nhân viên không được rỗng!","image/canhBao.png");
			txtEmail.requestFocus();
			return false;
		}else {
			Pattern pt = Pattern.compile(regexEmail);
			Matcher mc = pt.matcher(email);
			if(!mc.matches()) {
				showWarningAlert("Email sai định dạng!","image/canhBao.png");
				txtEmail.requestFocus();
				txtEmail.selectAll();
				return false;
			}
		}
		if(linkAnh.trim().equals("")) {
			showWarningAlert("Ảnh nhân viên chưa được chọn!","image/canhBao.png");
			return false;
		}
		return true;
	}



    // Xử lý cập nhật nhân viên
	@FXML
	private void btnCapNhatClicked() throws SQLException {
	    // Kiểm tra đã chọn nhân viên từ bảng chưa
	    if (txtMaNV.getText().isEmpty()) {
	    	showWarningAlert("Vui lòng chọn nhân viên cần cập nhật từ bảng!","image/canhBao.png");
	        return;

	    }

	    // Kiểm tra dữ liệu hợp lệ
	    if (!kiemTraTxt()) {
	        return;
	    }

	    NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
	    
	    // Lấy thông tin từ form
	    String maNhanVien = txtMaNV.getText();
	    String tenNhanVien = txtTenNV.getText();
	    LocalDate ngaySinh = datePickerNgaySinh.getValue();
	    String soDienThoai = txtSoDienThoai.getText();
	    String email = txtEmail.getText();
	    String CCCD_HoChieu = txtCCCD.getText();
	    
	    // Xử lý giới tính
	    GioiTinh gioiTinh = cboGioiTinh.getValue();
	    
	    // Xử lý chức vụ
	    ChucVu chucVu = cboChucVu.getValue();
	    
	    // Xử lý trạng thái
	    TrangThaiNhanVien trangThai = cboTrangThaiNhanVien.getValue();
	    
	    // Xử lý ảnh
	    if (!linkAnh.isEmpty()) {
	        String decodedPath = URLDecoder.decode(linkAnh.replace("file:/", ""), StandardCharsets.UTF_8);
	        File sourceFile = new File(decodedPath);
	        
	        try {
	            String fileName = sourceFile.getName();
	            File destFile = new File("image/" + fileName);

	            Files.copy(
	                sourceFile.toPath(),
	                destFile.toPath(),
	                StandardCopyOption.REPLACE_EXISTING
	            );

	            linkAnh = "image/" + fileName;
	        } catch (IOException e) {
	            e.printStackTrace();
	            showErrorAlert("Không thể sao chép ảnh: " + e.getMessage(),"image/canhBao.png");
	            return;
	        }
	    }

	    // Hiển thị hộp thoại xác nhận
	    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	    confirmationAlert.setTitle("Xác nhận cập nhật");
	    confirmationAlert.setHeaderText("Bạn có chắc chắn muốn cập nhật thông tin nhân viên này?");
	    confirmationAlert.setContentText("Mã NV: " + txtMaNV.getText() + "\nTên NV: " + txtTenNV.getText());
	    
	    // Thiết lập icon cho hộp thoại
	    setAlertIcon(confirmationAlert, "image/hoi.png");
	    
	    // Thêm các nút tùy chỉnh
	    ButtonType buttonTypeYes = new ButtonType("Cập nhật", ButtonData.YES);
	    ButtonType buttonTypeNo = new ButtonType("Hủy", ButtonData.NO);
	    confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

	    // Hiển thị hộp thoại và chờ phản hồi từ người dùng
	    Optional<ButtonType> result = confirmationAlert.showAndWait();
	    
	    // Nếu người dùng chọn "Hủy" thì không thực hiện cập nhật
	    if (result.isPresent() && result.get() == buttonTypeNo) {
	        return;
	    }
	    
	    // Tạo đối tượng nhân viên mới với thông tin cập nhật
	    NhanVien nv = new NhanVien(
	        maNhanVien, 
	        tenNhanVien, 
	        ngaySinh, 
	        gioiTinh, 
	        CCCD_HoChieu, 
	        chucVu, 
	        trangThai, 
	        soDienThoai, 
	        email, 
	        linkAnh
	    );

	    // Gọi DAO để cập nhật
	    if (nhanVien_DAO.capNhatNhanVien(nv)) {
	        showInformationAlert("Cập nhật nhân viên thành công!","image/thanhCong.png");
	        
	        // Lấy vị trí của nhân viên đã cập nhật trong bảng
	        int selectedIndex = tbDanhSachNhanVien.getSelectionModel().getSelectedIndex();
	        
	        // Cập nhật lại dòng đã sửa trong bảng
	        if (selectedIndex >= 0) {
	            tbDanhSachNhanVien.getItems().set(selectedIndex, nv);
	        }
	        
	        // Làm rỗng form
	        btnLamRongClicked();
	    } else {
	        showErrorAlert("Cập nhật không thành công!","image/canhBao.png");
	    }
	}

	@FXML 
	private Button btnLamRong;
	
	@FXML
	private void btnLamRongClicked() {
		txtMaNV.setText("");
		txtTenNV.setText("");
		txtCCCD.setText("");
		txtEmail.setText("");
		txtSoDienThoai.setText("");
		datePickerNgaySinh.setValue(null);
		cboTrangThaiNhanVien.setValue(TrangThaiNhanVien.hoatDong);
		cboGioiTinh.setValue(GioiTinh.nam);
		cboChucVu.setValue(ChucVu.banVe);
		imgNhanVien.setImage(null);
		cboTrangThaiNhanVien.setEditable(false);
		cboTrangThaiNhanVien.setDisable(true);
		txtTenNV.requestFocus();
		linkAnh = "";
	}
    
	@FXML
	private Button btnThemTaiKhoan;
	
	@FXML
	private void btnThemTaiKhoanClicked() {
		if(txtMaNV.getText().toString().trim().equals("")) {
			showErrorAlert("Chọn một nhân viên trước khi thêm tài khoản!", "image/canhBao.png");
		}else {
			String maNhanVien = txtMaNV.getText().toString().trim();
			TaiKhoan_DAO taiKhoan_DAO = new TaiKhoan_DAO();
			if(taiKhoan_DAO.kiemTraTonTaiTaiKhoanTheoMaNhanVien(maNhanVien)) {
				showWarningAlert("Nhân viên đã có tài khoản, vui lòng chọn nhân viên khác hoặc sang chức năng quản lý tài khoản để đổi mật khẩu!", "image/canhBao.png");
				return;
			}
			// Tạo một Stage mới cho cửa sổ modal
			Stage stage = new Stage();
	        
	        // Khởi tạo 
	        new ThemTaiKhoan_GUI(stage, maNhanVien);
		}
	}
}