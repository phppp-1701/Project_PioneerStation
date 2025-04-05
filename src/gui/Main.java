package gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
<<<<<<< HEAD

        // Khởi tạo
//      new QuanLyBanVe_GUI(primaryStage);
//    	new Home_GUI(primaryStage);

    	new ThanhToan_GUI(primaryStage);



//    	new QuanLyBanVe_GUI(primaryStage);
//    	new ThanhToan_GUI(primaryStage);

        

        
        // Hiển thị giao diện quản lý bán vé
//        new ThongKe_GUI(primaryStage);
=======
        // Lấy kích thước màn hình
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

    
        // Đặt kích thước cửa sổ theo độ phân giải màn hình
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        

        // Đặt kích thước cửa sổ theo độ phân giải màn hình
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());

        // Thiết lập chế độ full màn hình
        primaryStage.setFullScreen(true);
        
        // Tùy chọn: Hiển thị thông báo thoát fullscreen (ấn ESC để thoát)
        primaryStage.setFullScreenExitHint("");

        // Hiển thị giao diện quản lý bán vé
        new QuanLyVe_GUI(primaryStage);
>>>>>>> origin/Tuan

        // Hiển thị cửa sổ
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}