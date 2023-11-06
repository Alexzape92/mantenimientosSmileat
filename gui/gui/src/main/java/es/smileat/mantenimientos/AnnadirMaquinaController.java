package es.smileat.mantenimientos;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AnnadirMaquinaController {
    private MaquinaServices maquinaServices;
    @FXML
    private Label lblTitulo;
    @FXML
    private ImageView imgAtras;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtIncidencias;
    @FXML
    private TextField txtSala;
    @FXML
    private Button btnGuardar;

    @FXML
    private void initialize(){
        try{
            maquinaServices = new MaquinaServices();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        lblTitulo.setText("AÑADIR MÁQUINA");
        lblTitulo.setFont(javafx.scene.text.Font.font(30));
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: #1E90FF");

        imgAtras.fitHeightProperty().set(50);
        imgAtras.fitWidthProperty().set(50);
        imgAtras.setOnMouseClicked(e -> {
            imgAtras.getScene().setCursor(javafx.scene.Cursor.DEFAULT); 
            App.setRoot("supervisor");
        });
        imgAtras.setOnMouseEntered(e -> imgAtras.getScene().setCursor(javafx.scene.Cursor.HAND));
        imgAtras.setOnMouseExited(e -> {
            if(imgAtras.getScene() != null) 
                imgAtras.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
        });

        txtId.setPromptText("ID (No se puede repetir)");
        txtModelo.setPromptText("Modelo");
        txtIncidencias.setPromptText("Incidencias");
        txtSala.setPromptText("Sala");

        btnGuardar.setText("Guardar");
        btnGuardar.setOnAction(e -> guardar());
    }

    private void guardar(){
        if(txtId.getText().isEmpty() || txtModelo.getText().isEmpty() || txtSala.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Excepto las incidencias, todos los campos son obligatorios", ButtonType.OK);
            alert.showAndWait();
        }
        else{
            try{
                String Id = txtId.getText();
                String Modelo = txtModelo.getText();
                int Incidencias = txtIncidencias.getText().isEmpty() ? 0 : Integer.parseInt(txtIncidencias.getText());
                String Sala = txtSala.getText();

                Maquina maquina = new Maquina(Id, Modelo, Incidencias, Sala);
                maquinaServices.registrarMaquina(maquina);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Máquina registrada correctamente", ButtonType.OK);
                alert.showAndWait();
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
}
