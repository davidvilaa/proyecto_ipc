package MainMenu;

import model.*;
import javafx.scene.control.*;

public class Celda extends TableCell<Charge, Category> {
    
    @Override
    protected void updateItem(Category t, boolean bln){
        super.updateItem(t, bln);
        
        if(bln || t == null) setText(null);
        else setText(t.getName());
    }
}
