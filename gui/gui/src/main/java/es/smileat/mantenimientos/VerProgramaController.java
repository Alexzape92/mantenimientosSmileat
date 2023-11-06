package es.smileat.mantenimientos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VerProgramaController {
    private CalendarioServices calendarioServices;
    private MantenimientoServices mantenimientoServices;
    @FXML
    private Label lblTitulo;
    @FXML
    private ImageView imgAtras;
    @FXML
    private FlowPane calendar;

    @FXML
    private void initialize(){
        try{
            calendarioServices = new CalendarioServices();
            calendarioServices.rellenarCalendario();
            mantenimientoServices = new MantenimientoServices();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        lblTitulo.setText("VER PROGRAMA");
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

        calendar.setHgap(5);
        calendar.setVgap(5);
        rellenar();
    }

    private void rellenar(){
        List<PreventivoType>[] programa = calendarioServices.getCalendario();

        LocalDate fecha = LocalDate.now();
        for(int i = 0; i < 30; i++){
            VBox vb = new VBox();
            vb.setAlignment(javafx.geometry.Pos.TOP_CENTER);
            vb.setPrefHeight(200);
            vb.setPrefWidth(200);
            vb.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000 ;-fx-border-width: 2px");

            if(fecha.getDayOfWeek().equals(DayOfWeek.SATURDAY))
                fecha = fecha.plusDays(2);
            else if(fecha.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                fecha = fecha.plusDays(1);

            String dia = fecha.getDayOfMonth() + "/" + fecha.getMonthValue() + "/" + fecha.getYear();
            Label lblDia = new Label(dia);
            lblDia.setFont(javafx.scene.text.Font.font(20));
            lblDia.setStyle("-fx-font-weight: bold; -fx-text-fill: #1E90FF");
            vb.getChildren().add(lblDia);

            VBox fp = new VBox();
            fp.setAlignment(Pos.CENTER);
            fp.setSpacing(3);
            fp.setMaxWidth(180);
            fp.setPrefHeight(150);
            fp.setStyle("-fx-border-color: #000000 ;-fx-border-width: 1.5px");

            for(PreventivoType preventivoType : programa[i]){
                Maquina maquina = preventivoType.getMaquina();

                Label lblMaquina = new Label(maquina.getId());
                lblMaquina.setFont(javafx.scene.text.Font.font(17));
                lblMaquina.setStyle("-fx-font-weight: bold; -fx-text-fill: #FF8C00");

                lblMaquina.setOnMouseEntered(e -> lblMaquina.getScene().setCursor(javafx.scene.Cursor.HAND));
                lblMaquina.setOnMouseExited(e -> {
                    if(lblMaquina.getScene() != null) 
                        lblMaquina.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
                });
                lblMaquina.setOnMouseClicked(e -> {
                    VerMaquinaController.toView = preventivoType;
                    lblMaquina.getScene().setCursor(javafx.scene.Cursor.DEFAULT); 
                    final Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(lblMaquina.getScene().getWindow());
                    try{
                        Scene scene = new Scene(App.loadFXML("verMaquina"), 300, 200);
                        stage.setScene(scene);
                        stage.show();
                    } catch(Exception ex){
                        Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                        alert.showAndWait();
                    }
                });

                fp.getChildren().add(lblMaquina);
            }

            //Mantenimientos preventivos ya realizados hoy
            if(i == 0){
                try{
                    List<Mantenimiento> mantenimientos = mantenimientoServices.getPreventivosFecha(LocalDate.now(), LocalDate.now()).stream().collect(Collectors.toList());
                    for(Mantenimiento mantenimiento : mantenimientos){
                        Label lblMaquina = new Label(mantenimiento.getMaquina().getId());
                        lblMaquina.setFont(javafx.scene.text.Font.font(17));
                        lblMaquina.setStyle("-fx-font-weight: bold; -fx-text-fill: #00FF00");

                        fp.getChildren().add(lblMaquina);
                    }
                }catch(Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
        
            vb.getChildren().add(fp);
            calendar.getChildren().add(vb);
            fecha = fecha.plusDays(1);
        }
    }
}
