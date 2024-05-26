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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        stage.setScene(new Scene(root));
 
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
        stage.setScene(new Scene(root));
 
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
        stage.setScene(new Scene(root));
 
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
        stage.setScene(new Scene(root));
 
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
        stage.setScene(new Scene(root));
 
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
    @FXML
 private void addCategories() throws IOException {
    try {
        List<Category> categoryList = Acount.getInstance().getUserCategories();
        if (categoryList == null || categoryList.isEmpty()) return;
 
        MenuItem allItem = new MenuItem("Todas las Categorías");
        allItem.setOnAction(event -> {
            tableCategory.setText("Todas");    
            updateCategory("Todas"); // Passing null to indicate all categories
        });
        categories.add(allItem);
 
        for (Category category : categoryList) {
            MenuItem item = new MenuItem(category.getName());
            item.setOnAction(event -> {
                tableCategory.setText(category.getName());
                updateCategory(category.getName());
            });
            categories.add(item);
        }
    } catch (AcountDAOException e) {
        e.printStackTrace();
    }
}
 
private void updateCategory(String catName) {
    List<Charge> listaSinFiltrar;
    try {
        listaSinFiltrar = Acount.getInstance().getUserCharges();
 
        List<Charge> listaFiltrada = new ArrayList<>();
 
        if (catName.equals("Todas")) {
            cargos.setAll(listaSinFiltrar);
        } else {
            for (Charge charge : listaSinFiltrar) {
                if (charge.getCategory().getName().equals(catName)) {
                    listaFiltrada.add(charge);
                }
            }
            cargos.setAll(listaFiltrada);
        }
 
        chargeTable.refresh();
    } catch (AcountDAOException | IOException ex) {
        ex.printStackTrace();
    }
}
 
    @FXML
    private void Estadisticas(ActionEvent event) throws IOException {
     FXMLLoader loader = new FXMLLoader(getClass().getResource("/Estadisticas/Estadisticas_FXML.fxml"));
        Parent root = loader.load();
 
        Stage stage = new Stage();
        stage.setTitle("Estadisticas");
        stage.setScene(new Scene(root));
 
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(statsButton.getScene().getWindow());
        stage.getIcons().add(new Image("./assets/ww_black.png"));
        stage.setResizable(false);
        stage.showAndWait();  
    }
}