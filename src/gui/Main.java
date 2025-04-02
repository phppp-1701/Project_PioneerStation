package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Khởi tạo
        new QuanLyBanVe_GUI(primaryStage);
//    	new Home_GUI(primaryStage);
        
        // Hiển thị cửa sổ
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}