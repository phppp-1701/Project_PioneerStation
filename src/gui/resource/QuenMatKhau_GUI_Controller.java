package gui.resource;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resource/DangNhap_GUI.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showMessage(String message, String type) {
        lblMessage.getStyleClass().removeAll("error-message", "success-message");
        lblMessage.getStyleClass().add(type + "-message");
        lblMessage.setText(message);
    }
}