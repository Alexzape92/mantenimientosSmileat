package es.smileat.mantenimientos;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class HomeController {
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    private UsuarioServices usuarioServices;

    @FXML
    private void initialize(){
        try{
            usuarioServices = new UsuarioServices();
        }catch(Exception e){
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }

        txtUsuario.setPromptText("Usuario");
        txtPassword.setPromptText("Contraseña");
        loginButton.setText("Login");
        loginButton.setOnAction(e -> loginButton());
        registerButton.setText("Registro");
        registerButton.setOnAction(e -> registerButton());
    }

    private void loginButton(){
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        try{
            Usuario user = usuarioServices.login(usuario, password);
            App.usuario = user;

            if(user instanceof Supervisor)
                App.setRoot("supervisor");

        }catch(Exception e){
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

    private void registerButton(){
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        Usuario user = new Empleado(usuario, password);

        try{
            usuarioServices.registro(user);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Registro");
            alert.setContentText("Registro correcto");
            alert.showAndWait();
            
        }catch(Exception e){
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }
}
