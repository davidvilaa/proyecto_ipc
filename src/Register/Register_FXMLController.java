package Register;

import java.io.File;
import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;    
import model.*;
import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

public class Register_FXMLController implements Initializable {
    
    @FXML
    public TextField name;
    @FXML
    public TextField surname;
    @FXML
    public TextField nickname;
    @FXML
    public PasswordField password;
    @FXML
    public TextField mail;
    @FXML
    private Button selectArchive;
    @FXML
    public ImageView image;
    @FXML
    private Button register;
    
    @FXML
    private Label name_error;
    @FXML
    private Label surname_error;
    @FXML
    private Label nickname_error;
    @FXML
    private Label password_error;
    @FXML
    private Label mail_error;
    @FXML
    private Label register_error;
    
    @FXML
    private Button atras;
    
    String[] accepted_mails = {"@gmail.com", "@yahoo.com", "@upv.es", "@hotmail.com"};

    @FXML
    public void selectArchiveHandle(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecciona una Imágen");
        
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png","*.jpg","*.jpeg"));
        
        File file = fc.showOpenDialog(new Stage());
        
        if(file != null){
            Image file_image = new Image(file.toURI().toString());
            image.setImage(file_image);
            selectArchive.setVisible(false);
            image.setVisible(true);
            
        }
    }
    
    private boolean nameIsCorrect(){
        return (name.getText().matches("^[a-zA-Z]+$"));
    }
    
    private boolean surnameIsCorrect(){
        return (surname.getText().matches("^[a-zA-Z]+$"));
    }
    
    private boolean nicknameIsCorrect(){
        return (nickname.getText().matches("^[a-z]+$"));
    }
    
    private boolean passwordIsCorrect(){
        return (password.getText().length() >= 6 && nickname.getText().matches("^[a-zA-Z0-9]+$"));
    }
    
    private boolean mailIsCorrect(){
        if(mail.getText().isEmpty() || mail.getText() == null) return false;
        
        int i = 0;
        for(String dominio : accepted_mails){
            if(mail.getText().endsWith(dominio)){
                i++;
                if(mail.getText().indexOf('@') == 0){
                    return false;
                }
            }
        }
        
        return i == 1;
    }
    
    @FXML
    public void registerHandle(ActionEvent event) throws AcountDAOException, IOException{
        register_error.setText("");
        name_error.setText("");
        surname_error.setText("");
        nickname_error.setText("");
        password_error.setText("");
        mail_error.setText("");
        
        try{
            if(nameIsCorrect() && surnameIsCorrect() && nicknameIsCorrect() && passwordIsCorrect() && mailIsCorrect()){
                Acount cuentaGastos = Acount.getInstance();
                LocalDate now = LocalDate.now();
                cuentaGastos.registerUser(name.getText(), surname.getText(), mail.getText(), nickname.getText(), password.getText(), image.getImage(), now);
                
                goToLogin();
            }
            else{
                if(!nameIsCorrect()){
                    name_error.setText("El Nombre solo puede contener Letras");
                }
                if(!surnameIsCorrect()){
                    surname_error.setText("El Apellido solo puede contener Letras");
                }
                if(!nicknameIsCorrect()){
                    nickname_error.setText("El usuario solo puede conter Letras minúsculas");
                }
                if(!passwordIsCorrect()){
                    password_error.setText("La contraseña solo puede contener un mínimo de 6 Letras o Números");
                }
                if(!mailIsCorrect()){
                    mail_error.setText("El mail debe contener un dominio permitido o texto antes de @");
                }
            }
        }
        catch(AcountDAOException e){
            register_error.setText("ERROR: Este usuario ya existe");
        }
    }
    
    @FXML
    public void atrasHandle(ActionEvent event) throws IOException{
        goToLogin();
    }
    
    public void goToLogin() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/Login/Login_FXML.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) nickname.getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Login");
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        image.setVisible(false);
        image.setImage(new Image("./assets/default_user.png"));
    }
}
