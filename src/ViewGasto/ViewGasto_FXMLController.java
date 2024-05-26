package ViewGasto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import MainMenu.MainMenu_FXMLController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewGasto_FXMLController implements Initializable {

    @FXML
    private Label categoryLabel;
    @FXML
    private Label costLabel;
    @FXML
    private Label cantidadLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView image;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    public void initializateData(String category, String cost, String cantidad, String date, String title, String description, Image image) {
        categoryLabel.setText(category);
        costLabel.setText(cost);
        cantidadLabel.setText(cantidad);
        dateLabel.setText(date);
        titleLabel.setText(title);
        descriptionLabel.setText(description);
        this.image.setImage(image);
    }

    @FXML
    private void abrirFotoGrande(MouseEvent event) throws IOException {
        FXMLLoader foto = new FXMLLoader(getClass().getResource("/ViewGasto/FOTOGRANDE_FXML.fxml"));
                    Parent root = foto.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Factura");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setResizable(false);

                    FOTOGRANDE_FXMLController cont = foto.getController();
                    cont.initImage(image.getImage());

                    stage.showAndWait();
    }
}
