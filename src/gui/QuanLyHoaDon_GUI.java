package gui;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QuanLyHoaDon_GUI {
    private final String maNhanVien; // Field to store employee ID

    public QuanLyHoaDon_GUI(Stage primaryStage, String maNhanVien) {
        this.maNhanVien = maNhanVien; // Store the employee ID
        
        try {
            // Tải file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/QuanLyHoaDon_GUI.fxml"));
            Parent root = loader.load();
            
            // Thiết lập scene và stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/gui/QuanLyHoaDon_GUI.css").toExternalForm());
            primaryStage.setScene(scene);
            
            // Lấy Controller sau khi load và gán maNhanVien
            QuanLyHoaDon_GUI_Controller controller = loader.getController();
            if (controller == null) {
                throw new NullPointerException("Controller không được khởi tạo.");
            }
            controller.setMaNhanVien(maNhanVien);
            
            try {
                // Sử dụng đường dẫn tương đối từ thư mục gốc dự án
                File iconFile = new File("image/icon.png");
                Image icon = new Image(iconFile.toURI().toString());
                primaryStage.getIcons().add(icon);
            } catch (Exception e) {
                System.err.println("Không tải được icon: " + e.getMessage());
                e.printStackTrace();
            }
            
            primaryStage.setTitle("PIONEER STATION - Trang chủ");
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể tải giao diện trang chủ: " + e.getMessage());
        }
    }

    // Getter method to access the employee ID
    public String getMaNhanVien() {
        return maNhanVien;
    }
}