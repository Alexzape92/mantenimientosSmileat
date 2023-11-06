package es.smileat.mantenimientos;

import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VerPreventivosController {
    private PreventivoTypeServices preventivoTypeServices;
    public static Maquina toShow;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<PreventivoType> tbPreventivos;
    @FXML
    private TableColumn<PreventivoType, String> tcDescripcion;
    @FXML
    private TableColumn<PreventivoType, Integer> tcFrecuencia;
    @FXML
    private TableColumn<PreventivoType, Integer> tcTiempo;
    @FXML
    private TableColumn<PreventivoType, Void> tcIconos;
    @FXML
    private Button btnNuevo;

    @FXML
    private void initialize(){
        try{
            preventivoTypeServices = new PreventivoTypeServices();
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

        lblTitulo.setText("VER PREVENTIVOS: " + toShow.getId());
        lblTitulo.setFont(javafx.scene.text.Font.font(30));
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: #1E90FF");

        btnNuevo.setText("Añadir");
        btnNuevo.setOnAction(e -> {
            AnnadirPreventivoController.maquina = toShow;
            btnNuevo.getScene().setCursor(javafx.scene.Cursor.DEFAULT); 

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnNuevo.getScene().getWindow());
            try{
                Scene scene = new Scene(App.loadFXML("annadirPreventivo"), 400, 600);
                stage.setScene(scene);
                stage.showAndWait();
                initTable();
            } catch(Exception ex){
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.showAndWait();
            }
        });

        initTable();
    }

    public void initTable(){
        List<PreventivoType> preventivos = null;

        try{
            preventivos = preventivoTypeServices.getPreventivosType().stream().filter(p -> p.getMaquina().getId().equals(toShow.getId())).collect(Collectors.toList());
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

        tcDescripcion.setCellValueFactory(new PropertyValueFactory<PreventivoType, String>("Descripcion"));
        tcFrecuencia.setCellValueFactory(new PropertyValueFactory<PreventivoType, Integer>("Frecuencia"));
        tcTiempo.setCellValueFactory(new PropertyValueFactory<PreventivoType, Integer>("Tiempo"));
        tcIconos.setCellFactory(new PreventivoIconCellFactory());

        tbPreventivos.getItems().addAll(preventivos);
    }
}
