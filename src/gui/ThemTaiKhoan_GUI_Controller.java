package gui;

import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;
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
        // Kiểm tra tên tài khoản
        if (txtTenTaiKhoan.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Lỗi", "Tên tài khoản không được để trống!");
            txtTenTaiKhoan.requestFocus();
            return;
        }
        
        // Kiểm tra tài khoản đã tồn tại chưa
        TaiKhoan_DAO taiKhoan_DAO = new TaiKhoan_DAO();
        if (taiKhoan_DAO.kiemTraTonTaiTaiKhoan(txtTenTaiKhoan.getText())) {
            showAlert(AlertType.WARNING, "Lỗi", "Tên tài khoản đã tồn tại, vui lòng nhập tên khác!");
            txtTenTaiKhoan.requestFocus();
            txtTenTaiKhoan.selectAll();
            return;
        }
        
        // Kiểm tra tên tài khoản 6 ký tự chữ/số
        String tenTaiKhoan = txtTenTaiKhoan.getText();
        if (tenTaiKhoan.length() <= 6 || !tenTaiKhoan.matches("[a-zA-Z0-9]+")) {
            showAlert(AlertType.ERROR, "Lỗi", "Tên tài khoản phải có đúng 6 ký tự (chỉ gồm chữ và số)!");
            txtTenTaiKhoan.requestFocus();
            txtTenTaiKhoan.selectAll();
            return;
        }
        
        // Kiểm tra mật khẩu
        if (txtPassword.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Lỗi", "Mật khẩu không được để trống!");
            txtPassword.requestFocus();
            return;
        }
        
        if (txtPassword.getText().length() < 8) {
            showAlert(AlertType.WARNING, "Lỗi", "Mật khẩu phải có ít nhất 8 ký tự!");
            txtPassword.requestFocus();
            txtPassword.selectAll();
            return;
        }
        
        // Kiểm tra nhập lại mật khẩu
        if (txtNhapLaiPassword.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Lỗi", "Chưa nhập lại mật khẩu!");
            txtNhapLaiPassword.requestFocus();
            return;
        }
        
        if (!txtPassword.getText().equals(txtNhapLaiPassword.getText())) {
            showAlert(AlertType.WARNING, "Lỗi", "Mật khẩu nhập lại không khớp!");
            txtNhapLaiPassword.requestFocus();
            txtNhapLaiPassword.selectAll();
            return;
        }
        
        // Nếu tất cả điều kiện hợp lệ, thực hiện thêm tài khoản
        TaiKhoan taiKhoanMoi = new TaiKhoan();
        taiKhoanMoi.setTenTaiKhoan(tenTaiKhoan);
        taiKhoanMoi.setMatKhau(txtPassword.getText());
        taiKhoanMoi.setMaNhanVien(maNhanVien);
        
        if (taiKhoan_DAO.themTaiKhoan(taiKhoanMoi)) {
            showAlert(AlertType.INFORMATION, "Thành công", "Thêm tài khoản thành công!");
            Stage stage = (Stage) btnThemTaiKhoan.getScene().getWindow();
            stage.close();
        } else {
            showAlert(AlertType.ERROR, "Lỗi", "Thêm tài khoản thất bại!");
        }
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