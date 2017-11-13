package Controllers;

import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import Models.PlanetAttributes;

class PlanetControllerInitializer {

    private static PlanetController controller;
    private static final Object lock = new Object();

    static void initialize(PlanetController planetController){

        controller = planetController;
        synchronized (lock){
            initializeComponents();
        }
    }

    private static void initializeComponents(){

        initializeTextListeners();

        initializeErrorListeners();

    }

    private static void initializeTextListeners(){
        controller.planetName.textProperty().addListener((obj, oldText, newText) -> {
            controller.handleNameChange(newText, oldText);
        });

        controller.planetDiameterKM.textProperty().addListener((obj, oldText, newText) -> {
            controller.handleDiameterChange(newText, oldText);
            controller.planetDiameterKM.end();
        });


        controller.planetMeanSurfaceTempC.textProperty().addListener((obj, oldText, newText) -> {
            controller.handleTemperatureChange(newText, oldText);
        });

        controller.planetNumberOfMoons.textProperty().addListener((obj, oldText, newText) -> {
            controller.handleMoonsChange(newText, oldText);
        });
    }

    private static void initializeErrorListeners(){

        HookErrorToText(PlanetAttributes.NAME, controller.nameError);

        HookErrorToText(PlanetAttributes.MOONS, controller.moonsError);

        HookErrorToText(PlanetAttributes.DIAMETER, controller.diameterError);

        HookErrorToText(PlanetAttributes.TEMPERATURE, controller.temperatureError);

        HookErrorToAlert(PlanetAttributes.IMAGE_URL, Alert.AlertType.ERROR);
    }

    static void initializeErrorListeners(PlanetController planetController){
        controller = planetController;

        synchronized (lock){
            initializeErrorListeners();
        }
    }

    private static void HookErrorToText(PlanetAttributes attribute, Text text){

        controller.currentPlanet.addErrorListener(new MapChangeListener<PlanetAttributes, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttributes, ? extends String> change) {
                if(change.getKey() == attribute){
                    text.setText(change.getMap().get(attribute));
                }else if(!change.getMap().containsKey(attribute)){
                    text.setText("");
                }
            }
        });
    }

    private static void HookErrorToAlert(PlanetAttributes attribute, Alert.AlertType alertType){
        controller.currentPlanet.addErrorListener(new MapChangeListener<PlanetAttributes, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttributes, ? extends String> change) {

                if(change.getKey() == attribute && change.getMap().get(attribute) != null){
                    Alert fileAlert = new Alert(alertType);
                    fileAlert.setContentText(change.getMap().get(attribute));
                    fileAlert.showAndWait();
                }

            }
        });
    }
}
