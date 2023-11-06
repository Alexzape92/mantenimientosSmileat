package es.smileat.mantenimientos;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class EditarMaquinaController {
    public static Maquina toUpdate;
    private MaquinaServices maquinaServices;
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

        txtId.setPromptText("ID: "+ toUpdate.getId());
        txtId.setEditable(false);
        txtModelo.setPromptText("Modelo: "+ toUpdate.getModelo());
        txtIncidencias.setPromptText("Incidencias: "+ String.valueOf(toUpdate.getIncidencias()));
        txtSala.setPromptText("Sala: "+ toUpdate.getSala());

        btnGuardar.setText("Guardar");
        btnGuardar.setOnAction(e -> guardar());
    }

    private void guardar(){
        try{
            String id = toUpdate.getId();
            String modelo = txtModelo.getText().isEmpty() ? toUpdate.getModelo() : txtModelo.getText();
            int incidencias = txtIncidencias.getText().isEmpty() ? toUpdate.getIncidencias() : Integer.parseInt(txtIncidencias.getText());
            String sala = txtSala.getText().isEmpty() ? toUpdate.getSala() : txtSala.getText();

            Maquina maquina = new Maquina(id, modelo, incidencias, sala);
            maquinaServices.modificarMaquina(maquina);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Máquina actualizada correctamente", ButtonType.OK);
            alert.showAndWait();
            App.setRoot("verMaquinas");
            btnGuardar.getScene().getWindow().hide();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
