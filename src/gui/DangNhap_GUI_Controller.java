package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent; // Sửa import này
import java.io.File;
import dao.TaiKhoan_DAO;
import javafx.scene.Node;

public class DangNhap_GUI_Controller {
    @FXML private TextField txtTenDangNhap;
    @FXML private PasswordField txtPassword;
    @FXML private Hyperlink lnkQuenMatKhau;
    @FXML private Button btnDangNhap;

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
    private void btnDangNhapClicked(ActionEvent event) { // Thêm ActionEvent
        if(kiemTraTxt()) {
        	String tenTaiKhoan = txtTenDangNhap.getText().toString();
        	TaiKhoan_DAO dao = new TaiKhoan_DAO();
        	String maNhanVien = dao.timMaNhanVienTheoTenTaiKhoan(tenTaiKhoan);
            // Lấy stage hiện tại của DangNhap_GUI
            Stage currentStage = (Stage) txtTenDangNhap.getScene().getWindow();
            currentStage.close();
            // Lấy stage hiện tại và mở Home_GUI
            currentStage = new Stage();
            new Home_GUI(currentStage, maNhanVien);
        }
    }
    
    private boolean kiemTraTxt() {
        TaiKhoan_DAO taiKhoan_DAO = new TaiKhoan_DAO();
        String tenDangNhap = txtTenDangNhap.getText();
        String password = txtPassword.getText();
        
        if(tenDangNhap.isEmpty()) {
            showWarningAlert("Bạn chưa nhập tên đăng nhập!", "image/canhBao.png");
            txtTenDangNhap.requestFocus();
            return false;
        } else if(!taiKhoan_DAO.kiemTraTonTaiTaiKhoan(tenDangNhap)) {
            showWarningAlert("Tên đăng nhập không tồn tại!", "image/canhBao.png");
            txtTenDangNhap.requestFocus();
            txtTenDangNhap.selectAll();
            return false;
        }
        
        if(password.isEmpty()) {
            showWarningAlert("Bạn chưa nhập mật khẩu!", "image/canhBao.png");
            txtPassword.requestFocus();
            return false;
        } else if(!taiKhoan_DAO.kiemTraMatKhau(tenDangNhap, password)) {
            showWarningAlert("Mật khẩu không đúng!", "image/canhBao.png");
            txtPassword.requestFocus();
            txtPassword.selectAll();
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void handleQuenMatKhau(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String username = txtTenDangNhap.getText();
        new QuenMatKhau_GUI(currentStage, username);
    }
}