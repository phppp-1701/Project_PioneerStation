package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        // Hiển thị giao diện quản lý bán vé
        new ThongKe_GUI(primaryStage);

        // Hiển thị cửa sổ
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}