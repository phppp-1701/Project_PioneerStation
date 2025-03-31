package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class DangNhap_GUI {
    public DangNhap_GUI(Stage primaryStage) {
        try {
            // Sửa đường dẫn FXML - cách 1: dùng getResourceAsStream
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResourceAsStream("resource/DangNhap_GUI.fxml"));
            
            // Hoặc cách 2: nếu dùng getResource() thông thường
            // Parent root = FXMLLoader.load(getClass().getResource("/gui/resource/DangNhap_GUI.fxml"));
            
            Scene scene = new Scene(root);
            
            // Thiết lập icon (sửa đường dẫn)
            try {
                Image icon = new Image(getClass().getResourceAsStream("/image/icon.png"));
                primaryStage.getIcons().add(icon);
            } catch (Exception e) {
                System.out.println("Không tải được icon: " + e.getMessage());
            }
            
            primaryStage.setTitle("PIONEER STATION");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            System.err.println("LỖI KHỞI TẠO MÀN HÌNH ĐĂNG NHẬP:");
            e.printStackTrace();
            
            // Debug đường dẫn
            System.out.println("Đường dẫn thực tế tới FXML: " + 
                getClass().getResource("resource/DangNhap_GUI.fxml"));
        }
    }
}