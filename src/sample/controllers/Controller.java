package sample.controllers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import sample.data.Matrice;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    private HashMap<String, Matrice> map = new HashMap<>();
    private ArrayList<TextField> textFields = new ArrayList<>();
    private int tfWidth = 60, tfHeight = 30;

    @FXML
    private Spinner spinnerLignes, spinnerColonnes;

    @FXML
    private GridPane gridPane;

    public void initialize() {
        gridPane.setAlignment(Pos.CENTER);

        gridPane.setGridLinesVisible(false);
        setGridPane();

        spinnerLignes.valueProperty().addListener((that, oldValue, newValue) -> setGridPane());
        spinnerColonnes.valueProperty().addListener((that, oldValue, newValue) -> setGridPane());
    }

    private void setGridPane() {
        gridPane.getChildren().clear();
        textFields.clear();

        gridPane.setMaxSize((int) spinnerColonnes.getValue()*tfWidth, (int) spinnerLignes.getValue()*tfHeight );

        for (int y = 0; y < (int) spinnerLignes.getValue(); y++) {
            for (int x = 0; x < (int) spinnerColonnes.getValue(); x++) {
                TextField temp = new TextField();
                temp.setPromptText("0");

                temp.setAlignment(Pos.CENTER);
                temp.setMaxWidth(Region.USE_PREF_SIZE);
                temp.setMaxHeight(Region.USE_PREF_SIZE);
                temp.setMinWidth(Region.USE_PREF_SIZE);
                temp.setMinHeight(Region.USE_PREF_SIZE);
                temp.setPrefWidth(tfWidth);
                temp.setPrefHeight(tfHeight);


                textFields.add(temp);
                gridPane.add(temp, x, y);
            }
        }
    }

    public void ajouterMatrice() {

        TextInputDialog alerte = new TextInputDialog("Entrez ici");
        alerte.setTitle("Information importante");
        alerte.setHeaderText("Veuillez entrer le nom de la matrice");
        alerte.setContentText("nom: ");
        String resultat = alerte.showAndWait().get();

        Matrice tempo= new Matrice(resultat,(int)spinnerColonnes.getValue(),(int)spinnerLignes.getValue());

        int value=0;
        for(int i=0;i<(int)spinnerLignes.getValue();i++){

            for(int j=0; j<(int)spinnerColonnes.getValue();j++)
            {
                tempo.setValue(Float.parseFloat(textFields.get(value).getText()),j,i);
                value++;
            }
        }
        map.put(resultat,tempo);
    }
}
