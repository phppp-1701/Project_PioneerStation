package gui;

import entity.KhachHang;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
    	new QuanLyBanVe_GUI(primaryStage);
        primaryStage.show();
    }

}