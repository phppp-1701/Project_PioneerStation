package gui;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
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
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QuanLyNhanVien_GUI_Controller {
    @FXML
    private Button btnXuatDanhSach;
    
    @FXML
    private TableView<NhanVien> tbDanhSachNhanVien;
    
    @FXML
    private TableColumn<NhanVien, String> colStt;
    
    @FXML 
    private TableColumn<NhanVien, String> colMaNV;
    
    @FXML 
    private TableColumn<NhanVien, String> colTenNV;
    
    @FXML
    private TableColumn<NhanVien, String> colNgaySinh;
    
    @FXML
    private TableColumn<NhanVien, String> colSoDienThoai;
    
    @FXML
    private TableColumn<NhanVien, String> colEmail;
    
    @FXML 
    private TableColumn<NhanVien, String> colGioiTinh;
    
    @FXML 
    private TableColumn<NhanVien, String> colCCCD;
    
    @FXML 
    private TableColumn<NhanVien, String> colChucVu;
    
    @FXML 
    private TableColumn<NhanVien, String> colTrangThai;
    
    @FXML
    private void btnXuatDanhSachClicked() {
        try {
            List<NhanVien> danhSachNhanVien = new NhanVien_DAO().getAllNhanVien();
            if (danhSachNhanVien.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Danh sách nhân viên trống!");
                alert.showAndWait();
                return;
            }
            
            // Xóa dữ liệu cũ trong table (nếu có)
            tbDanhSachNhanVien.getItems().clear();
            
            // Thêm dữ liệu mới vào table
            tbDanhSachNhanVien.getItems().addAll(danhSachNhanVien);
            
            // Thiết lập giá trị cho các cột
            colStt.setCellValueFactory(cellData -> 
                new SimpleStringProperty(String.valueOf(tbDanhSachNhanVien.getItems().indexOf(cellData.getValue()) + 1)));
            colMaNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaNhanVien()));
            colTenNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenNhanVien()));
            colNgaySinh.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNgaySinh().toString()));
            colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
            colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            colGioiTinh.setCellValueFactory(cellData -> {
                GioiTinh gioiTinh = cellData.getValue().getGioiTinh();
                String displayValue = gioiTinh == GioiTinh.nam ? "Nam" : "Nữ";
                return new SimpleStringProperty(displayValue);
            });
            colCCCD.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCccd_HoChieu()));
            colChucVu.setCellValueFactory(cellData -> {
                ChucVu chucVu = cellData.getValue().getChucVu();
                String displayValue = "";
                if (chucVu == ChucVu.quanLy) {
                    displayValue = "Quản lý";
                } else if (chucVu == ChucVu.banVe) {
                    displayValue = "Bán vé";
                }
                return new SimpleStringProperty(displayValue);
            });
            colTrangThai.setCellValueFactory(cellData -> {
                TrangThaiNhanVien trangThaiNhanVien = cellData.getValue().getTrangThaiNhanVien();
                String displayValue = "";
                if (trangThaiNhanVien == TrangThaiNhanVien.hoatDong) {
                    displayValue = "Hoạt động";
                } else if (trangThaiNhanVien == TrangThaiNhanVien.voHieuHoa) {
                    displayValue = "Vô hiệu hóa";
                    

                }
                return new SimpleStringProperty(displayValue);
            });
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Không thể tải danh sách nhân viên!");
        }
    }
    @FXML
    private Button btnTim;

    @FXML
    private TextField txtTimTenNhanVien;

    @FXML
    private TextField txtTimSoDienThoai;

    @FXML
    private void btnTimClicked() {
        // Lấy giá trị từ các TextField
        String tenNhanVien = txtTimTenNhanVien.getText().trim();
        String soDienThoai = txtTimSoDienThoai.getText().trim();
        
        // Kiểm tra nếu cả hai trường đều trống
        if (tenNhanVien.isEmpty() && soDienThoai.isEmpty()) {
            showWarningAlert("Vui lòng nhập tên nhân viên hoặc số điện thoại để tìm kiếm!");
            return;
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
                showInformationAlert("Không tìm thấy nhân viên phù hợp!");
                tbDanhSachNhanVien.getSelectionModel().selectAll();
                // Di chuyển chuột đến dòng đầu tiên (hoặc dòng nào bạn muốn)
                tbDanhSachNhanVien.scrollTo(0); 
         
                return;
            }
            // Hiển thị kết quả trên TableView
            tbDanhSachNhanVien.getItems().clear();
            tbDanhSachNhanVien.setItems(FXCollections.observableArrayList(ketQuaTimKiem));
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert);
        alert.showAndWait();
    }

    private void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert);
        alert.showAndWait();
    }

    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert);
        alert.showAndWait();
    }

    private void setAlertIcon(Alert alert) {
        File file = new File("image/canhBao.png");
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
    private void initialize() {
        // Thiết lập ComboBox chức vụ và trạng thái nhân viên
    	cboGioiTinh.getItems().setAll(GioiTinh.values());
    	cboTrangThaiNhanVien.setDisable(true);
    	
        cboChucVu.getItems().setAll(ChucVu.values());
        cboChucVu.setValue(ChucVu.banVe); 
        cboChucVu.setEditable(false);
        cboChucVu.setDisable(true);

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
                    cboChucVu.setEditable(true);
                    cboChucVu.setDisable(false);
                    cboTrangThaiNhanVien.setEditable(true);
                    cboTrangThaiNhanVien.setDisable(false);
                }
            }
        });
    }

	@FXML
	private Button btnThemNhanVien;
	
	@FXML
	private void btnThemNhanVienClicked() {
	    if (!txtMaNV.getText().trim().isEmpty()) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText(null);
	        alert.setContentText("Bạn đang chọn một nhân viên, vui lòng làm rỗng để thêm!");
	        File file = new File("image/canhBao.png");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image(file.toURI().toString()));
	        alert.showAndWait();
	        return;
	    }

	    if (kiemTraTxt()) {
	        NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();

	        String maNhanVien = nhanVien_DAO.taoMaNhanVienMoi();
	        String tenNhanVien = txtTenNV.getText();

	        // Lấy ngày sinh
	        LocalDate ngaySinh = datePickerNgaySinh.getValue();
	        if (ngaySinh == null) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Lỗi");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng chọn ngày sinh!");
	            File file = new File("image/canhBao.png");
	            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	            stage.getIcons().add(new Image(file.toURI().toString()));
	            alert.showAndWait();
	            return;
	        }

	        GioiTinh gioiTinh = cboGioiTinh.getValue();
	        String cccd = txtCCCD.getText();
	        String sdt = txtSoDienThoai.getText();
	        String email = txtEmail.getText();
	        ChucVu chucVu = cboChucVu.getValue();
	        TrangThaiNhanVien trangThai = cboTrangThaiNhanVien.getValue();
	        

	        if (nhanVien_DAO.kiemTraCCCD(cccd)) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Lỗi");
	            alert.setHeaderText(null);
	            alert.setContentText("CCCD đã tồn tại. Nhân viên có thể đã tồn tại trong hệ thống!");
	            File file = new File("image/canhBao.png");
	            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	            stage.getIcons().add(new Image(file.toURI().toString()));
	            alert.showAndWait();

	            txtCCCD.requestFocus();
	            txtCCCD.selectAll();

	            List<NhanVien> danhSachTrung = nhanVien_DAO.timNhanVienTheoCCCD_HoChieu(cccd);
	            tbDanhSachNhanVien.getItems().clear();
	            tbDanhSachNhanVien.getItems().addAll(danhSachTrung);

	            colStt.setCellValueFactory(cellData -> 
	                new SimpleStringProperty(String.valueOf(tbDanhSachNhanVien.getItems().indexOf(cellData.getValue()) + 1)));
	            colMaNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaNhanVien()));
	            colTenNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenNhanVien()));
	            colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
	            colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
	            colChucVu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChucVu().toString()));
	            colTrangThai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrangThaiNhanVien().toString()));
	            return;
	        }

	        // Tạo đối tượng nhân viên
	        NhanVien nv = new NhanVien(maNhanVien, tenNhanVien, ngaySinh, gioiTinh, cccd, chucVu, trangThai, sdt, email, email);

	        try {
				if (nhanVien_DAO.themNhanVien(nv)) {
				    Alert alert = new Alert(Alert.AlertType.INFORMATION);
				    alert.setTitle("Thông báo");
				    alert.setHeaderText(null);
				    alert.setContentText("Thêm nhân viên thành công!\nMã: " + maNhanVien + "\nTên: " + tenNhanVien);
				    File file = new File("image/thanhCong.png");
				    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				    stage.getIcons().add(new Image(file.toURI().toString()));
				    alert.showAndWait();

				    // Hiển thị lại thông tin
				    txtMaNV.setText(nv.getMaNhanVien());
				    txtTenNV.setText(nv.getTenNhanVien());
				    txtCCCD.setText(nv.getCccd_HoChieu());
				    txtSoDienThoai.setText(nv.getSoDienThoai());
				    txtEmail.setText(nv.getEmail());
				    datePickerNgaySinh.setValue(ngaySinh);
				    cboGioiTinh.setValue(nv.getGioiTinh());
				    cboChucVu.setValue(nv.getChucVu());
				    cboTrangThaiNhanVien.setValue(nv.getTrangThaiNhanVien());
				    cboChucVu.setDisable(false);
				    cboTrangThaiNhanVien.setDisable(false);
				} else {
				    Alert alert = new Alert(Alert.AlertType.ERROR);
				    alert.setTitle("Lỗi");
				    alert.setHeaderText(null);
				    alert.setContentText("Lỗi khi thêm nhân viên!");
				    File file = new File("image/canhBao.png");
				    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				    stage.getIcons().add(new Image(file.toURI().toString()));
				    alert.showAndWait();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	
	@FXML
	private boolean kiemTraTxt() {
		String tenNhanVien = txtTenNV.getText();
		String CCCD_HoChieu = txtCCCD.getText();
		String soDienThoai = txtSoDienThoai.getText();
		String email = txtEmail.getText();

		String regexTen = "^[A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*(?: [A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*)+$";
		String regexCCCD_HoChieu = "^(\\d{12}|[A-Z]\\d{7})$";
		String regexSoDienThoai = "^(0|\\+84)(3|5|7|8|9)\\d{8}$";
		String regexEmail = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";

		if (tenNhanVien.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(null);
			alert.setContentText("Tên nhân viên không được rỗng");
			File file = new File("image/canhBao.png");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(file.toURI().toString()));
			alert.showAndWait();
			txtTenNV.requestFocus();
			return false;
		} else {
			Pattern pt = Pattern.compile(regexTen);
			Matcher mc = pt.matcher(tenNhanVien);
			if (!mc.matches()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Lỗi");
				alert.setHeaderText(null);
				alert.setContentText("Tên nhân viên phải có ít nhất 2 từ, viết hoa chữ đầu; không chứa kí tự đặc biệt hoặc số!");
				File file = new File("image/canhBao.png");
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(file.toURI().toString()));
				alert.showAndWait();
				txtTenNV.requestFocus();
				txtTenNV.selectAll();
				return false;
			}
		}
		return true;
	}



    // Xử lý cập nhật nhân viên
	@FXML
	private void btnCapNhatClicked() {
	    if (kiemTraTxt()) {
	        NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
	        String maNhanVien = txtMaNV.getText().trim();
	        String tenNhanVien = txtTenNV.getText();

	        // Lấy ngày sinh
	        LocalDate ngaySinh = datePickerNgaySinh.getValue();
	        if (ngaySinh == null) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Lỗi");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng chọn ngày sinh!");
	            File file = new File("image/canhBao.png");
	            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	            stage.getIcons().add(new Image(file.toURI().toString()));
	            alert.showAndWait();
	            return;
	        }
	        

	        GioiTinh gioiTinh = cboGioiTinh.getValue();
	        String cccd = txtCCCD.getText();
	        String sdt = txtSoDienThoai.getText();
	        String email = txtEmail.getText();
	        ChucVu chucVu = cboChucVu.getValue();
	        TrangThaiNhanVien trangThai = cboTrangThaiNhanVien.getValue();

	        // Tạo đối tượng nhân viên mới
	        NhanVien nv = new NhanVien(maNhanVien, tenNhanVien, ngaySinh, gioiTinh, cccd, chucVu, trangThai, sdt, email, email);

	        // Xác nhận cập nhật
	        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
	        confirm.setTitle("Xác nhận");
	        confirm.setHeaderText("Bạn có chắc muốn cập nhật thông tin nhân viên này?");
	        confirm.setContentText("Mã NV: " + maNhanVien + "\nTên NV: " + tenNhanVien);
	        File file = new File("image/canhBao.png");
	        if (file.exists()) {
	            Stage stage = (Stage) confirm.getDialogPane().getScene().getWindow();
	            stage.getIcons().add(new Image(file.toURI().toString()));
	        }

	        Optional<ButtonType> result = confirm.showAndWait();
	        if (result.isPresent() && result.get() == ButtonType.OK) {
	            if (nhanVien_DAO.capNhatNhanVien(nv)) {
	                Alert success = new Alert(Alert.AlertType.INFORMATION);
	                success.setTitle("Thành công");
	                success.setHeaderText(null);
	                success.setContentText("Cập nhật nhân viên thành công!");
	                File successFile = new File("image/thanhCong.png");
	                if (successFile.exists()) {
	                    Stage stage = (Stage) success.getDialogPane().getScene().getWindow();
	                    stage.getIcons().add(new Image(successFile.toURI().toString()));
	                }
	                success.showAndWait();

	                // Cập nhật lại dòng trong TableView
	                NhanVien selected = tbDanhSachNhanVien.getSelectionModel().getSelectedItem();
	                if (selected != null) {
	                    int index = tbDanhSachNhanVien.getSelectionModel().getSelectedIndex();
	                    selected.setTenNhanVien(nv.getTenNhanVien());
	                    selected.setGioiTinh(nv.getGioiTinh());
	                    selected.setNgaySinh(nv.getNgaySinh());
	                    selected.setSoDienThoai(nv.getSoDienThoai());
	                    selected.setChucVu(nv.getChucVu());
	                    tbDanhSachNhanVien.getItems().set(index, selected);
	                }

	            } else {
	                Alert error = new Alert(Alert.AlertType.ERROR);
	                error.setTitle("Lỗi");
	                error.setHeaderText(null);
	                error.setContentText("Cập nhật nhân viên thất bại!");
	                File errorFile = new File("image/canhBao.png");
	                if (errorFile.exists()) {
	                    Stage stage = (Stage) error.getDialogPane().getScene().getWindow();
	                    stage.getIcons().add(new Image(errorFile.toURI().toString()));
	                }
	                error.showAndWait();
	            }
	        }
	    }
	}


    
    @FXML
	private void btnLamRongClicked() {
		txtSoDienThoai.setText("");
		txtMaNV.setText("");
		txtTenNV.setText("");
		datePickerNgaySinh.setValue(null);
		txtCCCD.setText("");
		txtEmail.setText("");
		cboGioiTinh.setValue(null);
		cboChucVu.setValue(null);
        cboTrangThaiNhanVien.setValue(null);
	}
    
    // Xóa form nhập liệu
    private void xoaForm() {
        txtMaNV.clear();
        txtTenNV.clear();
        datePickerNgaySinh.setValue(null);
        cboGioiTinh.setValue(null);
        txtCCCD.clear();
        cboChucVu.setValue(null);
        cboTrangThaiNhanVien.setValue(null);
    }
}