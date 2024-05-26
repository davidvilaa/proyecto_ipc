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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    private Button register_button;

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
        return (password.getText().length() >= 6 && password.getText().matches("^[a-zA-Z0-9]+$"));
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
    private void keyEnterRegister(KeyEvent event) throws IOException, AcountDAOException {
        if(event.getCode() == KeyCode.ENTER){
            register();
        }
    }
    
    @FXML
    public void registerHandle(ActionEvent event) throws AcountDAOException, IOException{
        register();
    }
    
    public void register()throws AcountDAOException, IOException{
        name_error.setVisible(false);
        surname_error.setVisible(false);
        nickname_error.setVisible(false);
        password_error.setVisible(false);
        mail_error.setVisible(false);
        
        try{
            if(nameIsCorrect() && surnameIsCorrect() && nicknameIsCorrect() && passwordIsCorrect() && mailIsCorrect()){
                Acount cuentaGastos = Acount.getInstance();
                LocalDate now = LocalDate.now();
                cuentaGastos.registerUser(name.getText(), surname.getText(), mail.getText(), nickname.getText(), password.getText(), image.getImage(), now);
                
                goToLogin();
            }
            else{
                if(!nameIsCorrect()){
                    name_error.setVisible(true);
                }
                if(!surnameIsCorrect()){
                    surname_error.setVisible(true);
                }
                if(!nicknameIsCorrect()){
                    nickname_error.setVisible(true);
                }
                if(!passwordIsCorrect()){
                    password_error.setVisible(true);
                }
                if(!mailIsCorrect()){
                    mail_error.setVisible(true);
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
        String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage window = (Stage) nickname.getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Login");
        window.show();
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        image.setVisible(false);
        image.setImage(new Image("./assets/default_user.png"));
        
        name_error.setVisible(false);
        surname_error.setVisible(false);
        nickname_error.setVisible(false);
        password_error.setVisible(false);
        mail_error.setVisible(false);
    }
}
