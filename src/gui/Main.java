package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        // Khởi tạo
        new QuanLyBanVe_GUI(primaryStage);
//    	new Home_GUI(primaryStage);
<<<<<<< HEAD

//    	new QuanLyBanVe_GUI(primaryStage);
    	new ThanhToan_GUI(primaryStage);
=======
//    	new ThanhToan_GUI(primaryStage);
        

        
        // Hiển thị giao diện quản lý bán vé
        new ThongKe_GUI(primaryStage);


>>>>>>> 91922e3fa88e8bc8e87c1b0f2da45f3e58dc1223
        // Hiển thị cửa sổ
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}