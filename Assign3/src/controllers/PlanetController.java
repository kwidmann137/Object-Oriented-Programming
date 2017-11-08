package controllers;

import Builders.PlanetBuilder;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import models.Planet;
import models.PlanetAttribute;
import models.PlanetFileGateway;
import models.PlanetValidator;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class PlanetController {

    @FXML
    private ImageView planetImage;

    @FXML
    private Button selectImageButton;

    @FXML
    private TextField planetName;

    @FXML
    private TextField planetDiameterKM;

    @FXML
    private TextField planetDiameterM;

    @FXML
    private TextField planetMeanSurfaceTempC;

    @FXML
    private TextField planetMeanSurfaceTempF;

    @FXML
    private TextField planetNumberOfMoons;

    @FXML
    private Label fancyPlanetName;

    @FXML
    private Text nameError;

    @FXML
    private Text temperatureError;

    @FXML
    private Text diameterError;

    @FXML
    private Text moonsError;

    private final FileChooser fileChooser = new FileChooser();
    private Stage stage;
    private Planet currentPlanet;
    private PlanetValidator planetValidator;

    public PlanetController(Stage stage){
        this.stage = stage;
        this.currentPlanet = new Planet();
        this.planetValidator = new PlanetValidator();
    }

    public void initializeComponents(){

        planetName.textProperty().addListener((obj, oldText, newText) -> {
            handleNameChange(newText, oldText);
        });

        planetDiameterKM.textProperty().addListener((obj, oldText, newText) -> {
            //Need to invoke the update since it alters the text field value, which
            //can not be done by the listener
            Platform.runLater(() -> {
                handleDiameterChange(newText, oldText);
                planetDiameterKM.end();
            });
        });

        planetMeanSurfaceTempC.textProperty().addListener((obj, oldText, newText) -> {
            handleTemperatureChange(newText, oldText);
        });

        planetNumberOfMoons.textProperty().addListener((obj, oldText, newText) -> {
            handleMoonsChange(newText, oldText);
        });

        planetImage.setImage(new Image("images/no_image.png"));

        currentPlanet.addErrorListener(new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {

                if(change.getKey() == PlanetAttribute.NAME){
                    nameError.setText(change.getMap().get(PlanetAttribute.NAME));
                }else if(!change.getMap().containsKey(PlanetAttribute.NAME)){
                    nameError.setText("");
                }

            }
        });

        currentPlanet.addErrorListener(new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {

                if(change.getKey() == PlanetAttribute.MOONS){
                    moonsError.setText(change.getMap().get(PlanetAttribute.MOONS));
                }else if(!change.getMap().containsKey(PlanetAttribute.MOONS)){
                    moonsError.setText("");
                }

            }
        });

        currentPlanet.addErrorListener(new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {

                if(change.getKey() == PlanetAttribute.DIAMETER){
                    diameterError.setText(change.getMap().get(PlanetAttribute.DIAMETER));
                }else if(!change.getMap().containsKey(PlanetAttribute.DIAMETER)){
                    diameterError.setText("");
                }

            }
        });

        currentPlanet.addErrorListener(new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {

                if(change.getKey() == PlanetAttribute.TEMPERATURE){
                    temperatureError.setText(change.getMap().get(PlanetAttribute.TEMPERATURE));
                }else if(!change.getMap().containsKey(PlanetAttribute.TEMPERATURE)){
                    temperatureError.setText("");
                }

            }
        });

        currentPlanet.addErrorListener(new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {

                if(change.getKey() == PlanetAttribute.IMAGE_FILE){
                    Alert fileAlert = new Alert(Alert.AlertType.ERROR);
                    fileAlert.setContentText(change.getMap().get(PlanetAttribute.IMAGE_FILE));
                    fileAlert.show();
                }

            }
        });
    }

    private void loadCurrentPlanet(){
        planetName.setText(currentPlanet.getName());
        planetMeanSurfaceTempC.setText(String.valueOf(currentPlanet.getTemperature()));
        planetDiameterKM.setText(String.valueOf(currentPlanet.getDiameter()));
        planetNumberOfMoons.setText(String.valueOf(currentPlanet.getNumberOfMoons()));
        planetImage.setImage(currentPlanet.getImage());
    }

    @FXML
    void selectImage(ActionEvent event) {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            currentPlanet.setImageFile(file);
            planetImage.setImage(currentPlanet.getImage());
        }
    }

    @FXML
    void loadPlanet(ActionEvent event){
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            //build planet
            try {
                currentPlanet = PlanetBuilder.BuildPlanetFromFile(file);

                loadCurrentPlanet();

            } catch (IOException e) {

                Alert failedLoadAlert = new Alert(Alert.AlertType.ERROR);
                failedLoadAlert.setContentText("Failed to load planet.");
                failedLoadAlert.show();
            }
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

        currentPlanet.setName(newName);

        fancyPlanetName.setText(currentPlanet.getName());
    }

    void handleTemperatureChange(String newCelsius, String oldCelsius){

        currentPlanet.setTemperature(newCelsius);

        Double newTempF = (32 + (currentPlanet.getTemperature()*9 / 5));

        DecimalFormat df = new DecimalFormat("#.##");

        planetMeanSurfaceTempF.setText(df.format(newTempF));

    }

    void handleDiameterChange(String newKM, String oldKM){

        currentPlanet.setDiameter(newKM);

        int newDiameterM = currentPlanet.getDiameter() * 1000;

        if(currentPlanet.getDiameter() > 1000){
            planetDiameterKM.setText(String.format("%,d", currentPlanet.getDiameter()));
        }

        planetDiameterM.setText(String.format("%,d", newDiameterM));

    }

    void handleMoonsChange(String newMoons, String oldMoons){

        currentPlanet.setNumberOfMoons(newMoons);

    }
}