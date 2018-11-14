package sample.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import sample.data.Matrice;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    private HashMap<String, Matrice> map = new HashMap<>();
    private ArrayList<TextField> textFields = new ArrayList<>();

    @FXML
    private Spinner spinnerLignes, spinnerColonnes;

    @FXML
    private GridPane gridPane;

    public void initialize() {
        for (int y = 0; y < 10; y++) { //Lignes
            for (int x = 0; x < 10; x++) { //Colonnes
                TextField temp = new TextField();

                temp.setAlignment(Pos.CENTER);
                temp.setMaxWidth(Region.USE_PREF_SIZE);
                temp.setMaxHeight(Region.USE_PREF_SIZE);
                temp.setMinWidth(Region.USE_PREF_SIZE);
                temp.setMinHeight(Region.USE_PREF_SIZE);
                temp.setPrefWidth(60);
                temp.setPrefHeight(30);
                textFields.add(temp);
                gridPane.add(temp, x, y);
            }
        }
    }


    private void ajouterMatrice() {
    }
}
