package MainMenu;


import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Acount;
import model.AcountDAOException;
import java.io.IOException;
public class MainMenu_FXMLController implements Initializable {

    public Label BienvenidoUsuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     try{
     Acount user = Acount.getInstance();
     BienvenidoUsuario.setText(user.getLoggedUser().getName());
    }
     catch(AcountDAOException e){
          e.printStackTrace();
    }catch(IOException e){
        e.printStackTrace();
    }
    }
}
