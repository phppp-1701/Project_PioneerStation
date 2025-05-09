package gui;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import entity.VeTam;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ThanhToan_GUI {
    public ThanhToan_GUI(Stage stage, String maNhanVien, List<VeTam> danhSachVeXacNhan) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ThanhToan_GUI.fxml"));
        AnchorPane root = loader.load();

        ThanhToan_GUI_Controller controller = loader.getController();
        controller.setMaNhanVien(maNhanVien);
        controller.setDanhSachVeXacNhan(danhSachVeXacNhan);
        controller.initializeData();

        try {
            File iconFile = new File("image/icon.png");
            Image icon = new Image(iconFile.toURI().toString());
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Không tải được icon: " + e.getMessage());
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Thanh Toán");
        stage.show();
    }
}