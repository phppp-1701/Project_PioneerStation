package gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/resource/DangNhap_GUI.fxml"));
            Scene scene = new Scene(root);
            
            Image icon = new Image("file:image/icon.png");
            primaryStage.getIcons().add(icon);
            
            primaryStage.setTitle("PIONEER STATION");
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}