package es.smileat.mantenimientos;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VerMaquinaController {
    public static PreventivoType toView;
    @FXML
    private Label lblId;
    @FXML
    private Label lblModelo;
    @FXML
    private Label lblTiempo;
    @FXML
    private Label lblSala;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Button btnMarcar;

    @FXML
    private void initialize(){
        Maquina maquina = toView.getMaquina();
        lblId.setText("ID: " + maquina.getId());
        lblModelo.setText("Modelo: " + maquina.getModelo());
        lblTiempo.setText("Tiempo previsto: " + String.valueOf(toView.getTiempo()));
        lblSala.setText("Sala: " + maquina.getSala());
        lblDescripcion.setText("Descripción: " + toView.getDescripcion());

        btnMarcar.setText("Marcar como realizado");
        btnMarcar.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Marcar mantenimiento");
            stage.initModality(Modality.APPLICATION_MODAL);
            try{
                stage.setScene(new Scene(App.loadFXML("marcarMantenimiento"), 400, 600));
            }catch(Exception ex){
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.showAndWait();
            }
            stage.show();
            btnMarcar.getScene().getWindow().hide();
        });
    }
}
