package gui.resource;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent; // Sửa import từ java.awt sang javafx
import javafx.stage.Stage;
import java.io.IOException;

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
            // Sửa thành đường dẫn tuyệt đối từ classpath root
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resource/QuenMatKhau_GUI.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) lnkQMK.getScene().getWindow();
            stage.setScene(new Scene(root));
            
            QuenMatKhau_GUI_Controller controller = loader.getController();
            controller.setEmail(txtUsername.getText());
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể mở màn hình quên mật khẩu");
            
            // Debug - in ra đường dẫn thực tế
            System.err.println("Đường dẫn thử tải: " + getClass().getResource("/gui/resource/QuenMatKhau_GUI.fxml"));
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