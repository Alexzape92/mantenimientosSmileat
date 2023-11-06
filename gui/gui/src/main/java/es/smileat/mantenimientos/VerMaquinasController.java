package es.smileat.mantenimientos;

import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class VerMaquinasController {
    private MaquinaServices maquinaServices;
    @FXML
    private Label lblTitulo;
    @FXML
    private ImageView imgAtras;
    @FXML
    private ChoiceBox<String> cbMaquinas;
    @FXML
    private TableView<Maquina> tbMaquinas;
    @FXML
    private TableColumn<Maquina, String> tcId;
    @FXML
    private TableColumn<Maquina, String> tcModelo;
    @FXML
    private TableColumn<Maquina, Integer> tcIncidencias;
    @FXML
    private TableColumn<Maquina, String> tcSala;
    @FXML
    private TableColumn<Maquina, Void> tcIconos;

    @FXML
    private void initialize(){
        try{
            maquinaServices = new MaquinaServices();
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        lblTitulo.setText("VER MÁQUINAS");
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

        initOptions();
        initTable();

        cbMaquinas.setOnAction(e -> {
            tbMaquinas.getItems().clear();
            initTable();
        });
    }

    private void initOptions(){
        List<String> salas = null;
        try{
            salas = maquinaServices.getMaquinas().stream().map(m -> m.getSala()).distinct().collect(Collectors.toList());
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        salas.add("Todas");
        cbMaquinas.getItems().addAll(salas);
        cbMaquinas.setValue("Todas");
    }

    private void initTable(){

        List<Maquina> maquinas = null;

        try{
            maquinas = maquinaServices.getMaquinas().stream().filter(m -> m.getSala().equals(cbMaquinas.getValue()) || cbMaquinas.getValue().equals("Todas")).collect(Collectors.toList());
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        
        tcId.setCellValueFactory(new PropertyValueFactory<Maquina, String>("Id"));
        tcModelo.setCellValueFactory(new PropertyValueFactory<Maquina, String>("Modelo"));
        tcIncidencias.setCellValueFactory(new PropertyValueFactory<Maquina, Integer>("Incidencias"));
        tcSala.setCellValueFactory(new PropertyValueFactory<Maquina, String>("Sala"));
        tcIconos.setCellFactory(new MaquinasIconCellFactory());
        
        tbMaquinas.getItems().addAll(maquinas);
    }
}
