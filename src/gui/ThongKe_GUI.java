package gui;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ThongKe_GUI {
    public ThongKe_GUI(Stage primaryStage, String maNhanVien) {
        try {
            // Tải file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ThongKe_GUI.fxml"));
            Parent root = loader.load();
            
            // Thiết lập scene và stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/gui/ThongKe_GUI.css").toExternalForm());
            primaryStage.setScene(scene);
            
            ThongKe_GUI_Controller controller = loader.getController();
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
            
            primaryStage.setTitle("PIONEER STATION - Thống Kê");
            primaryStage.show();
            
            // Có thể sử dụng maNhanVien ở đây nếu cần
            System.out.println("Mã nhân viên: " + maNhanVien);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể tải giao diện trang chủ: " + e.getMessage());
        }
    }
}