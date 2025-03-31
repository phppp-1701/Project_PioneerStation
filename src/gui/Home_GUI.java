package gui;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

public class Home_GUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Lấy thông tin màn hình chính
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // In ra kích thước màn hình
        System.out.println("Width: " + bounds.getWidth());
        System.out.println("Height: " + bounds.getHeight());
        System.out.println("Top-Left X: " + bounds.getMinX());
        System.out.println("Top-Left Y: " + bounds.getMinY());

    }
}