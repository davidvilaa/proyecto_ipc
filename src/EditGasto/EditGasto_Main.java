package EditGasto;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;

public class EditGasto_Main extends Application {
    
    private static Scene scene;
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditGasto_FXML.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setTitle("Editar Gasto");
        mainStage.getIcons().add(new Image("./assets/ww_black.png"));
        mainStage.setResizable(false);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
