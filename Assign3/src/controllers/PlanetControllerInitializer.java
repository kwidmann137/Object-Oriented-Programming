package controllers;

import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import models.PlanetAttribute;

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
            Platform.runLater(() -> {
                controller.handleDiameterChange(newText, oldText);
                controller.planetDiameterKM.end();
            });
        });

        controller.planetMeanSurfaceTempC.textProperty().addListener((obj, oldText, newText) -> {
            controller.handleTemperatureChange(newText, oldText);
        });

        controller.planetNumberOfMoons.textProperty().addListener((obj, oldText, newText) -> {
            controller.handleMoonsChange(newText, oldText);
        });
    }

    private static void initializeErrorListeners(){

        HookErrorToText(PlanetAttribute.NAME, controller.nameError);

        HookErrorToText(PlanetAttribute.MOONS, controller.moonsError);

        HookErrorToText(PlanetAttribute.DIAMETER, controller.diameterError);

        HookErrorToText(PlanetAttribute.TEMPERATURE, controller.temperatureError);

        HookErrorToAlert(PlanetAttribute.IMAGE_URL, Alert.AlertType.ERROR);
    }

    static void initializeErrorListeners(PlanetController planetController){
        controller = planetController;

        synchronized (lock){
            initializeErrorListeners();
        }
    }

    private static void HookErrorToText(PlanetAttribute attribute, Text text){

        controller.currentPlanet.addErrorListener(new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {
                if(change.getKey() == attribute){
                    text.setText(change.getMap().get(attribute));
                }else if(!change.getMap().containsKey(attribute)){
                    text.setText("");
                }
            }
        });
    }

    private static void HookErrorToAlert(PlanetAttribute attribute, Alert.AlertType alertType){
        controller.currentPlanet.addErrorListener(new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {

                if(change.getKey() == attribute && change.getMap().get(attribute) != null){
                    Alert fileAlert = new Alert(alertType);
                    fileAlert.setContentText(change.getMap().get(attribute));
                    fileAlert.showAndWait();
                }

            }
        });
    }
}
