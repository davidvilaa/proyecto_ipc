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

public class MainMenu_FXMLController implements Initializable {

    @FXML
    private Label bienvenido;
    @FXML
    private ImageView fotoperfil;
    @FXML
    private Button goToAddGasto;
    @FXML
    private Button goToCategory;
    @FXML
    private Button goToVisualizar;
    @FXML
    private Button goToModificar;
    @FXML
    private Button goToBorrar;
    @FXML
    public TableView<Charge> chargeTable;
    @FXML
    private TableColumn<Charge, Category> categoryColumn;
    @FXML
    private TableColumn<Charge, String> conceptColumn;
    @FXML
    private TableColumn<Charge, Double> priceColumn;
    @FXML
    private TableColumn<Charge, LocalDate> dateColumn;
    @FXML
    private MenuButton usuarioMenu;
    @FXML
    private MenuItem configuracion;
    @FXML
    private Button NOBORRAR;
    
    private ObservableList<Charge> cargos = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bienvenido.setText(Login_FXMLController.getNombrecuentaGastos());
        fotoperfil.setImage(Login_FXMLController.getImagencuentaGastos());
        
        goToVisualizar.disableProperty().bind(Bindings.equal(-1,chargeTable.getSelectionModel().selectedIndexProperty()));
        goToModificar.disableProperty().bind(Bindings.equal(-1,chargeTable.getSelectionModel().selectedIndexProperty()));
        goToBorrar.disableProperty().bind(Bindings.equal(-1,chargeTable.getSelectionModel().selectedIndexProperty()));
            
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setCellFactory((c) -> new Celda());
        conceptColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Charge, LocalDate>("date"));
        
        try{
            updateCharges();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void updateCharges() throws IOException{
        try{
            List<Charge> charges = Acount.getInstance().getUserCharges();
            for(Charge charge : charges){
                cargos.add(charge);
            }
            chargeTable.setItems(cargos);
        }
        catch(AcountDAOException e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void usuariomenu(MouseEvent event) {
    }

    @FXML
    private void usuariopasar(MouseEvent event) {
        bienvenido.setStyle("-fx-underline: true;");
        fotoperfil.setStyle("-fx-underline: true;");
    }

    @FXML
    private void usuarionopasar(MouseEvent event) {
        bienvenido.setStyle("-fx-underline: false;"); 
        fotoperfil.setStyle("-fx-underline: false;");
    }
    
    @FXML
    private void goToAddGastoHandle(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddGasto/AddGasto_FXML.fxml"));
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setTitle("Añadir Gasto");
        stage.setScene(new Scene(root));
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(goToAddGasto.getScene().getWindow());
        
        stage.show();
    }
    
    @FXML
    private void goToCategoryHandle(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Category/Category_FXML.fxml"));
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setTitle("Categorías");
        stage.setScene(new Scene(root));
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(goToCategory.getScene().getWindow());
        
        stage.show();
    }
    
    @FXML
    private void goToVisualizarHandle() throws IOException{
        Charge selected = chargeTable.getSelectionModel().getSelectedItem();
        String costString = Double.toString(selected.getCost());
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewGasto/ViewGasto_FXML.fxml"));
        Parent root = loader.load();
        
        ViewGasto_FXMLController viewController = loader.getController();
        viewController.initializateData(selected.getCategory().getName(), costString,
                selected.getDate().toString(), selected.getName(), selected.getDescription(), 
                selected.getImageScan());
        
        Stage stage = new Stage();
        stage.setTitle("Visualizar Gasto");
        stage.setScene(new Scene(root));
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(goToCategory.getScene().getWindow());
        
        stage.show();
    }
    
    @FXML
    private void goToModificarHandle() throws IOException{
        Charge selected = chargeTable.getSelectionModel().getSelectedItem();
        String costString = Double.toString(selected.getCost());
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditGasto/EditGasto_FXML.fxml"));
        Parent root = loader.load();
        
        EditGasto_FXMLController viewController = loader.getController();
        viewController.initializateData(selected.getCategory().getName(), costString,
                selected.getDate(), selected.getName(), selected.getDescription(), 
                selected.getImageScan());
        
        Stage stage = new Stage();
        stage.setTitle("Visualizar Gasto");
        stage.setScene(new Scene(root));
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(goToCategory.getScene().getWindow());
        
        stage.show();
    }
    
    @FXML
    private void goToBorrarHandle() throws IOException{
        Charge selectedCharge = chargeTable.getSelectionModel().getSelectedItem();
        
        try{
            cargos.remove(selectedCharge);
            chargeTable.setItems(cargos);
            
            Acount.getInstance().removeCharge(selectedCharge);
        }
        catch(AcountDAOException e3){
            e3.printStackTrace();
        }
    }

    @FXML
    private void pulsarUsuario(MouseEvent event) {
        usuarioMenu.show();
    }

    @FXML
    private void Configurar(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Configurar/Configurar_FXML.fxml"));
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setTitle("Configurar datos");
        stage.setScene(new Scene(root));
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(NOBORRAR.getScene().getWindow());
        stage.getIcons().add(new Image("./assets/ww_black.png"));
        stage.showAndWait();
        
        bienvenido.setText(Login_FXMLController.getNombrecuentaGastos());
        fotoperfil.setImage(Login_FXMLController.getImagencuentaGastos());
    }

    @FXML
    private void LogOut(ActionEvent event) throws IOException {
        Alert logOut = new Alert(Alert.AlertType.WARNING);
        logOut.setTitle("Cerrar Sesión");
        logOut.setHeaderText("¿Quiere cerrar sesión?");
        logOut.setContentText("Si lo hace, tendrá que volver a iniciar sesión.");
        logOut.showAndWait();
        Login_FXMLController.cuentaGastos.logOutUser();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login_FXML.fxml"));
        Parent root = loader.load();
        Login_Main.scene = new Scene(root);
        Login_Main.mainStage.setScene(Login_Main.scene);
        Login_Main.mainStage.setTitle("Login Usuario");
        Login_Main.mainStage.getIcons().add(new Image("./assets/ww_black.png"));
        Login_Main.mainStage.setResizable(false);
        Login_Main.mainStage.showAndWait();
    }
}