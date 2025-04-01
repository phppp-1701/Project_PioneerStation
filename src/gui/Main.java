package gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Lấy kích thước màn hình
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    
        // Đặt kích thước cửa sổ theo độ phân giải màn hình
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        

        // Hiển thị giao diện quản lý bán vé
        new QuanLyBanVe_GUI(primaryStage);

        // Hiển thị cửa sổ
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}