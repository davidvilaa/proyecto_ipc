/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Configurar;

import Login.Login_FXMLController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;



/**
 * FXML Controller class
 *
 * @author Borja Rodríguez Vict
 */
public class Configurar_FXMLController implements Initializable {

    public Boolean posibleGuardar = true;
    @FXML
    private TextField configurarNickname;
    @FXML
    private TextField configurarNombre;
    @FXML
    private TextField configurarApellido;
    @FXML
    private TextField configurarCorreo;
    
    @FXML
    private ImageView fotoQueSeConfigura;
    @FXML
    private Label configurarFoto;
    @FXML
    private Button guardarNuevaConfiguracion;
    
    private Acount cuentaConfigurar= Login_FXMLController.cuentaGastos;
    @FXML
    private PasswordField antiguaContraseña;
    @FXML
    private PasswordField nuevaContraseña;
    @FXML
    private Button guardarNuevaConfiguracion1;
    String[] accepted_mails = {"@gmail.com", "@yahoo.com", "@upv.es", "@hotmail.com"};
    @FXML
    public Label name_error;
    @FXML
    public Label surname_error;
    @FXML
    public Label password_error;
    @FXML
    public Label mail_error;
    public Label register_error;
    
    public boolean contraseñaCorrecta = false;
    private boolean nameIsCorrect(){
        return (configurarNombre.getText().matches("^[a-zA-Z]+$"));
    }
    
    private boolean surnameIsCorrect(){
        return (configurarApellido.getText().matches("^[a-zA-Z]+$"));
    }
    
    private boolean passwordIsCorrect(){
        return (nuevaContraseña.getText().length() >= 6);
    }
    
    private boolean mailIsCorrect(){
        if(configurarCorreo.getText().isEmpty() || configurarCorreo.getText() == null) return false;
        
        int i = 0;
        for(String dominio : accepted_mails){
            if(configurarCorreo.getText().endsWith(dominio)){
                i++;
                if(configurarCorreo.getText().indexOf('@') == 0){
                    return false;
                }
            }
        }
        
        return i == 1;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        configurarNickname.setText(cuentaConfigurar.getLoggedUser().getNickName());
        configurarNombre.setText(cuentaConfigurar.getLoggedUser().getName());
        configurarApellido.setText(cuentaConfigurar.getLoggedUser().getSurname());
        configurarCorreo.setText(cuentaConfigurar.getLoggedUser().getEmail());
        fotoQueSeConfigura.setImage(cuentaConfigurar.getLoggedUser().getImage()); 
    }

    @FXML
    private void cambiarNuevaFoto(MouseEvent event) {
     FileChooser fc = new FileChooser();
        fc.setTitle("Selecciona una Imágen");
        
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png","*.jpg","*.jpeg"));
        
        File file = fc.showOpenDialog(new Stage());
        
        if(file != null){
            Image file_image = new Image(file.toURI().toString());
            fotoQueSeConfigura.setImage(file_image);
        }
    }
    
    @FXML
    private void guardar(MouseEvent event) throws IOException {
        if(0 == antiguaContraseña.getText().compareTo(cuentaConfigurar.getLoggedUser().getPassword())){
            System.out.println("contraseñaCorrecta");
            cuentaConfigurar.getLoggedUser().setPassword(nuevaContraseña.getText());
        }
                if(!nameIsCorrect()){
                    name_error.setText("El Nombre solo puede contener Letras");
                    posibleGuardar = false;
                    name_error.setVisible(true);
                }
                if(!surnameIsCorrect()){
                    surname_error.setText("El Apellido solo puede contener Letras");
                    posibleGuardar = false;
                    surname_error.setVisible(true);
                }
                
                
                if(!passwordIsCorrect()){
                    password_error.setText("La contraseña solo puede contener un mínimo de 6 Letras o Números");
                    posibleGuardar = false;
                    password_error.setVisible(true);
                }
                if(!mailIsCorrect()){
                    mail_error.setText("El mail debe contener un dominio permitido o texto antes de @");
                    posibleGuardar = false;
                    mail_error.setVisible(true);
                }
        
        if(posibleGuardar = true){
        cuentaConfigurar.getLoggedUser().setName((configurarNombre.getText()));
        cuentaConfigurar.getLoggedUser().setSurname((configurarApellido.getText()));
        cuentaConfigurar.getLoggedUser().setEmail((configurarCorreo.getText()));
        cuentaConfigurar.getLoggedUser().setImage(fotoQueSeConfigura.getImage());
    }
}
}


