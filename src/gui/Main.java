package gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Khởi tạo
//    	new QuanLyBanVe_GUI(primaryStage);
//    	new Home_GUI(primaryStage);
//    	new ThanhToan_GUI(primaryStage);
        new QuanLyHoaDon_GUI(primaryStage);

        
        // Hiển thị giao diện quản lý bán vé
//        new QuanLyKhachHang_GUI(primaryStage);


        // Hiển thị cửa sổ
//    	new QuanLyTaiKhoan_GUI(primaryStage);
//    	new ThongKe_GUI(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}