package es.smileat.mantenimientos;

import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MaquinasIconCellFactory implements Callback<TableColumn<Maquina, Void>, TableCell<Maquina, Void>> {
    
    @Override
    public TableCell<Maquina, Void> call(TableColumn<Maquina, Void> param) {
        return new TableCell<Maquina, Void>() {
            private final ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("img/edit-icon.png")));
            private final ImageView removeIcon = new ImageView(new Image(getClass().getResourceAsStream("img/remove-icon.png")));
            private final ImageView infoIcon = new ImageView(new Image(getClass().getResourceAsStream("img/info-icon.png")));
            private MaquinaServices maquinaServices;
            
            {
                editIcon.setFitHeight(16);
                editIcon.setFitWidth(16);
                removeIcon.setFitHeight(16);
                removeIcon.setFitWidth(16);
                infoIcon.setFitHeight(16);
                infoIcon.setFitWidth(16);
                try{
                    maquinaServices = new MaquinaServices();
                }catch(Exception e){
                    Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(10, editIcon, removeIcon, infoIcon));
                    Maquina maquina = getTableView().getItems().get(getIndex());

                    //Edit icon
                    editIcon.setOnMouseEntered(event -> editIcon.getScene().setCursor(javafx.scene.Cursor.HAND));
                    editIcon.setOnMouseExited(event -> {
                        if(editIcon.getScene() != null)
                            editIcon.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
                    });
                    editIcon.setOnMouseClicked(event -> {
                        EditarMaquinaController.toUpdate = maquina;
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(editIcon.getScene().getWindow());
                        try{
                            Scene dialogScene = new Scene(App.loadFXML("editarMaquina"), 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }catch(Exception e){
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
                        alert.setTitle("Eliminar máquina");
                        alert.setHeaderText("¿Está seguro de que desea eliminar la máquina?");
                        alert.setContentText("Esta acción no se puede deshacer");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            try{
                                maquinaServices.eliminarMaquina(maquina.getId());
                            }catch(Exception e){
                                Alert alert2 = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                                alert2.showAndWait();
                            }
                            getTableView().getItems().remove(getIndex());
                        }
                    });

                    //Info icon
                    infoIcon.setOnMouseEntered(event -> infoIcon.getScene().setCursor(javafx.scene.Cursor.HAND));
                    infoIcon.setOnMouseExited(event -> {
                        if(infoIcon.getScene() != null)
                            infoIcon.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
                    });
                    infoIcon.setOnMouseClicked(event -> {
                        VerPreventivosController.toShow = maquina;
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(infoIcon.getScene().getWindow());
                        try{
                            Scene dialogScene = new Scene(App.loadFXML("verPreventivos"), 1024, 768);
                            dialog.setScene(dialogScene);
                            dialog.setMaximized(true);
                            dialog.show();
                        }catch(Exception e){
                            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                            alert.showAndWait();
                            e.printStackTrace();
                        }
                    });
                }
            }
        };
    }
}
