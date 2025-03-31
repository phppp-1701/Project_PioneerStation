package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Tạo và hiển thị màn hình đăng nhập
        new DangNhap_GUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args); // Khởi chạy JavaFX
    }
}