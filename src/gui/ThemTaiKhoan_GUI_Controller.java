package gui;

import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ThemTaiKhoan_GUI_Controller {
    private String maNhanVien;
    
    @FXML
    private TextField txtMaNhanVien;
    @FXML
    private TextField txtTenNhanVien;
    @FXML
    private TextField txtTenTaiKhoan;  // Thêm TextField cho tên tài khoản
    @FXML
    private PasswordField txtPassword;     // Thêm TextField cho mật khẩu
    @FXML
    private Button btnThemTaiKhoan;
    @FXML
    private Button btnQuayLai;
    @FXML 
    private PasswordField txtNhapLaiPassword;

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }
    
    @FXML
    private void initialize() {
        // Khởi tạo các giá trị mặc định nếu cần
        System.out.println("Initialize chạy, maNhanVien: " + maNhanVien);
    }
    
    public void updateUI() {
        if (maNhanVien != null) {
            NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
            NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien);
            if (nv != null) {
                txtMaNhanVien.setText(nv.getMaNhanVien());
                txtTenNhanVien.setText(nv.getTenNhanVien());
                txtMaNhanVien.setEditable(false);
                txtMaNhanVien.setDisable(true);
                txtTenNhanVien.setEditable(false);
                txtTenNhanVien.setDisable(true);
            } else {
                System.err.println("Không tìm thấy nhân viên với mã: " + maNhanVien);
            }
        }
    }
    
    @FXML
    private void btnQuayLaiClicked() {
        // Đóng cửa sổ hiện tại
        Stage stage = (Stage) btnQuayLai.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void btnThemTaiKhoanClicked() {
    	TaiKhoan_DAO taiKhoan_DAO = new TaiKhoan_DAO();
        // Kiểm tra tên tài khoản không được rỗng
        if (txtTenTaiKhoan.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Lỗi", "Tên tài khoản không được để trống!");
            txtTenTaiKhoan.requestFocus();
            return;
        }else {
        	if(taiKhoan_DAO.kiemTraTonTaiTaiKhoan(txtTenTaiKhoan.getText().toString())) {
        		showAlert(AlertType.WARNING, "Lỗi", "Tên tài khoản đã tồn tại, vui lòng nhập tên khác!");
        		txtTenTaiKhoan.requestFocus();
        		txtTenNhanVien.selectAll();
        	}else {
                String tenTaiKhoan = txtTenTaiKhoan.getText();
                if (tenTaiKhoan.length() < 6 || !tenTaiKhoan.matches("[a-zA-Z0-9]+")) {
                    showAlert(AlertType.WARNING, "Lỗi", "Tên tài khoản phải có ít nhất 6 ký tự (chỉ gồm chữ và số)!");
                    txtTenTaiKhoan.requestFocus();
                    txtTenTaiKhoan.selectAll();
                    return;
                }
        	}
        }
        
        // Kiểm tra mật khẩu không được rỗng
        if (txtPassword.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Lỗi", "Mật khẩu không được để trống!");
            return;
        }
        
        //Kiểm tra có nhập lại mật khẩu chưa
        if(txtNhapLaiPassword.getText().isEmpty()) {
        	showAlert(AlertType.WARNING, "Lỗi", "Chưa nhập lại mật khẩu!");
        	return;
        }
        
        // Nếu tất cả điều kiện hợp lệ, thực hiện thêm tài khoản
        // (Thêm code xử lý thêm tài khoản vào database ở đây)
        showAlert(AlertType.INFORMATION, "Thành công", "Thêm tài khoản thành công!");
    }
    
    // Hiển thị thông báo
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}