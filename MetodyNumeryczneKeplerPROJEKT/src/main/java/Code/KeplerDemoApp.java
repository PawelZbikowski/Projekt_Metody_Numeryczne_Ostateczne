package Code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KeplerDemoApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Version1_2.fxml"));
        //tworzenie odsłony - sceny
        Scene scene = new Scene(root);
        //umieszczenie na scenie
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trajectories of planets of Solar System");
        primaryStage.show();

    }
}
