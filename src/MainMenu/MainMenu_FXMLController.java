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
import static Estadisticas.Estadisticas_Main.scene;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
    private Button stats;
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
    private TableColumn<Charge, Integer> unitColumn;
    @FXML
    private MenuButton usuarioMenu;
    @FXML
    private MenuItem configuracion;
    @FXML
    private Button NOBORRAR;
    
    private ObservableList<Charge> cargos = FXCollections.observableArrayList();
    @FXML
    private Button PrintButton;
    @FXML
    private MenuButton tableCategory;
    @FXML
    private Label labelTableCategory;
    
    private final StringProperty selectedCategory = new SimpleStringProperty();
    
    private FilteredList<Charge> filteredCharges;
    
    Category category = null;
    
    ObservableList<MenuItem> categories = FXCollections.observableArrayList();
    @FXML
    private HBox hBox;
    private Button statsButton;

    
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
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("units"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        filteredCharges = new FilteredList<>(cargos);
        
        chargeTable.setItems(filteredCharges);
        
        try{
            updateCharges();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        try{
          addCategories();
          tableCategory.getItems().addAll(categories);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        String css = this.getClass().getResource("/estilos/botonesLogin.css")
        .toExternalForm();
        hBox.getStylesheets().add(css);
    }

    public void updateCharges() throws IOException{
        try{
            List<Charge> charges = Acount.getInstance().getUserCharges();
            cargos.setAll(charges);
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
        String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setOnHidden(e -> {
            try {
                updateCharges();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
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
        String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        
        stage.setOnHidden(e -> {
            try {
                updateCharges();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(goToCategory.getScene().getWindow());
        
        stage.show();
    }
    
    @FXML
    private void goToVisualizarHandle() throws IOException{
        Charge selected = chargeTable.getSelectionModel().getSelectedItem();
        String costString = Double.toString(selected.getCost());
        String cantidadString = Integer.toString(selected.getUnits());
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewGasto/ViewGasto_FXML.fxml"));
        Parent root = loader.load();
        
        ViewGasto_FXMLController viewController = loader.getController();
        viewController.initializateData(selected.getCategory().getName(), costString,
                cantidadString,
                selected.getDate().toString(), selected.getName(), 
                selected.getDescription(),selected.getImageScan());
        
        Stage stage = new Stage();
        stage.setTitle("Visualizar Gasto");
        String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(goToCategory.getScene().getWindow());
        
        stage.show();
    }
    
    @FXML
    private void goToModificarHandle() throws IOException{
        Charge selected = chargeTable.getSelectionModel().getSelectedItem();
        String costString = Double.toString(selected.getCost());
        String cantidadString = Integer.toString(selected.getUnits());
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditGasto/EditGasto_FXML.fxml"));
        Parent root = loader.load();
        
        EditGasto_FXMLController viewController = loader.getController();
        viewController.initializateData(selected.getCategory().getName(), costString,
                cantidadString, selected.getDate(), selected.getName(), 
              selected.getDescription(), selected.getImageScan(), selected);
        
        Stage stage = new Stage();
        stage.setTitle("Visualizar Gasto");
        String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        
        stage.setOnHidden(e -> {
            try {
                updateCharges();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
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
        String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(NOBORRAR.getScene().getWindow());
        stage.getIcons().add(new Image("./assets/ww_black.png"));
        stage.setResizable(false);
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

        ButtonType buttonAceptar = new ButtonType("Aceptar");
        ButtonType buttonCancelar = new ButtonType("Cancelar");

        logOut.getButtonTypes().setAll(buttonAceptar, buttonCancelar);

        Optional<ButtonType> result = logOut.showAndWait();
        if (result.isPresent() && result.get() == buttonAceptar) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login_FXML.fxml"));
            Parent root = loader.load();
            Login_Main.scene = new Scene(root);
            String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
            Login_Main.scene.getStylesheets().add(css);
            Login_Main.mainStage.setScene(Login_Main.scene);
            Login_Main.mainStage.setTitle("Login Usuario");
            Login_Main.mainStage.getIcons().add(new Image("./assets/ww_black.png"));
            Login_Main.mainStage.setResizable(false);
            Login_Main.mainStage.showAndWait();
        } else {
        }
    }

    @FXML
    private void PrintingButton(ActionEvent event) throws IOException {
    try {
        PDDocument dpdf = new PDDocument();
        PDPage page = new PDPage();
        dpdf.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(dpdf, page);

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float anchuraTable = page.getMediaBox().getWidth() - (2 * margin);
        float y = yStart;
        float marginBajo = 70;
        float marginCelda = 10;
        float alturaRow = 20;
        float tableBajoY = marginBajo;
        boolean draw = true;
        TableView.TableViewSelectionModel<Charge> selectionModel = chargeTable.getSelectionModel();
        ObservableList<TableColumn<Charge, ?>> columns = chargeTable.getColumns();
        double[] columnsWidthPercentage = {0.1, 0.2, 0.2, 0.2, 0.2};

        
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        for (int i = 0; i < columns.size(); i++) {
            TableColumn<Charge, ?> column = columns.get(i);
            double columnWidth = anchuraTable * columnsWidthPercentage[i];
            String columnHeader = column == categoryColumn ? "Categoría" : column.getText();
            contentStream.beginText();
            contentStream.newLineAtOffset((float) (margin + (i * columnWidth)), y);
            contentStream.showText(columnHeader);
            contentStream.endText();
        }
        y -= alturaRow;
        
        for (Charge charge : cargos) {
            if (draw) {
                y -= alturaRow;
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                for (int i = 0; i < columns.size(); i++) {
                    TableColumn<Charge, ?> column = columns.get(i);
                    double columnWidth = anchuraTable * columnsWidthPercentage[i];
                    String cellValue = column == categoryColumn ? charge.getCategory().getName() : 
                                                                  column.getCellObservableValue(charge).getValue().toString();
                    contentStream.beginText();
                    contentStream.newLineAtOffset((float) (margin + (i * columnWidth) + marginCelda), y);
                    contentStream.showText(cellValue);
                    contentStream.endText();
                }
            }
        }

        contentStream.close();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            dpdf.save(file);
        }
        dpdf.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
 }
    
    private void addCategories() throws IOException {
        try {
            List<Category> categoryList = Acount.getInstance().getUserCategories();
            if (categoryList == null || categoryList.isEmpty()) return;
            for (Category category : categoryList) {
                MenuItem item = new MenuItem();
                String categoryName = category.getName();
                item.setText(categoryName);
                item.setOnAction(event -> {
                    updateCategory(category);
                });
                categories.add(item);
            }
        } catch (AcountDAOException e) {
            e.printStackTrace();
        }
    }
    
    private void updateCategory(Category category) {
        this.category = category;
        selectedCategory.set(category.getName()); 
        labelTableCategory.setText(category.getName());
        
        filteredCharges.setPredicate(charge -> {
        if (category == null) {
            return true; // Mostrar todos los cargos si no se selecciona ninguna categoría
        }
        return charge.getCategory().equals(category);
        });
    }
    
    @FXML
    private void Estadisticas(ActionEvent event) throws IOException {
        Scene currentScene = NOBORRAR.getScene();
        double currentHeight = currentScene.getHeight();
        double currentWidth = currentScene.getWidth();
        
        Parent root = FXMLLoader.load(getClass().getResource("/Estadisticas/Estadisticas_FXML.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage window = (Stage) NOBORRAR.getScene().getWindow();
        window.setScene(scene);
        //window.setHeight(currentHeight);
        //window.setWidth(currentWidth);
        window.centerOnScreen();
        window.setTitle("Estadisticas");
        window.show();
    }
}