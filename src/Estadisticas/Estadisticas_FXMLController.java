package Estadisticas;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.table.TableColumn;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import MainMenu.MainMenu_FXMLController;

public class Estadisticas_FXMLController implements Initializable {
    public int ventanaActual = 0;
    public double gastado = 0;
    public double gastado2 = 0;
    private LineChart<String, Number> Grafica;
    @FXML
    private LineChart<String, Number> GraficaMensual;
    @FXML
    private Button siguienteOpcion;
    public Label Gastos;
    @FXML
    private MenuButton menuTiempo;
    private Integer selectedYear; 
    @FXML
    private Button DESELECCIONAR;
    @FXML
    private PieChart porcentaje;
    @FXML
    private Button atrasOpcion;
    private Month mesSeleccionado;
    @FXML
    private MenuButton menuMes;
    @FXML
    private LineChart<String, Number> graficaDeMes;
    @FXML
    private Button PrintButton;
    @FXML
    private Button homeButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeMenuItems();
        initializeMenuMesItems();
        try {
            actualizarPieChart();
        } catch (IOException ex) {
            Logger.getLogger(Estadisticas_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        GraficaMensual.setVisible(false);
        XYChart.Series<String, Number> emptySeries = new XYChart.Series<>();
        graficaDeMes.getData().add(emptySeries);
        porcentaje.setVisible(true);
        actualizarTabla();
        actualizarTablaMes();
        
        PrintButton.setDisable(true);
    }
    
    private void actualizarPieChart() throws IOException {
        List<PieChart.Data> l = new ArrayList<>();
        try {
            Acount user = Acount.getInstance();
            for (Category cat : user.getUserCategories()) {
                Double dineroGastado = 0.0d;
                for (Charge cargos : user.getUserCharges()) {
                    if (!cat.getName().equals(cargos.getCategory().getName())) continue;
                    if (selectedYear != null && cargos.getDate().getYear() != selectedYear) continue; // Filtra por año si hay uno seleccionado
                    dineroGastado += cargos.getCost();
                }
                if (dineroGastado == 0.0d) continue;
                l.add(new PieChart.Data(cat.getName(), dineroGastado));
            }
        } catch (AcountDAOException ex) {
            ex.printStackTrace();
        }
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(l);
        porcentaje.setData(pieChartData);
    }   
    
    private void initializeMenuItems() {
        Set<Integer> years = new HashSet<>();
        try {
            Acount cuentaUsuario = Acount.getInstance();
            for (Charge cargo : cuentaUsuario.getUserCharges()) {
                years.add(cargo.getDate().getYear());
            }

            List<Integer> sortedYears = new ArrayList<>(years);
            Collections.sort(sortedYears, Collections.reverseOrder());

            MenuItem allYearsItem = new MenuItem("Todos los años");
            allYearsItem.setOnAction(event -> {
                selectedYear = null;
                try {
                    actualizarPieChart();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                menuTiempo.setText("Año: Todos");
                Platform.runLater(() -> {
                    DESELECCIONAR.requestFocus();
                });
            });
            menuTiempo.getItems().add(allYearsItem);

            for (Integer year : sortedYears) {
                MenuItem item = new MenuItem(year.toString());
                item.setOnAction(event -> {
                    this.selectedYear = year;
                    actualizarTabla();
                    actualizarTablaMes();
                    try {
                        actualizarPieChart();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    menuTiempo.setText("Año: " + year);
                    Platform.runLater(() -> {
                        DESELECCIONAR.requestFocus();
                    });
                });
                menuTiempo.getItems().add(item);
            }

        } catch (IOException | AcountDAOException e) {
            e.printStackTrace();
        }
    }

    public void actualizarTabla() {
        GraficaMensual.getData().clear();
        gastado = 0;

        Acount user = null;
        try {
            user = Acount.getInstance();
            for (Category categoria : user.getUserCategories()) {

                XYChart.Series<String, Number> series = new XYChart.Series<>();
                for (Month mes : Month.values()) {
                    Double gastoMesActual = 0.0d;
                    for (Charge cargo : user.getUserCharges()) {
                        if (selectedYear != null && cargo.getDate().getYear() != selectedYear) continue;
                        if (!cargo.getDate().getMonth().equals(mes)) continue;
                        if (!cargo.getCategory().getName().equals(categoria.getName())) continue;

                        gastoMesActual += cargo.getCost();
                    }

                    series.getData().add(new XYChart.Data<>(mes.toString(), gastoMesActual));
                    gastado += gastoMesActual;
                }
                series.setName(categoria.getName());
                GraficaMensual.getData().add(series);
            }
            
            Gastos.setText("Gastos Anuales: " + gastado + "€");
        } catch (IOException | AcountDAOException e) {
            e.printStackTrace();
        }
    }
    
    private void initializeMenuMesItems(){
    for (Month mes : Month.values()) {
        MenuItem menuItem = new MenuItem(mes.toString());
        menuItem.setOnAction(event -> seleccionarMes(mes));
        menuMes.getItems().add(menuItem);
                    Platform.runLater(() -> {
                        DESELECCIONAR.requestFocus();
                    });
    }
    
    // Selecciona el mes actual por defecto
    mesSeleccionado = LocalDate.now().getMonth();
    }

    public void seleccionarMes(Month mes) {
        mesSeleccionado = mes;

        actualizarTablaMes(); // Actualiza la tabla cuando se selecciona un nuevo mes
    }
        public void actualizarTablaMes() {
        menuMes.setText("Mes: " + mesSeleccionado );
        graficaDeMes.getData().clear();
        gastado2 = 0;

        Acount user = null;
        try {
            user = Acount.getInstance();

            if (mesSeleccionado == null || selectedYear == null) {
                // Manejar el caso cuando no se selecciona ningún mes o año
                graficaDeMes.getData().forEach(series -> series.getData().clear());
                return;
            }

            // Obtén el número de días en el mes seleccionado
            YearMonth yearMonth = YearMonth.of(selectedYear.intValue(), mesSeleccionado);
            int diasEnMes = yearMonth.lengthOfMonth();

            for (Category categoria : user.getUserCategories()) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();

                for (int dia = 1; dia <= diasEnMes; dia++) {
                    Double gastoDiaActual = 0.0d;
                    LocalDate fechaActual = LocalDate.of(this.selectedYear, mesSeleccionado, dia);

                    for (Charge cargo : user.getUserCharges()) {
                        if (cargo.getDate().equals(fechaActual) && cargo.getCategory().getName().equals(categoria.getName())) {
                            gastoDiaActual += cargo.getCost();
                        }
                    }

                    series.getData().add(new XYChart.Data<>(String.valueOf(dia), gastoDiaActual));
                    gastado2 += gastoDiaActual;
                }

                series.setName(categoria.getName());
                graficaDeMes.getData().add(series);
            }

            Gastos.setText("Gastos del mes: " + gastado2 + "€");
        } catch (IOException | AcountDAOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pasarOpcion(ActionEvent event) {
        ventanaActual++;
        atrasOpcion.setDisable(false);
        if(ventanaActual == 1){
            porcentaje.setVisible(false);
            GraficaMensual.setVisible(true);
            siguienteOpcion.setDisable(false);
            graficaDeMes.setVisible(false);
            menuMes.setVisible(false);
            Gastos.setText("Gastos Anuales: " + gastado + "€");

        }
        if(ventanaActual == 2){
            porcentaje.setVisible(false);
            GraficaMensual.setVisible(false);
            graficaDeMes.setVisible(true);
            siguienteOpcion.setDisable(true);
            menuMes.setVisible(true);
            Gastos.setText("Gastos del mes: " + gastado2 + "€");

        }
        else{
        siguienteOpcion.setDisable(false);
        }
    }

    @FXML
    private void atrasarOpcion(ActionEvent event) {
    ventanaActual--;
    atrasOpcion.setDisable(false);
    if(ventanaActual == 0){
        atrasOpcion.setDisable(true);
        GraficaMensual.setVisible(false);
        porcentaje.setVisible(true);
        atrasOpcion.setDisable(true);
        graficaDeMes.setVisible(false);
        menuMes.setVisible(false);
        Gastos.setText("Gastos Anuales: " + gastado + "€");

    }
    if(ventanaActual == 1){
        GraficaMensual.setVisible(true);
        porcentaje.setVisible(false);
        graficaDeMes.setVisible(false);
        siguienteOpcion.setDisable(false);
        menuMes.setVisible(false);
        Gastos.setText("Gastos Anuales: " + gastado + "€");

        }
       
    }

    @FXML
    private void goToHome(ActionEvent event) throws IOException{
        Scene currentScene = DESELECCIONAR.getScene();
        double currentHeight = currentScene.getHeight();
        double currentWidth = currentScene.getWidth();
        
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu/MainMenu_FXML.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) DESELECCIONAR.getScene().getWindow();
        window.setScene(scene);
        //window.setHeight(currentHeight);
        //window.setWidth(currentWidth);
        window.centerOnScreen();
        window.setTitle("Menú Principal");
        window.show();
    }

    @FXML
    private void goToPrint(ActionEvent event) throws IOException {
    }
}