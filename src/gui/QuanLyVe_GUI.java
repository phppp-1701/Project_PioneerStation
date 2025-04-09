package gui;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QuanLyVe_GUI {
    private String maNhanVien;
    
    public QuanLyVe_GUI(Stage primaryStage, String maNhanVien) {
        this.maNhanVien = maNhanVien;
        try {
            // Tải file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/QuanLyVe_GUI.fxml"));
            Parent root = loader.load();
            
            // Lấy Controller sau khi load và gán maNhanVien nếu cần
            QuanLyVe_GUI_Controller controller = loader.getController();
            if (controller != null) {
                controller.setMaNhanVien(maNhanVien); // Truyền mã nhân viên nếu controller cần
            }
            
            // Thiết lập scene và stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/gui/QuanLyVe_GUI.css").toExternalForm());
            primaryStage.setScene(scene);
            
            try {
                File iconFile = new File("image/icon.png");
                Image icon = new Image(iconFile.toURI().toString());
                primaryStage.getIcons().add(icon);
            } catch (Exception e) {
                System.err.println("Không tải được icon: " + e.getMessage());
            }
            
            primaryStage.setTitle("PIONEER STATION - Quản lý vé");
            primaryStage.setMaximized(true);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể tải giao diện quản lý vé: " + e.getMessage());
        }
    }
    
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
    
    public String getMaNhanVien() {
        return maNhanVien;
    }
}