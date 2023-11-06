package es.smileat.mantenimientos;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class AnnadirPreventivoController {
    public static Maquina maquina;
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

        txtDescripcion.setPromptText("Descripción");
        txtFrecuencia.setPromptText("Frecuencia");
        txtTiempo.setPromptText("Tiempo");

        btnGuardar.setText("Guardar");
        btnGuardar.setOnAction(e -> guardar());
    }

    private void guardar(){
        if(txtDescripcion.getText().isEmpty() || txtFrecuencia.getText().isEmpty() || txtTiempo.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Rellene todos los campos", ButtonType.OK);
            alert.showAndWait();
        }
        else{
            try{
                int frecuencia = Integer.parseInt(txtFrecuencia.getText());
                int tiempo = Integer.parseInt(txtTiempo.getText());
                String descripcion = txtDescripcion.getText();

                PreventivoType preventivoType = new PreventivoType(maquina, frecuencia, tiempo, descripcion);
                preventivoTypeServices.registrarPreventivoType(preventivoType);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preventivo añadido correctamente", ButtonType.OK);
                alert.showAndWait();
                btnGuardar.getScene().getWindow().hide();
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
}
