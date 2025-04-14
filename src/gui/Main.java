package gui;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage; // Thêm biến static

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        primaryStage = stage; // Lưu trữ stage
        new QuanLyBanVe_GUI(stage, "2022NV000001");
//          new DangNhap_GUI(stage);
//        new ThanhToan_GUI(stage, "2022NV000001", null);
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