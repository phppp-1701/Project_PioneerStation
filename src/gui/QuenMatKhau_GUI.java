package gui;

import gui.QuenMatKhau_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class QuenMatKhau_GUI {
    // Constructor thay thế cho phương thức static show
    public QuenMatKhau_GUI(Stage currentStage, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/QuenMatKhau_GUI.fxml"));
            Parent root = loader.load();
            
            // Thiết lập scene mới
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            
            // Truyền dữ liệu sang controller
            QuenMatKhau_GUI_Controller controller = loader.getController();
            controller.setEmail(username);
            
            // Có thể thêm các thiết lập khác cho stage nếu cần
            currentStage.setTitle("PIONEER STATION - Quên Mật Khẩu");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi tải màn hình quên mật khẩu:");
            System.err.println("Đường dẫn thử tải: " + getClass().getResource("/gui/QuenMatKhau_GUI.fxml"));
            
            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không thể mở màn hình quên mật khẩu");
            alert.showAndWait();
        }
    }
}