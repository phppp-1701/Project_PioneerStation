package gui;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QuanLyBanVe_GUI {
    public QuanLyBanVe_GUI(Stage primaryStage) {
        try {
            // Tải file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/QuanLyBanVe_GUI.fxml"));
            Parent root = loader.load();
            
            // Thiết lập scene và stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/gui/QuanLyBanVe_GUI.css").toExternalForm());
            primaryStage.setScene(scene);
            
            try {
                // Sử dụng đường dẫn tương đối từ thư mục gốc dự án
                File iconFile = new File("image/icon.png");
                Image icon = new Image(iconFile.toURI().toString());
                primaryStage.getIcons().add(icon);
            } catch (Exception e) {
                System.err.println("Không tải được icon: " + e.getMessage());
                e.printStackTrace();
            }
            
            primaryStage.setTitle("PIONEER STATION - Quản lý bán vé");
            
            // Đặt cửa sổ ở chế độ Maximized (full màn hình nhưng vẫn hiển thị thanh tiêu đề)
            primaryStage.setMaximized(true);
            
            // Tùy chọn: Ẩn nút phóng to/thu nhỏ nếu không muốn người dùng thay đổi kích thước
            // primaryStage.setResizable(false);
            
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể tải giao diện trang chủ: " + e.getMessage());
        }
    }
}