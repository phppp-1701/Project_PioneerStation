package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
    	// Tạo và hiển thị màn hình đăng nhập
//    	new DangNhap_GUI(primaryStage);
    	//Tạo và hiển thị màn hình chính (Home) thay vì màn hình đăng nhập
    	new Home_GUI(primaryStage);
    	
//    	Tạo và hiển thị màn hình quản lý bán vé
    	new QuanLyBanVe_GUI(primaryStage);

    }

    public static void main(String[] args) {
        launch(args); // Khởi chạy JavaFX
    }
}