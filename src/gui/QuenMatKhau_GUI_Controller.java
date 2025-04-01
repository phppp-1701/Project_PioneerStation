package gui;

import gui.DangNhap_GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class QuenMatKhau_GUI_Controller {
    @FXML private TextField txtEmail;
    @FXML private Label lblMessage;
    
    public void setEmail(String email) {
        if (email != null && !email.isEmpty()) {
            txtEmail.setText(email);
        }
    }
    
    @FXML
    private void handleGuiYeuCau() {
        String email = txtEmail.getText().trim();
        
        if (email.isEmpty()) {
            showMessage("Vui lòng nhập email/username", "error");
            return;
        }
        
        // Gửi yêu cầu reset mật khẩu (giả lập)
        showMessage("Yêu cầu đã được gửi đến " + email, "success");
        
        // Trong thực tế: gọi service gửi email reset mật khẩu
    }
    
    @FXML
    private void handleQuayLai() {
        try {
            // Lấy stage hiện tại từ component nào đó trong scene
            Stage currentStage = (Stage) txtEmail.getScene().getWindow();
            
            // Tạo mới instance DangNhap_GUI và truyền stage hiện tại
            new DangNhap_GUI(currentStage);
            
        } catch (Exception e) {
            System.err.println("Lỗi khi quay lại màn hình đăng nhập: " + e.getMessage());
            e.printStackTrace();
            
            // Hiển thị thông báo lỗi cho người dùng
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi Hệ Thống");
            alert.setHeaderText(null);
            alert.setContentText("Không thể quay lại màn hình đăng nhập");
            alert.showAndWait();
        }
    }
    
    private void showMessage(String message, String type) {
        lblMessage.getStyleClass().removeAll("error-message", "success-message");
        lblMessage.getStyleClass().add(type + "-message");
        lblMessage.setText(message);
    }
}