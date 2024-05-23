package MainMenu;

import Login.Login_FXMLController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.*;
import java.io.*;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Login.Login_Main;
import ViewGasto.ViewGasto_FXMLController;
import EditGasto.EditGasto_FXMLController;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.StackPane;

public class Celda extends TableCell<Charge, Category> {
    
    @Override
    protected void updateItem(Category t, boolean bln){
        super.updateItem(t, bln);
        
        if(bln || t == null) setText(null);
        else setText(t.getName());
    }
}
