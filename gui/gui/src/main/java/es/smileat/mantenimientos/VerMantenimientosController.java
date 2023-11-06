package es.smileat.mantenimientos;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class VerMantenimientosController {
    private MantenimientoServices mantenimientoServices;
    @FXML
    private Label lblTitulo;
    @FXML
    private ImageView imgAtras;
    @FXML
    private ChoiceBox<String> cbMantenimientos;
    @FXML
    private TextField txtMaquina;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Mantenimiento> tbMantenimientos;
    @FXML
    private TableColumn<Mantenimiento, String> tcMaquina;
    @FXML
    private TableColumn<Mantenimiento, LocalDate> tcFecha;
    @FXML
    private TableColumn<Mantenimiento, ResultadoType> tcResultado;
    @FXML
    private TableColumn<Mantenimiento, String> tcComentarios;
    @FXML
    private TableColumn<Mantenimiento, String> tcTipo;
    @FXML
    private TableColumn<Mantenimiento, String> tcEmpleado;
    @FXML
    private TableColumn<Mantenimiento, String> tcSupervisor;
    @FXML
    private TableColumn<Mantenimiento, Integer> tcTiempo;
    @FXML
    private TableColumn<Mantenimiento, String> tcDescripcion;


    @FXML
    private void initialize(){
        try{
            mantenimientoServices = new MantenimientoServices();
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        lblTitulo.setText("VER MANTENIMIENTOS");
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

        cbMantenimientos.getItems().addAll("Todos", "Correctivos", "Preventivos");
        cbMantenimientos.setValue("Todos");

        initTable();

        cbMantenimientos.setOnAction(e -> {
            tbMantenimientos.getItems().clear();
            initTable();
        });

        btnBuscar.setOnAction(e -> {
            tbMantenimientos.getItems().clear();
            initTable();
        });
    }

    private void initTable(){

        List<Mantenimiento> mantenimientos = null;

        try{
            if(cbMantenimientos.getValue().equals("Todos")){
                mantenimientos = mantenimientoServices.getMantenimientosCorrectivos().stream().collect(Collectors.toList());
                mantenimientos.addAll(mantenimientoServices.getMantenimientosPreventivos().stream().collect(Collectors.toList()));
            }else if(cbMantenimientos.getValue().equals("Correctivos")){
                mantenimientos = mantenimientoServices.getMantenimientosCorrectivos().stream().collect(Collectors.toList());
            }else if(cbMantenimientos.getValue().equals("Preventivos")){
                mantenimientos = mantenimientoServices.getMantenimientosPreventivos().stream().collect(Collectors.toList());
            }

            if(!txtMaquina.getText().isEmpty()){
                mantenimientos = mantenimientos.stream().filter(m -> m.getMaquina().getId().equals(txtMaquina.getText())).collect(Collectors.toList());
            }
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        
        tcMaquina.setCellFactory(new MaquinaCellFactory());
        tcFecha.setCellValueFactory(new PropertyValueFactory<Mantenimiento, LocalDate>("Fecha"));
        tcResultado.setCellValueFactory(new PropertyValueFactory<Mantenimiento, ResultadoType>("Resultado"));
        tcComentarios.setCellValueFactory(new PropertyValueFactory<Mantenimiento, String>("Comentarios"));
        tcTipo.setCellFactory(new TipoMantCellFactory());
        tcEmpleado.setCellFactory(new EmpleadoCellFactory());
        tcSupervisor.setCellFactory(new SupervisorCellFactory());
        tcTiempo.setCellValueFactory(new PropertyValueFactory<Mantenimiento, Integer>("Tiempo"));
        tcDescripcion.setCellFactory(new DescripcionCellFactory());
        
        tbMantenimientos.getItems().addAll(mantenimientos);
    }
}
