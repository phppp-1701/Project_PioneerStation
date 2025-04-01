package gui;

import java.io.File;

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
            Parent root = loader.load(getClass().getResource("/gui/DangNhap_GUI.fxml"));
            
            // Hoặc cách 2: nếu dùng getResource() thông thường
            // Parent root = FXMLLoader.load(getClass().getResource("/gui/resource/DangNhap_GUI.fxml"));
            
            Scene scene = new Scene(root);
            
            try {
                // Sử dụng đường dẫn tương đối từ thư mục gốc dự án
                File iconFile = new File("image/icon.png");
                Image icon = new Image(iconFile.toURI().toString());
                primaryStage.getIcons().add(icon);
            } catch (Exception e) {
                System.err.println("Không tải được icon: " + e.getMessage());
                e.printStackTrace();
            }
            
            primaryStage.setTitle("PIONEER STATION - Đăng nhập");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            System.err.println("LỖI KHỞI TẠO MÀN HÌNH ĐĂNG NHẬP:");
            e.printStackTrace();
            
            // Debug đường dẫn
            System.out.println("Đường dẫn thực tế tới FXML: " + 
                getClass().getResource("/gui/DangNhap_GUI.fxml"));
        }
    }
}