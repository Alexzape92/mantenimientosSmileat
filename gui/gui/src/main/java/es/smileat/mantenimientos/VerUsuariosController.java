package es.smileat.mantenimientos;

import java.util.ArrayList;
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

public class VerUsuariosController {
    private  UsuarioServices usuarioServices;
    @FXML
    private Label lblTitulo;
    @FXML
    private ImageView imgAtras;
    @FXML
    private ChoiceBox<String> cbUsuarios;
    @FXML
    private TableView<Usuario> tbUsuarios;
    @FXML
    private TableColumn<Usuario, String> tcNombre;
    @FXML
    private TableColumn<Usuario, Void> tcIconos;

    @FXML
    private void initialize(){
        try{
            usuarioServices = new UsuarioServices();
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        lblTitulo.setText("VER USUARIOS");
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

        cbUsuarios.setOnAction(e -> {
            tbUsuarios.getItems().clear();
            initTable();
        });
    }

    private void initOptions(){
        List<String> roles = new ArrayList<String>();
        roles.add("Empleado");
        roles.add("Supervisor");
        cbUsuarios.getItems().addAll(roles);
        cbUsuarios.setValue("Empleado");
    }

    private void initTable(){
        List<Usuario> usuarios = null;

        try{
            if(cbUsuarios.getValue().equals("Empleado"))
                usuarios = usuarioServices.loadAllEmpleados().stream().collect(Collectors.toList());
            else
                usuarios = usuarioServices.loadAllSupervisores().stream().collect(Collectors.toList());
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        
        tcNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nombre"));

        tcIconos.setCellFactory(new UsuariosIconCellFactory());
        
        tbUsuarios.getItems().addAll(usuarios);
    }
}

