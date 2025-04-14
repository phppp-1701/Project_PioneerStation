package gui;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class QuanLyBanVe_GUI {
    public QuanLyBanVe_GUI(Stage primaryStage) {
//        try {
//            // Tải file FXML
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/QuanLyBanVe_GUI.fxml"));
//            Parent root = loader.load();
//            
//            // Thiết lập scene và stage
//            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("/gui/QuanLyBanVe_GUI.css").toExternalForm());
//            primaryStage.setScene(scene);
//            
//            try {
//                // Sử dụng đường dẫn tương đối từ thư mục gốc dự án
//                File iconFile = new File("image/icon.png");
//                Image icon = new Image(iconFile.toURI().toString());
//                primaryStage.getIcons().add(icon);
//            } catch (Exception e) {
//                System.err.println("Không tải được icon: " + e.getMessage());
//                e.printStackTrace();
//            }
//            
//            primaryStage.setTitle("PIONEER STATION - Quản lý bán vé");
//            
//            // Đặt cửa sổ ở chế độ Maximized (full màn hình nhưng vẫn hiển thị thanh tiêu đề)
//            primaryStage.setMaximized(true);
//            
//            // Tùy chọn: Ẩn nút phóng to/thu nhỏ nếu không muốn người dùng thay đổi kích thước
//            // primaryStage.setResizable(false);
//            
//            primaryStage.show();
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Không thể tải giao diện trang chủ: " + e.getMessage());
//        }
//    }
    
    /*Đừng sửa ở đây vì ở đây là code chỉnh theo kích thước màn hình nha mấy ní*/
	 try {
        // Load file FXML gốc (thiết kế trên máy lớn hơn)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuanLyBanVe_GUI.fxml"));
        Parent root = loader.load();

        // Kích thước thiết kế gốc (VD: máy 15.6 inch bạn thiết kế với 1540x795)
        double originalWidth = 1540;
        double originalHeight = 795;

        // Kích thước màn hình thật của máy hiện tại
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Tính tỷ lệ scale nhỏ nhất (đảm bảo vừa cả chiều ngang và dọc)
        double scaleX = screenWidth / originalWidth;
        double scaleY = screenHeight / originalHeight;
        double scale = Math.min(scaleX, scaleY);

        // Áp dụng scale lên toàn bộ giao diện
        Scale scaleTransform = new Scale(scale, scale, 0, 0);
        root.getTransforms().add(scaleTransform);

        // Tạo scene với kích thước gốc (vì đã scale trong transform)
        Scene scene = new Scene(root, originalWidth * scale, originalHeight * scale);

        primaryStage.setScene(scene);
        primaryStage.setTitle("PioneerStation");
        primaryStage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}