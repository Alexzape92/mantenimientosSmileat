package es.smileat.mantenimientos;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SupervisorController {
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblUsuarios;
    @FXML
    private Label lblBienvenida;
    @FXML
    private Label lblInfo;
    @FXML
    private ImageView imgCerrarsesion;
    @FXML
    private Label lblMaquinas;
    @FXML
    private Label lblMantenimientos;
    @FXML
    private Button btnVerMaquinas;
    @FXML
    private Button btnAnadirMaquina;
    @FXML  
    private Button btnVerPrograma;
    @FXML
    private Button btnAnadirMantenimiento;
    @FXML
    private Button btnVerMantenimientos;
    @FXML
    private Button btnVerUsuarios;

    @FXML
    private void initialize(){
        lblInfo.setText("SUPERVISOR");
        lblInfo.setFont(javafx.scene.text.Font.font(30));
        lblInfo.setTextFill(Color.web("#1E90FF"));
        lblInfo.setStyle("-fx-font-weight: bold");

        lblUsuario.setText(App.usuario.getNombre());
        lblUsuario.setFont(javafx.scene.text.Font.font(25));
        lblUsuario.setTextFill(Color.web("#ffa500"));
        lblUsuario.setStyle("-fx-font-weight: bold");

        lblBienvenida.setText("Hola, eres");
        lblBienvenida.setFont(javafx.scene.text.Font.font(25));

        imgCerrarsesion.fitHeightProperty().set(35);
        imgCerrarsesion.fitWidthProperty().set(35);
        imgCerrarsesion.setOnMouseClicked(e -> cerrarSesion());
        imgCerrarsesion.setOnMouseEntered(e -> imgCerrarsesion.getScene().setCursor(Cursor.HAND));
        imgCerrarsesion.setOnMouseExited(e -> imgCerrarsesion.getScene().setCursor(Cursor.DEFAULT));

        lblMaquinas.setText("MAQUINAS");
        lblMaquinas.setFont(javafx.scene.text.Font.font(30));
        lblMaquinas.setStyle("-fx-font-weight: bold");

        lblMantenimientos.setText("MANTENIMIENTOS");
        lblMantenimientos.setFont(javafx.scene.text.Font.font(30));
        lblMantenimientos.setStyle("-fx-font-weight: bold");

        lblUsuarios.setText("USUARIOS");
        lblUsuarios.setFont(javafx.scene.text.Font.font(30));
        lblUsuarios.setStyle("-fx-font-weight: bold");

        btnVerMaquinas.setText("Consultar máquinas");
        btnVerMaquinas.setPrefWidth(200);
        btnVerMaquinas.setPrefHeight(60);
        btnVerMaquinas.setStyle("-fx-font-weight: bold; -fx-background-color: #ffa500");
        btnVerMaquinas.setOnAction(e -> App.setRoot("verMaquinas"));

        btnAnadirMaquina.setText("Añadir máquina");
        btnAnadirMaquina.setPrefWidth(200);
        btnAnadirMaquina.setPrefHeight(60);
        btnAnadirMaquina.setStyle("-fx-font-weight: bold; -fx-background-color: #ffa500");
        btnAnadirMaquina.setOnAction(e -> App.setRoot("annadirMaquina"));

        btnVerPrograma.setText("Consultar programa");
        btnVerPrograma.setPrefWidth(200);
        btnVerPrograma.setPrefHeight(60);
        btnVerPrograma.setStyle("-fx-font-weight: bold; -fx-background-color: #ffa500");
        btnVerPrograma.setOnAction(e -> App.setRoot("verPrograma"));

        btnAnadirMantenimiento.setText("Añadir mantenimiento");
        btnAnadirMantenimiento.setPrefWidth(200);
        btnAnadirMantenimiento.setPrefHeight(60);
        btnAnadirMantenimiento.setStyle("-fx-font-weight: bold; -fx-background-color: #ffa500");

        btnVerMantenimientos.setText("Consultar mantenimientos");
        btnVerMantenimientos.setPrefWidth(200);
        btnVerMantenimientos.setPrefHeight(60);
        btnVerMantenimientos.setStyle("-fx-font-weight: bold; -fx-background-color: #ffa500");
        btnVerMantenimientos.setOnAction(e -> App.setRoot("verMantenimientos"));

        btnVerUsuarios.setText("Consultar usuarios");
        btnVerUsuarios.setPrefWidth(200);
        btnVerUsuarios.setPrefHeight(60);
        btnVerUsuarios.setStyle("-fx-font-weight: bold; -fx-background-color: #ffa500");
        btnVerUsuarios.setOnAction(e -> App.setRoot("verUsuarios"));
    }

    private void cerrarSesion(){
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setHeaderText("Cerrar sesión");
        confirmation.setContentText("¿Estás seguro de que quieres cerrar sesión?");

        ButtonType result = confirmation.showAndWait().orElse(ButtonType.CANCEL);

        if(result == ButtonType.OK){
            try{
                App.setRoot("home");
            }catch(Exception e){
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }
}
