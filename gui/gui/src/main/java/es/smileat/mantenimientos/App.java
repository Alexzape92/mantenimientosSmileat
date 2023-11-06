package es.smileat.mantenimientos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Usuario usuario;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"), 1024, 768);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Mantenimientos Smileat");
        stage.show();
    }

    static void setRoot(String fxml) {
        try{
            scene.setRoot(loadFXML(fxml));
        }catch(Exception e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}