package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HoanHuyVe_GUI {
    public HoanHuyVe_GUI(Stage primaryStage) {
        try {
            // Tải file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HoanHuyVe_GUI.fxml"));
            Parent root = loader.load();
            
            // Thiết lập scene và stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/gui/HoanHuyVe_GUI.css").toExternalForm());
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
            primaryStage.setTitle("PIONEER STATION - Trang chủ");
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể tải giao diện trang chủ: " + e.getMessage());
        }
    }
}