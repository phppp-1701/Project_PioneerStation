package gui;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.KhachHang;
import entity.NhanVien;
import entity.KhachHang.LoaiThanhVien;
import entity.KhachHang.TrangThaiKhachHang;
import entity.NhanVien.ChucVu;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

public class QuanLyKhachHang_GUI_Controller {
	
	private String maNhanVien;
	
	// Added maNhanVien handling from previous pattern
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        updateNhanVienInfo();
    }

    public String getMaNhanVien() {
        return maNhanVien;
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
	private TableView<KhachHang> tbDanhSachKhachHang;
	
	@FXML
	private TableColumn<KhachHang, String> colStt; 
	
	@FXML 
	private TableColumn<KhachHang, String> colMaKhachHang;
	
	@FXML 
	private TableColumn<KhachHang, String> colTen;
	
	@FXML
	private TableColumn<KhachHang, String> colSoDienThoai;
	
	@FXML 
	private TableColumn<KhachHang, String> colEmail;
	
	@FXML 
	private TableColumn<KhachHang, String> colLoaiKhachHang;
	
	@FXML
	private TableColumn<KhachHang, String> colTrangThaiKhachHang;
	
	@FXML
	private Button btnTim;
	
	@FXML 
	private TextField txtTimTenKhachHang;
	
	@FXML
	private TextField txtTimSoDienThoai;
	
	@FXML
	private void btnTimClicked() {
		// Lấy giá trị từ các TextField
	    String tenKhachHang = txtTimTenKhachHang.getText().trim();
	    String soDienThoai = txtTimSoDienThoai.getText().trim();
	    
	    // Kiểm tra nếu cả hai trường đều trống
	    if (tenKhachHang.isEmpty() && soDienThoai.isEmpty()) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Cảnh báo");
	        alert.setHeaderText(null);
	        alert.setContentText("Vui lòng nhập tên khách hàng hoặc số điện thoại để tìm kiếm!");
	        
	        File file = new File("image/canhBao.png");
	        if (file.exists()) {
	            try {
	                // Cách 1: Dùng đường dẫn file
	                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	                stage.getIcons().add(new Image(file.toURI().toString()));
	                
	                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
	            } catch (Exception e) {
	                System.err.println("Lỗi khi tải ảnh: " + e.getMessage());
	            }
	        } else {
	            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
	        }
	        
	        alert.showAndWait();
	        txtTimTenKhachHang.requestFocus();
	        txtTimTenKhachHang.selectAll();
	        return;
	    }
	    
	    if (soDienThoai.isEmpty() && !tenKhachHang.isEmpty()) {
	    	try {
		        List<KhachHang> danhSachKhachHang = new KhachHang_DAO().timKhachHangTheoTen(tenKhachHang);
		        if (danhSachKhachHang.isEmpty()) {
		            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		            alert.setTitle("Thông báo");
		            alert.setHeaderText(null);
		            alert.setContentText("Không tìm thấy khách hàng có tên được nhập!");
		            File file = new File("image/canhBao.png");
			        if (file.exists()) {
			            try {
			                // Cách 1: Dùng đường dẫn file
			                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			                stage.getIcons().add(new Image(file.toURI().toString()));
			                
			                // Hoặc Cách 2: Dùng InputStream
			                // Image image = new Image(new FileInputStream(file));
			                // stage.getIcons().add(image);
			                
			                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
			            } catch (Exception e1) {
			                System.err.println("Lỗi khi tải ảnh: " + e1.getMessage());
			            }
			        } else {
			            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
			        }
		            alert.showAndWait();
			        txtTimTenKhachHang.requestFocus();
			        txtTimTenKhachHang.selectAll();
		            return;
		        }
		        
		        // Xóa dữ liệu cũ trong table (nếu có)
		        tbDanhSachKhachHang.getItems().clear();
		        
		        // Thêm dữ liệu mới vào table
		        tbDanhSachKhachHang.getItems().addAll(danhSachKhachHang);
		        
		        // Thiết lập giá trị cho các cột
		        colStt.setCellValueFactory(cellData -> 
		            new SimpleStringProperty(String.valueOf(tbDanhSachKhachHang.getItems().indexOf(cellData.getValue()) + 1)));
		        colMaKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaKhachHang()));
		        colTen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenKhachHang()));
		        colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
		        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		        colLoaiKhachHang.setCellValueFactory(cellData -> {
		            LoaiThanhVien loai = cellData.getValue().getLoaiThanhVien();
		            String displayValue = "";
		            if (loai == LoaiThanhVien.thanThiet) {
		                displayValue = "Thân thiết";
		            } else if (loai == LoaiThanhVien.vip) {
		                displayValue = "VIP";
		            }
		            return new SimpleStringProperty(displayValue);
		        });
		        colTrangThaiKhachHang.setCellValueFactory(cellData -> {
		            TrangThaiKhachHang trangThaiKhachHang = cellData.getValue().getTrangThaiKhachHang();
		            String displayValue = "";
		            if (trangThaiKhachHang == TrangThaiKhachHang.hoatDong) {
		                displayValue = "Hoạt động";
		            } else if (trangThaiKhachHang == TrangThaiKhachHang.voHieuHoa) {
		                displayValue = "Vô hiệu hóa";
		            }
		            return new SimpleStringProperty(displayValue);
		        });
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		        Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Lỗi");
		        alert.setHeaderText(null);
		        alert.setContentText("Không thể tải danh sách khách hàng!");
		        File file = new File("image/canhBao.png");
		        if (file.exists()) {
		            try {
		                // Cách 1: Dùng đường dẫn file
		                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		                stage.getIcons().add(new Image(file.toURI().toString()));
		                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
		            } catch (Exception e1) {
		                System.err.println("Lỗi khi tải ảnh: " + e.getMessage());
		            }
		        } else {
		            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
		        }
		        alert.showAndWait();
	        }
	    }
	    if (!soDienThoai.isEmpty() && tenKhachHang.isEmpty()) {
	    	try {
		        List<KhachHang> danhSachKhachHang = new KhachHang_DAO().timKhachHangTheoSoDienThoai(soDienThoai);
		        if (danhSachKhachHang.isEmpty()) {
		            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		            alert.setTitle("Thông báo");
		            alert.setHeaderText(null);
		            alert.setContentText("Không tìm thấy khách hàng có số điện thoại được nhập!");
		            File file = new File("image/canhBao.png");
			        if (file.exists()) {
			            try {
			                // Cách 1: Dùng đường dẫn file
			                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			                stage.getIcons().add(new Image(file.toURI().toString()));   
			                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
			            } catch (Exception e1) {
			                System.err.println("Lỗi khi tải ảnh: " + e1.getMessage());
			            }
			        } else {
			            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
			        }
		            alert.showAndWait();
			        txtTimSoDienThoai.requestFocus();
			        txtTimSoDienThoai.selectAll();
		            return;
		        }
		        
		        // Xóa dữ liệu cũ trong table (nếu có)
		        tbDanhSachKhachHang.getItems().clear();
		        
		        // Thêm dữ liệu mới vào table
		        tbDanhSachKhachHang.getItems().addAll(danhSachKhachHang);
		        
		        // Thiết lập giá trị cho các cột
		        colStt.setCellValueFactory(cellData -> 
		            new SimpleStringProperty(String.valueOf(tbDanhSachKhachHang.getItems().indexOf(cellData.getValue()) + 1)));
		        colMaKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaKhachHang()));
		        colTen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenKhachHang()));
		        colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
		        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		        colLoaiKhachHang.setCellValueFactory(cellData -> {
		            LoaiThanhVien loai = cellData.getValue().getLoaiThanhVien();
		            String displayValue = "";
		            if (loai == LoaiThanhVien.thanThiet) {
		                displayValue = "Thân thiết";
		            } else if (loai == LoaiThanhVien.vip) {
		                displayValue = "VIP";
		            }
		            return new SimpleStringProperty(displayValue);
		        });
		        colTrangThaiKhachHang.setCellValueFactory(cellData -> {
		            TrangThaiKhachHang trangThaiKhachHang = cellData.getValue().getTrangThaiKhachHang();
		            String displayValue = "";
		            if (trangThaiKhachHang == TrangThaiKhachHang.hoatDong) {
		                displayValue = "Hoạt động";
		            } else if (trangThaiKhachHang == TrangThaiKhachHang.voHieuHoa) {
		                displayValue = "Vô hiệu hóa";
		            }
		            return new SimpleStringProperty(displayValue);
		        });
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		        Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Lỗi");
		        alert.setHeaderText(null);
		        alert.setContentText("Không thể tải danh sách khách hàng!");
		        File file = new File("image/canhBao.png");
		        if (file.exists()) {
		            try {
		                // Cách 1: Dùng đường dẫn file
		                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		                stage.getIcons().add(new Image(file.toURI().toString()));
		                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
		            } catch (Exception e1) {
		                System.err.println("Lỗi khi tải ảnh: " + e.getMessage());
		            }
		        } else {
		            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
		        }
		        alert.showAndWait();
	        }
	    }
	    if (!soDienThoai.isEmpty() && !tenKhachHang.isEmpty()) {
	    	try {
		        List<KhachHang> danhSachKhachHang = new KhachHang_DAO().timKhachHangTheoTenVaSoDienThoai(tenKhachHang, soDienThoai);
		        if (danhSachKhachHang.isEmpty()) {
		            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		            alert.setTitle("Thông báo");
		            alert.setHeaderText(null);
		            alert.setContentText("Không tìm thấy khách hàng có tên và số điện thoại được nhập!");
		            File file = new File("image/canhBao.png");
			        if (file.exists()) {
			            try {
			                // Cách 1: Dùng đường dẫn file
			                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			                stage.getIcons().add(new Image(file.toURI().toString()));   
			                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
			            } catch (Exception e1) {
			                System.err.println("Lỗi khi tải ảnh: " + e1.getMessage());
			            }
			        } else {
			            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
			        }
		            alert.showAndWait();
			        txtTimSoDienThoai.requestFocus();
			        txtTimSoDienThoai.selectAll();
		            return;
		        }
		        
		        // Xóa dữ liệu cũ trong table (nếu có)
		        tbDanhSachKhachHang.getItems().clear();
		        
		        // Thêm dữ liệu mới vào table
		        tbDanhSachKhachHang.getItems().addAll(danhSachKhachHang);
		        
		        // Thiết lập giá trị cho các cột
		        colStt.setCellValueFactory(cellData -> 
		            new SimpleStringProperty(String.valueOf(tbDanhSachKhachHang.getItems().indexOf(cellData.getValue()) + 1)));
		        colMaKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaKhachHang()));
		        colTen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenKhachHang()));
		        colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
		        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		        colLoaiKhachHang.setCellValueFactory(cellData -> {
		            LoaiThanhVien loai = cellData.getValue().getLoaiThanhVien();
		            String displayValue = "";
		            if (loai == LoaiThanhVien.thanThiet) {
		                displayValue = "Thân thiết";
		            } else if (loai == LoaiThanhVien.vip) {
		                displayValue = "VIP";
		            }
		            return new SimpleStringProperty(displayValue);
		        });
		        colTrangThaiKhachHang.setCellValueFactory(cellData -> {
		            TrangThaiKhachHang trangThaiKhachHang = cellData.getValue().getTrangThaiKhachHang();
		            String displayValue = "";
		            if (trangThaiKhachHang == TrangThaiKhachHang.hoatDong) {
		                displayValue = "Hoạt động";
		            } else if (trangThaiKhachHang == TrangThaiKhachHang.voHieuHoa) {
		                displayValue = "Vô hiệu hóa";
		            }
		            return new SimpleStringProperty(displayValue);
		        });
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		        Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Lỗi");
		        alert.setHeaderText(null);
		        alert.setContentText("Không thể tải danh sách khách hàng!");
		        File file = new File("image/canhBao.png");
		        if (file.exists()) {
		            try {
		                // Cách 1: Dùng đường dẫn file
		                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		                stage.getIcons().add(new Image(file.toURI().toString()));
		                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
		            } catch (Exception e1) {
		                System.err.println("Lỗi khi tải ảnh: " + e.getMessage());
		            }
		        } else {
		            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
		        }
		        alert.showAndWait();
	        }
	    }
	}

	@FXML
	private TextField txtMaKhachHang;
	
	@FXML 
	private TextField txtTenKhachHang;
	
	@FXML
	private TextField txtCCCD_HoChieu;
	
	@FXML
	private TextField txtSoDienThoai;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private ComboBox<LoaiThanhVien> cboLoaiKhachHang;
	
	@FXML
	private ComboBox<TrangThaiKhachHang> cboTrangThai;
	
	@FXML
	private Button btnLamRong;
	
	@FXML
	private void btnLamRongClicked() {
		txtSoDienThoai.setText("");
		txtMaKhachHang.setText("");
		txtTenKhachHang.setText("");
		txtCCCD_HoChieu.setText("");
		txtEmail.setText("");
		cboLoaiKhachHang.setValue(LoaiThanhVien.thanThiet);
		cboTrangThai.setValue(TrangThaiKhachHang.hoatDong);
		cboLoaiKhachHang.setEditable(false);
		cboLoaiKhachHang.setDisable(true);
		cboTrangThai.setEditable(false);
		cboTrangThai.setDisable(true);
	}
	
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
		cboLoaiKhachHang.getItems().setAll(LoaiThanhVien.thanThiet, LoaiThanhVien.vip);
	    cboLoaiKhachHang.setValue(LoaiThanhVien.thanThiet); 
	    
	    cboTrangThai.getItems().setAll(TrangThaiKhachHang.values());
	    cboTrangThai.setValue(TrangThaiKhachHang.hoatDong);
	    
	    cboTrangThai.setEditable(false);
	    cboTrangThai.setDisable(true);
		cboLoaiKhachHang.setEditable(false);
		cboLoaiKhachHang.setDisable(true);
		
		Platform.runLater(() -> {
	        txtTimTenKhachHang.requestFocus();
	    });
	    
	    tbDanhSachKhachHang.setOnMouseClicked(event -> {
	    	if(event.getClickCount() == 1) {
	    		KhachHang selectItem = tbDanhSachKhachHang.getSelectionModel().getSelectedItem();
	    		if(selectItem != null) {
	    			 String maKhachHang = colMaKhachHang.getCellData(selectItem);
	    			 KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
	    			 KhachHang kh = khachHang_DAO.timKhachHangTheoMa(maKhachHang);
	    			 txtMaKhachHang.setText(kh.getMaKhachHang());
	    			 txtTenKhachHang.setText(kh.getTenKhachHang());
	    			 txtCCCD_HoChieu.setText(kh.getCCCD_HoChieu());
	    			 txtSoDienThoai.setText(kh.getSoDienThoai());
	    			 txtEmail.setText(kh.getEmail());
	    			 cboTrangThai.setValue(kh.getTrangThaiKhachHang());
	    			 cboLoaiKhachHang.setValue(kh.getLoaiThanhVien());
	    			 cboTrangThai.setEditable(true);
	    			 cboTrangThai.setDisable(false);
	    			 cboLoaiKhachHang.setEditable(true);
	    			 cboLoaiKhachHang.setDisable(false);
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
	private Button btnThemKhachHang;
	
	@FXML
	private void btnThemKhachHangClicked() {
		if(!txtMaKhachHang.getText().toString().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText(null);
	        alert.setContentText("Bạn đang chọn một khách hàng, vui lòng làm rỗng để thêm!");
	        File file = new File("image/canhBao.png");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(file.toURI().toString()));
            System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
	        alert.showAndWait();
	        return;
		}
		if(kiemTraTxt()) {
			KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
			String maKhachHang = khachHang_DAO.taoMaKhachHangMoi();
			String tenKhachHang = txtTenKhachHang.getText();
			String CCCD_HoChieu = txtCCCD_HoChieu.getText();
			String soDienThoai = txtSoDienThoai.getText();
			String email = txtEmail.getText();
			
			if(khachHang_DAO.kiemTraCCCD(CCCD_HoChieu)) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Lỗi");
		        alert.setHeaderText(null);
		        alert.setContentText("CCCD/Hộ chiếu đã tồn tại, có thể khách hàng đã là thành viên trước đó!");
		        File file = new File("image/canhBao.png");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(file.toURI().toString()));
                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
		        alert.showAndWait();
		        txtCCCD_HoChieu.requestFocus();
		        txtCCCD_HoChieu.selectAll();
		        List<KhachHang> danhSachKhachHang = khachHang_DAO.timKhachHangTheoCCCD_HoChieu(CCCD_HoChieu);
		     // Xóa dữ liệu cũ trong table (nếu có)
		        tbDanhSachKhachHang.getItems().clear();
		        
		        // Thêm dữ liệu mới vào table
		        tbDanhSachKhachHang.getItems().addAll(danhSachKhachHang);
		        
		        // Thiết lập giá trị cho các cột
		        colStt.setCellValueFactory(cellData -> 
		            new SimpleStringProperty(String.valueOf(tbDanhSachKhachHang.getItems().indexOf(cellData.getValue()) + 1)));
		        colMaKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaKhachHang()));
		        colTen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenKhachHang()));
		        colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
		        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		        colLoaiKhachHang.setCellValueFactory(cellData -> {
		            LoaiThanhVien loai = cellData.getValue().getLoaiThanhVien();
		            String displayValue = "";
		            if (loai == LoaiThanhVien.thanThiet) {
		                displayValue = "Thân thiết";
		            } else if (loai == LoaiThanhVien.vip) {
		                displayValue = "VIP";
		            }
		            return new SimpleStringProperty(displayValue);
		        });
		        return;
			}
			
			TrangThaiKhachHang trangThai = cboTrangThai.getValue();
			LoaiThanhVien loaiKhachHang = cboLoaiKhachHang.getValue();
			KhachHang kh = new KhachHang(maKhachHang, tenKhachHang, CCCD_HoChieu, soDienThoai, email, loaiKhachHang, trangThai);
			if(khachHang_DAO.themKhachHang(kh)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("Thông báo");
		        alert.setHeaderText(null);
		        alert.setContentText("Thêm khách hàng thành công!\nKhách hàng có mã: "+maKhachHang+"\nTên: "+tenKhachHang);
		        File file = new File("image/thanhCong.png");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	            stage.getIcons().add(new Image(file.toURI().toString()));
	            System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
		        alert.showAndWait();
		    
    			txtMaKhachHang.setText(kh.getMaKhachHang());
    			txtTenKhachHang.setText(kh.getTenKhachHang());
    			txtCCCD_HoChieu.setText(kh.getCCCD_HoChieu());
    			txtSoDienThoai.setText(kh.getSoDienThoai());
    			txtEmail.setText(kh.getEmail());
    			cboTrangThai.setValue(kh.getTrangThaiKhachHang());
    			cboLoaiKhachHang.setValue(kh.getLoaiThanhVien());
    			cboTrangThai.setEditable(true);
    			cboTrangThai.setDisable(false);
    			cboLoaiKhachHang.setEditable(true);
    			cboLoaiKhachHang.setDisable(false);
			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Lỗi");
		        alert.setHeaderText(null);
		        alert.setContentText("Lỗi khi thêm khách hàng!");
		        File file = new File("image/canhBao.png");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	            stage.getIcons().add(new Image(file.toURI().toString()));
	            System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
		        alert.showAndWait();
			}
		}
	}
	
	@FXML
	private boolean kiemTraTxt() {
		String tenKhachHang = txtTenKhachHang.getText();
		String CCCD_HoChieu = txtCCCD_HoChieu.getText();
		String soDienThoai = txtSoDienThoai.getText();
		String email = txtEmail.getText();
		
		String regexTen = "^[A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*(?: [A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*)+$";
		String regexCCCD_HoChieu = "^(\\d{12}|[A-Z]\\d{7})$";
		String regexSoDienThoai = "^(0|\\+84)(3|5|7|8|9)\\d{8}$";
		String regexEmail = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";

		if(tenKhachHang.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText(null);
	        alert.setContentText("Tên khách hàng không được rỗng");
	        File file = new File("image/canhBao.png");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(file.toURI().toString()));
            System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
	        alert.showAndWait();
	        txtTenKhachHang.requestFocus();
	        return false;
		}else {
			Pattern pt = Pattern.compile(regexTen);
			Matcher mc = pt.matcher(tenKhachHang);
			if(!mc.matches()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Lỗi");
		        alert.setHeaderText(null);
		        alert.setContentText("Tên khách hàng phải có ít nhất 2 từ, viết hoa chữ đầu; không chứa kí tự đặc biệt hoặc số!");
		        File file = new File("image/canhBao.png");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(file.toURI().toString()));
                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
		        alert.showAndWait();
		        txtTenKhachHang.requestFocus();
		        txtTenKhachHang.selectAll();
		        return false;
			}
		}
		
		
		if(CCCD_HoChieu.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText(null);
	        alert.setContentText("CCCD/Hộ chiếu của khách hàng không được rỗng");
	        File file = new File("image/canhBao.png");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(file.toURI().toString()));
            System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
	        alert.showAndWait();
	        txtCCCD_HoChieu.requestFocus();
	        return false;
		}else {
			Pattern pt = Pattern.compile(regexCCCD_HoChieu);
			Matcher mc = pt.matcher(CCCD_HoChieu);
			if(!mc.matches()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Lỗi");
		        alert.setHeaderText(null);
		        alert.setContentText("CCCD phải là 12 chữ số, Hộ chiếu phải là 8 kí tự trong đó 1 kí tự viết hoa kết hợp với 7 chữ số!");
		        File file = new File("image/canhBao.png");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(file.toURI().toString()));
                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
		        alert.showAndWait();
		        txtCCCD_HoChieu.requestFocus();
		        txtCCCD_HoChieu.selectAll();
		        return false;
			}	
		}
		
		if(soDienThoai.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText(null);
	        alert.setContentText("Số điện thoại của khách hàng không được rỗng");
	        File file = new File("image/canhBao.png");
	        if (file.exists()) {
	            try {
	                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	                stage.getIcons().add(new Image(file.toURI().toString()));
	                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
	            } catch (Exception e1) {
	                System.err.println("Lỗi khi tải ảnh: " + e1.getMessage());
	            }
	        } else {
	            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
	        }
	        alert.showAndWait();
	        txtSoDienThoai.requestFocus();
	        return false;
		}else {
			Pattern pt = Pattern.compile(regexSoDienThoai);
			Matcher mc = pt.matcher(soDienThoai);
			if(!mc.matches()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Lỗi");
		        alert.setHeaderText(null);
		        alert.setContentText("Số điện thoại bắt đầu bằng 0 hoặc +84, sau đó là 1 trong các số sau: 3, 5, 7, 8, 9; cuối cùng là 8 kí tự số!");
		        File file = new File("image/canhBao.png");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(file.toURI().toString()));
                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
		        alert.showAndWait();
		        txtSoDienThoai.requestFocus();
		        txtSoDienThoai.selectAll();
		        return false;
			}
		}
		
		if(email.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText(null);
	        alert.setContentText("Email của khách hàng không được rỗng");
	        File file = new File("image/canhBao.png");
	        if (file.exists()) {
	            try {
	                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	                stage.getIcons().add(new Image(file.toURI().toString()));
	                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
	            } catch (Exception e1) {
	                System.err.println("Lỗi khi tải ảnh: " + e1.getMessage());
	            }
	        } else {
	            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
	        }
	        alert.showAndWait();
	        txtEmail.requestFocus();
	        return false;
		}else {
			Pattern pt = Pattern.compile(regexEmail);
			Matcher mc = pt.matcher(email);
			if(!mc.matches()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Lỗi");
		        alert.setHeaderText(null);
		        alert.setContentText("Email không hợp lệ!");
		        File file = new File("image/canhBao.png");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(file.toURI().toString()));
                System.out.println("Đã tải icon từ: " + file.getAbsolutePath());
		        alert.showAndWait();
		        txtEmail.requestFocus();
		        txtEmail.selectAll();
		        return false;
			}
		}
		return true;
	}
	
	@FXML
	private Button btnCapNhat;
	
	@FXML
	private void btnCapNhatClicked() {
	    if (kiemTraTxt()) {
	        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
	        String maKhachHang = txtMaKhachHang.getText();
	        String tenKhachHang = txtTenKhachHang.getText();
	        String CCCD_HoChieu = txtCCCD_HoChieu.getText();
	        String soDienThoai = txtSoDienThoai.getText();
	        String email = txtEmail.getText();

	        // Xử lý cboTrangThai
	        TrangThaiKhachHang trangThai;
	        Object trangThaiValue = cboTrangThai.getValue();
	        if (trangThaiValue instanceof TrangThaiKhachHang) {
	            // Nếu getValue() trả về enum
	            trangThai = (TrangThaiKhachHang) trangThaiValue;
	        } else {
	            // Nếu getValue() trả về String (toString() của enum)
	            String trangThaiStr = trangThaiValue != null ? trangThaiValue.toString().trim() : "hoatDong";
	            if (trangThaiStr.equals("Vô hiệu hóa")) {
	                trangThai = TrangThaiKhachHang.voHieuHoa;
	            } else {
	                trangThai = TrangThaiKhachHang.hoatDong;
	            }
	        }

	        // Xử lý cboLoaiKhachHang
	        LoaiThanhVien loaiKhachHang;
	        Object loaiKhachHangValue = cboLoaiKhachHang.getValue();
	        if (loaiKhachHangValue instanceof LoaiThanhVien) {
	            // Nếu getValue() trả về enum
	            loaiKhachHang = (LoaiThanhVien) loaiKhachHangValue;
	        } else {
	            // Nếu getValue() trả về String (toString() của enum)
	            String loaiKhachHangStr = loaiKhachHangValue != null ? loaiKhachHangValue.toString().trim() : "thanThiet";
	            if (loaiKhachHangStr.equals("VIP")) {
	                loaiKhachHang = LoaiThanhVien.vip;
	            } else {
	                loaiKhachHang = LoaiThanhVien.thanThiet;
	            }
	        }

	        KhachHang kh = new KhachHang(maKhachHang, tenKhachHang, CCCD_HoChieu, soDienThoai, email, loaiKhachHang, trangThai);

	        // Hiển thị hộp thoại xác nhận
	        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
	        confirmAlert.setTitle("Xác nhận cập nhật");
	        confirmAlert.setHeaderText("Bạn có chắc chắn muốn cập nhật khách hàng này?");
	        confirmAlert.setContentText("Mã khách hàng: " + kh.getMaKhachHang() + "\nTên: " + kh.getTenKhachHang());

	        File confirmFile = new File("image/canhBao.png");
	        if (confirmFile.exists()) {
	            Stage confirmStage = (Stage) confirmAlert.getDialogPane().getScene().getWindow();
	            confirmStage.getIcons().add(new Image(confirmFile.toURI().toString()));
	            System.out.println("Đã tải icon từ: " + confirmFile.getAbsolutePath());
	        }

	        Optional<ButtonType> result = confirmAlert.showAndWait();

	        if (result.isPresent() && result.get() == ButtonType.OK) {
	            if (khachHang_DAO.capNhatKhachHang(kh)) {
	                Alert successAlert = new Alert(AlertType.INFORMATION);
	                successAlert.setTitle("Thông báo");
	                successAlert.setHeaderText(null);
	                successAlert.setContentText("Cập nhật thành công!");
	                File successFile = new File("image/thanhCong.png");
	                Stage successStage = (Stage) successAlert.getDialogPane().getScene().getWindow();
	                successStage.getIcons().add(new Image(successFile.toURI().toString()));
	                System.out.println("Đã tải icon từ: " + successFile.getAbsolutePath());
	                successAlert.showAndWait();

	                KhachHang selectedKhachHang = tbDanhSachKhachHang.getSelectionModel().getSelectedItem();
	                if (selectedKhachHang != null) {
	                    int selectedIndex = tbDanhSachKhachHang.getSelectionModel().getSelectedIndex();
	                    selectedKhachHang.setTenKhachHang(kh.getTenKhachHang());
	                    selectedKhachHang.setCCCD_HoChieu(kh.getCCCD_HoChieu());
	                    selectedKhachHang.setSoDienThoai(kh.getSoDienThoai());
	                    selectedKhachHang.setEmail(kh.getEmail());
	                    selectedKhachHang.setLoaiThanhVien(kh.getLoaiThanhVien());
	                    selectedKhachHang.setTrangThaiKhachHang(kh.getTrangThaiKhachHang());
	                    tbDanhSachKhachHang.getItems().set(selectedIndex, selectedKhachHang);
	                }
	            } else {
	                Alert errorAlert = new Alert(AlertType.ERROR);
	                errorAlert.setTitle("Lỗi");
	                errorAlert.setHeaderText(null);
	                errorAlert.setContentText("Lỗi khi cập nhật khách hàng!");
	                File errorFile = new File("image/canhBao.png");
	                Stage errorStage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
	                errorStage.getIcons().add(new Image(errorFile.toURI().toString()));
	                System.out.println("Đã tải icon từ: " + errorFile.getAbsolutePath());
	                errorAlert.showAndWait();
	            }
	        }
	    }
	}
}
