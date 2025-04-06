package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class QuanLyNhanVien_GUI {
    public QuanLyNhanVien_GUI(Stage primaryStage) {
        try {
            // Tải file FXML cho giao diện quản lý nhân viên
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/QuanLyNhanVien_GUI.fxml"));
            Parent root = loader.load();
            
            // Thiết lập scene và stage
            Scene scene = new Scene(root);
            // Thêm file CSS nếu có
            scene.getStylesheets().add(getClass().getResource("/gui/QuanLyNhanVien_GUI.css").toExternalForm());
            primaryStage.setScene(scene);
            
            // Thiết lập icon cho cửa sổ
            try {
                File iconFile = new File("image/icon.png");
                Image icon = new Image(iconFile.toURI().toString());
                primaryStage.getIcons().add(icon);
            } catch (Exception e) {
                System.err.println("Không tải được icon: " + e.getMessage());
                e.printStackTrace();
            }
            
            primaryStage.setTitle("PIONEER STATION - Quản lý nhân viên");
            
            // Thiết lập chế độ Maximized (full màn hình)
            primaryStage.setMaximized(true);
            
            // Hiển thị cửa sổ
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể tải giao diện quản lý nhân viên: " + e.getMessage());
        }
    }
}
