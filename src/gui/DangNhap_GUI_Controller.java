package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent; // Sửa import từ java.awt sang javafx
import javafx.stage.Stage;
import java.io.IOException;

import gui.QuenMatKhau_GUI;

public class DangNhap_GUI_Controller {
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Hyperlink lnkQMK;
    @FXML private Button btnDN;
    
    @FXML
    private void handleLogin(ActionEvent event) { // Thêm ActionEvent nếu cần
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin");
            return;
        }
        // Xử lý đăng nhập...
    }
    
    @FXML
    private void handleForgotPassword(ActionEvent event) {
        try {
            // Lấy stage hiện tại từ control
            Stage currentStage = (Stage) lnkQMK.getScene().getWindow();
            
            // Tạo instance mới của QuenMatKhau_GUI và truyền dữ liệu
            new QuenMatKhau_GUI(currentStage, txtUsername.getText());
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không thể mở màn hình quên mật khẩu");
            alert.showAndWait();
        }
    }
    
    private void showAlert(String title, String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}