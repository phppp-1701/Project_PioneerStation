package gui;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import dao.KhachHang_DAO;
import entity.KhachHang;
import entity.KhachHang.LoaiThanhVien;
import entity.KhachHang.TrangThaiKhachHang;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

public class QuanLyKhachHang_GUI_Controller {
	@FXML
    private Button btnXuatDanhSach;
	
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
	private void btnXuatDanhSachClicked() {
	    try {
	        List<KhachHang> danhSachKhachHang = new KhachHang_DAO().getAllKhachHang();
	        txtTimSoDienThoai.setText("");
	        txtTimTenKhachHang.setText("");
	        if (danhSachKhachHang.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Thông báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Danh sách khách hàng trống!");
	            alert.showAndWait();
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
	                
	                // Hoặc Cách 2: Dùng InputStream
	                // Image image = new Image(new FileInputStream(file));
	                // stage.getIcons().add(image);
	                
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
	                
	                // Hoặc Cách 2: Dùng InputStream
	                // Image image = new Image(new FileInputStream(file));
	                // stage.getIcons().add(image);
	                
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
	private void initialize() {
	    // Load dữ liệu cho ComboBox loại khách hàng
	    cboLoaiKhachHang.getItems().setAll(LoaiThanhVien.values());
	    cboLoaiKhachHang.setValue(LoaiThanhVien.thanThiet); // Giá trị mặc định
	    
	    // Load dữ liệu cho ComboBox trạng thái
	    cboTrangThai.getItems().setAll(TrangThaiKhachHang.values());
	    cboTrangThai.setValue(TrangThaiKhachHang.hoatDong); // Giá trị mặc định
	    
	    cboTrangThai.setEditable(false);
	    cboTrangThai.setDisable(true);
		cboLoaiKhachHang.setEditable(false);
		cboLoaiKhachHang.setDisable(true);
	    
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
	}
	
	@FXML
	private Button btnThemKhachHang;
}
