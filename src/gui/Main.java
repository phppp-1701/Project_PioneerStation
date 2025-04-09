package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage; // Thêm biến static

    @Override
    public void start(Stage stage) {
        primaryStage = stage; // Lưu trữ stage
        new DangNhap_GUI(primaryStage);
        primaryStage.show();
    }

    // Thêm phương thức static để truy cập từ bất kỳ đâu
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}