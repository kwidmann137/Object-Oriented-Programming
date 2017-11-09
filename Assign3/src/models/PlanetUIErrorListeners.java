package models;

import javafx.collections.MapChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by kyle on 11/8/17.
 */
public class PlanetUIErrorListeners {

    public static MapChangeListener<PlanetAttribute, String> getImageFileErrorListener(){

        return new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {

                if(change.getKey() == PlanetAttribute.IMAGE_FILE){
                    Alert fileAlert = new Alert(Alert.AlertType.ERROR);
                    fileAlert.setContentText(change.getMap().get(PlanetAttribute.IMAGE_FILE));
                    fileAlert.show();
                }

            }
        };
    }

    public static MapChangeListener<PlanetAttribute, String> getTemperatureErrorListener(Text temperatureError){

        return new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {

                if(change.getKey() == PlanetAttribute.TEMPERATURE){
                    temperatureError.setText(change.getMap().get(PlanetAttribute.TEMPERATURE));
                }else if(!change.getMap().containsKey(PlanetAttribute.TEMPERATURE)){
                    temperatureError.setText("");
                }

            }
        };
    }
}
