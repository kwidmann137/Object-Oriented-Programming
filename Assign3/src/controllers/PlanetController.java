package controllers;

import Builders.PlanetBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import models.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class PlanetController {

    @FXML
    private ImageView planetImage;

    @FXML
    private Button selectImageButton;

    @FXML
    TextField planetName, planetDiameterKM,  planetMeanSurfaceTempC, planetNumberOfMoons;

    @FXML
    private TextField planetDiameterM, planetMeanSurfaceTempF;

    @FXML
    private Label fancyPlanetName;

    @FXML
    Text nameError, temperatureError, diameterError, moonsError;

    private final FileChooser fileChooser = new FileChooser();
    private Stage stage;
    Planet currentPlanet;

    public PlanetController(Stage stage){
        this.stage = stage;
        this.currentPlanet = new Planet();
    }

    public void initializeComponents(){

        PlanetControllerInitializer.initialize(this);

        this.planetImage.setImage(new Image(currentPlanet.getImageUrl()));
    }

    private void loadCurrentPlanet(){

        PlanetControllerInitializer.initializeErrorListeners(this);

        planetName.setText(currentPlanet.getName());
        planetMeanSurfaceTempC.setText(String.valueOf(currentPlanet.getTemperature()));
        planetDiameterKM.setText(String.valueOf(currentPlanet.getDiameter()));
        planetNumberOfMoons.setText(String.valueOf(currentPlanet.getNumberOfMoons()));
        planetImage.setImage(new Image(currentPlanet.getImageUrl()));
    }

    @FXML
    void selectImage(ActionEvent event) {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            currentPlanet.setImageUrlFromFile(file);
            planetImage.setImage(new Image(currentPlanet.getImageUrl()));
        }
    }

    @FXML
    void confirmLoadPlanet(ActionEvent event){

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            ConfirmationAlert confirmLoadAlert = new ConfirmationAlert("Are you sure you want to replace the current planet?  Any unsaved data will be lost.");
            confirmLoadAlert.showAndWait();

            if(confirmLoadAlert.confirmed()){
                loadPlanet(file);
            }

        }
    }

    private void loadPlanet(File file){

        try {
            currentPlanet = PlanetBuilder.BuildPlanetFromFile(file);
            loadCurrentPlanet();
        } catch (IOException e) {
            Alert failedLoadAlert = new Alert(Alert.AlertType.ERROR);
            failedLoadAlert.setContentText("Failed to load planet.");
            failedLoadAlert.show();
        }

    }

    @FXML
    void savePlanet(ActionEvent event){

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try{
                currentPlanet.saveToFile(file);
            } catch (Exception e) {
                Alert failedSaveAlert = new Alert(Alert.AlertType.ERROR);
                failedSaveAlert.setContentText(e.getMessage());
                failedSaveAlert.show();
            }
        }

    }

    void handleNameChange(String newName, String oldName){

        if(currentPlanet.getValidator().validateName(newName)){
            currentPlanet.setName(newName);

            fancyPlanetName.setText(currentPlanet.getName());
        }
    }

    void handleTemperatureChange(String newCelsius, String oldCelsius){

        if(currentPlanet.getValidator().validateTemperature(newCelsius)){

            //ToDo: Refactor this to get rid of try catch
            try {

                Double validTemp = NumberFormat.getNumberInstance(Locale.US).parse(newCelsius).doubleValue();

                currentPlanet.setTemperature(validTemp);

                Double newTempF = (32 + (currentPlanet.getTemperature()*9 / 5));

                DecimalFormat df = new DecimalFormat("#.##");

                planetMeanSurfaceTempF.setText(df.format(newTempF));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    void handleDiameterChange(String newKM, String oldKM){

        if(currentPlanet.getValidator().validateDiameter(newKM)){

            try {

                int validDiameter = NumberFormat.getNumberInstance(Locale.US).parse(newKM).intValue();

                currentPlanet.setDiameter(validDiameter);

                int newDiameterM = validDiameter * 1000;

                if (newKM.length() > 0) {
                    planetDiameterKM.setText(String.format("%,d", currentPlanet.getDiameter()));
                    planetDiameterM.setText(String.format("%,d", newDiameterM));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    void handleMoonsChange(String newMoons, String oldMoons){

        if(currentPlanet.getValidator().validateMoons(newMoons)){
            int validMoons = Integer.parseInt(newMoons);
            currentPlanet.setNumberOfMoons(validMoons);
        }
    }
}