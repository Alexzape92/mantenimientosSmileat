package es.smileat.mantenimientos;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class MarcarMantenimientoController {
    private UsuarioServices usuarioServices;
    private MaquinaServices maquinaServices;
    private MantenimientoServices mantenimientoServices;
    @FXML
    private TextField txtFecha;
    @FXML
    private ChoiceBox<ResultadoType> cbResultado;
    @FXML
    private TextField txtComentarios;
    @FXML
    private ChoiceBox<String> cbEmpleado;
    @FXML
    private ChoiceBox<String> cbSupervisor;
    @FXML
    private TextField txtTiempo;
    @FXML
    private TextField txtMaquina;
    @FXML
    private ChoiceBox<String> cbTipo;
    @FXML
    private Button btnGuardar;

    @FXML
    private void initialize(){
        try{
            usuarioServices = new UsuarioServices();
            maquinaServices = new MaquinaServices();
            mantenimientoServices = new MantenimientoServices();

            txtFecha.setPromptText(LocalDate.now().toString());
            txtFecha.setEditable(false);

            cbResultado.getItems().addAll(ResultadoType.values());
            cbResultado.getSelectionModel().selectFirst();
            
            txtComentarios.setPromptText("Comentarios");

            cbEmpleado.getItems().addAll(usuarioServices.loadAllEmpleados().stream().map(Usuario::getNombre).sorted().collect(Collectors.toList()));
            cbEmpleado.getSelectionModel().selectFirst();

            cbSupervisor.getItems().add(App.usuario.getNombre());
            cbSupervisor.getSelectionModel().selectFirst();

            txtTiempo.setPromptText("Tiempo de mantenimiento (minutos)");

            txtMaquina.setPromptText(VerMaquinaController.toView.getMaquina().getId());
            txtMaquina.setEditable(false);

            cbTipo.getItems().add("PREVENTIVO");
            cbTipo.getSelectionModel().selectFirst();

            btnGuardar.setText("Guardar");
            btnGuardar.setStyle("-fx-font-weight: bold; -fx-background-color: #ffa500");
            btnGuardar.setOnAction(e -> guardar());
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void guardar(){
        if(txtTiempo.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "El tiempo de mantenimiento no puede estar vacío", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try{
            LocalDate fecha = LocalDate.now();
            ResultadoType resultado = cbResultado.getSelectionModel().getSelectedItem();
            String comentarios = txtComentarios.getText();
            Usuario empleado = usuarioServices.load(cbEmpleado.getSelectionModel().getSelectedItem());
            Supervisor supervisor = (Supervisor) usuarioServices.load(cbSupervisor.getSelectionModel().getSelectedItem());
            int tiempo = Integer.parseInt(txtTiempo.getText());
            Maquina maquina = maquinaServices.getMaquina(VerMaquinaController.toView.getMaquina().getId());

            Mantenimiento mantenimiento = new Preventivo(fecha, resultado, comentarios, empleado, supervisor, tiempo, maquina, VerMaquinaController.toView);
            mantenimientoServices.registrarMantenimiento(mantenimiento);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Mantenimiento marcado correctamente", ButtonType.OK);
            alert.showAndWait();
            btnGuardar.getScene().getWindow().hide();
            App.setRoot("verPrograma");
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
