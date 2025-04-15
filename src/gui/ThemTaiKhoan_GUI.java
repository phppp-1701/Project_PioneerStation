package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ThemTaiKhoan_GUI {
    public ThemTaiKhoan_GUI(Stage primaryStage, String maNhanVien) {
        try {
            // Tải file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ThemTaiKhoan_GUI.fxml"));
            Parent root = loader.load();
            
            ThemTaiKhoan_GUI_Controller controller = loader.getController();
            if (controller == null) {
                throw new NullPointerException("Controller không được khởi tạo.");
            }
            controller.setMaNhanVien(maNhanVien); // Truyền maNhanVien
            controller.updateUI(); // Gọi phương thức để cập nhật giao diện (thêm mới)
            
            // Thiết lập Scene và Stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("PIONEER STATION - Thêm Tài Khoản");
            primaryStage.show();
            
        } catch (IOException e) {
            System.err.println("Không thể tải ThemTaiKhoan_GUI.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}