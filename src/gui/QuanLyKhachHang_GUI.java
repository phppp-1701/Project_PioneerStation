package gui;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QuanLyKhachHang_GUI {
    private final String maNhanVien;

    public QuanLyKhachHang_GUI(Stage primaryStage, String maNhanVien) {
        this.maNhanVien = maNhanVien;
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/QuanLyKhachHang_GUI.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/gui/QuanLyKhachHang_GUI.css").toExternalForm());
            primaryStage.setScene(scene);
            
            QuanLyKhachHang_GUI_Controller controller = loader.getController();
            if (controller == null) {
                throw new NullPointerException("Controller không được khởi tạo.");
            }
            controller.setMaNhanVien(maNhanVien);
            
            try {
                File iconFile = new File("image/icon.png");
                Image icon = new Image(iconFile.toURI().toString());
                primaryStage.getIcons().add(icon);
            } catch (Exception e) {
                System.err.println("Không tải được icon: " + e.getMessage());
                e.printStackTrace();
            }
            
            primaryStage.setTitle("PIONEER STATION - Quản lý khách hàng");
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể tải giao diện quản lý khách hàng: " + e.getMessage());
        }
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }
}