package es.smileat.mantenimientos;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class EditarPreventivoController {
    public static PreventivoType toUpdate;
    private PreventivoTypeServices preventivoTypeServices;
    @FXML
    private TextField txtFrecuencia;
    @FXML
    private TextField txtTiempo;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private Button btnGuardar;

    @FXML
    private void initialize(){
        try{
            preventivoTypeServices = new PreventivoTypeServices();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        txtDescripcion.setPromptText("Descripción: "+ toUpdate.getDescripcion());
        txtFrecuencia.setPromptText("Frecuencia: "+ String.valueOf(toUpdate.getFrecuencia()));
        txtTiempo.setPromptText("Tiempo: "+ String.valueOf(toUpdate.getTiempo()));

        btnGuardar.setText("Guardar");
        btnGuardar.setOnAction(e -> guardar());
    }

    private void guardar(){
        try{
            String id = toUpdate.getId();
            int frecuencia = txtFrecuencia.getText().isEmpty() ? toUpdate.getFrecuencia() : Integer.parseInt(txtFrecuencia.getText());
            int incidencias = txtTiempo.getText().isEmpty() ? toUpdate.getTiempo() : Integer.parseInt(txtTiempo.getText());
            String descripcion = txtDescripcion.getText().isEmpty() ? toUpdate.getDescripcion() : txtDescripcion.getText();

            PreventivoType preventivoType = new PreventivoType(id, toUpdate.getMaquina(), frecuencia, incidencias, descripcion);
            preventivoTypeServices.modificarPreventivoType(preventivoType);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preventivo actualizado correctamente", ButtonType.OK);
            alert.showAndWait();
            App.setRoot("verPreventivos");
            btnGuardar.getScene().getWindow().hide();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
