package top.emptystack.jfinger.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("scratch.fxml"));
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);

        primaryStage.setTitle("Jfinger");
        primaryStage.setScene(new Scene(root));
//        primaryStage.setScene(new Scene(root, 600, 150));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
