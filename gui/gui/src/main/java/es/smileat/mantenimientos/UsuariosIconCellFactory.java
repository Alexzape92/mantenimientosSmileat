package es.smileat.mantenimientos;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class UsuariosIconCellFactory implements Callback<TableColumn<Usuario, Void>, TableCell<Usuario, Void>> {
    
    @Override
    public TableCell<Usuario, Void> call(TableColumn<Usuario, Void> param) {
        return new TableCell<Usuario, Void>() {
            private final ImageView upgradeIcon = new ImageView(new Image(getClass().getResourceAsStream("img/upgrade-icon.png")));
            private final ImageView removeIcon = new ImageView(new Image(getClass().getResourceAsStream("img/remove-icon.png")));
            private UsuarioServices usuarioServices;
            
            {
                upgradeIcon.setFitHeight(16);
                upgradeIcon.setFitWidth(16);
                removeIcon.setFitHeight(16);
                removeIcon.setFitWidth(16);
                try{
                    usuarioServices = new UsuarioServices();
                }catch(Exception e){
                    Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || !(getTableView().getItems().get(getIndex()) instanceof Empleado)) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(10, upgradeIcon, removeIcon));
                    Usuario usuario = getTableView().getItems().get(getIndex());

                    //Edit icon
                    upgradeIcon.setOnMouseEntered(event -> upgradeIcon.getScene().setCursor(javafx.scene.Cursor.HAND));
                    upgradeIcon.setOnMouseExited(event -> {
                        if(upgradeIcon.getScene() != null)
                            upgradeIcon.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
                    });
                    upgradeIcon.setOnMouseClicked(event -> {
                       try{
                            //If here, the user is an employee
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Promocionar usuario");
                            alert.setHeaderText("¿Está seguro de que desea promocionar el usuario?");
                            alert.setContentText("Esta acción no se puede deshacer");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK){
                                usuarioServices.upgrade((Empleado) usuario);
                                getTableView().getItems().remove(getIndex());
                            }
                       } catch(Exception e){
                            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                            alert.showAndWait();
                       }
                    });

                    //Remove icon
                    removeIcon.setOnMouseEntered(event -> removeIcon.getScene().setCursor(javafx.scene.Cursor.HAND));
                    removeIcon.setOnMouseExited(event -> {
                        if(removeIcon.getScene() != null)
                            removeIcon.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
                    });
                    removeIcon.setOnMouseClicked(event -> {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Eliminar usuario");
                        alert.setHeaderText("¿Está seguro de que desea eliminar el usuario?");
                        alert.setContentText("Esta acción no se puede deshacer");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            try{
                                usuarioServices.eliminar(usuario);
                            }catch(Exception e){
                                Alert alert2 = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                                alert2.showAndWait();
                            }
                            getTableView().getItems().remove(getIndex());
                        }
                    });
                }
            }
        };
    }
}
