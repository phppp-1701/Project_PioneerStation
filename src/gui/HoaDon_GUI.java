package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class HoaDon_GUI {
    private static final String FXML_PATH = "/gui/HoaDon_GUI.fxml";
    private static final String ICON_PATH = "/image/icon.png";
    private static final String WINDOW_TITLE = "PIONEER STATION - Hóa đơn";

    public HoaDon_GUI(Stage primaryStage, String maHoaDon) {
        try {
            // Load FXML with proper error handling
            FXMLLoader loader = new FXMLLoader();
            Parent root = loadFXML(loader);
            
            // Get the controller and pass maHoaDon
            HoaDon_GUI_Controller controller = loader.getController();
            controller.setMaHoaDon(maHoaDon);
            
            // Setup scene and stage
            setupStage(primaryStage, root);
            
        } catch (IOException e) {
            handleInitializationError(e);
        }
    }

    private Parent loadFXML(FXMLLoader loader) throws IOException {
        URL fxmlUrl = getClass().getResource(FXML_PATH);
        if (fxmlUrl == null) {
            throw new IOException("Không tìm thấy file FXML: " + FXML_PATH);
        }
        loader.setLocation(fxmlUrl);
        return loader.load();
    }

    private void setupStage(Stage stage, Parent root) {
        // Create and configure scene
        Scene scene = new Scene(root);
        
        // Load and set window icon
        loadWindowIcon(stage);
        
        // Configure stage properties
        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        stage.setResizable(true); // Invoice screen might need to be resizable
        stage.show();
    }

    private void loadWindowIcon(Stage stage) {
        try {
            // Tạo đường dẫn tới file icon
            File iconFile = new File("image/icon.png"); // Đường dẫn tương đối từ thư mục gốc dự án
            
            // Kiểm tra file có tồn tại không
            if (iconFile.exists()) {
                // Tạo Image từ file
                Image icon = new Image(iconFile.toURI().toString());
                stage.getIcons().add(icon);
            } else {
                System.err.println("Không tìm thấy file icon tại: " + iconFile.getAbsolutePath());
                
                // Thử load từ resources nếu không tìm thấy file
                tryLoadFromResources(stage);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải icon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void tryLoadFromResources(Stage stage) {
        try {
            URL resourceUrl = getClass().getResource(ICON_PATH);
            if (resourceUrl != null) {
                Image icon = new Image(resourceUrl.toString());
                stage.getIcons().add(icon);
                System.out.println("Đã tải icon từ resources");
            } else {
                System.err.println("Cũng không tìm thấy icon trong resources");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải icon từ resources: " + e.getMessage());
        }
    }

    private void handleInitializationError(Exception e) {
        System.err.println("LỖI KHỞI TẠO MÀN HÌNH HÓA ĐƠN:");
        e.printStackTrace();
        
        // Debug information
        System.out.println("Đường dẫn FXML tuyệt đối: " + 
            getClass().getResource(FXML_PATH));
        System.out.println("Đường dẫn icon tuyệt đối: " + 
            getClass().getResource(ICON_PATH));
        
        // You might want to show an alert to the user here
        // AlertHelper.showError("Lỗi khởi tạo", "Không thể tải màn hình hóa đơn");
    }
    
    private void moLaiFormHoaDon(String maHoaDon) {
        try {
            Stage hoaDonStage = new Stage();
            HoaDon_GUI hoaDonGUI = new HoaDon_GUI(hoaDonStage, maHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            showWarningAlert("Lỗi khi mở giao diện hóa đơn!", "image/canhBao.png");
        }
    }
    
    private void showWarningAlert(String message, String icon) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert, icon);
        alert.showAndWait();
    }
    
    private void setAlertIcon(Alert alert, String icon) {
        File file = new File(icon);
        if (file.exists()) {
            try {
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(file.toURI().toString()));
            } catch (Exception e) {
                // Ignore icon errors
            }
        }
    }
}