package sample.controllers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
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

    @FXML
    private Spinner spinnerLignes, spinnerColonnes;

    @FXML
    private GridPane gridPane;

    public void initialize() {
        gridPane.setAlignment(Pos.CENTER);

        gridPane.setGridLinesVisible(false);
        gridPane.setMaxSize((int) spinnerColonnes.getValue()*60, (int) spinnerLignes.getValue()*30 );

    /*    ColumnConstraints colConstraint = new ColumnConstraints(50);
        colConstraint.setHalignment(HPos.RIGHT);

        RowConstraints rowConstraints = new RowConstraints(50);
        rowConstraints.setValignment(VPos.CENTER);*/

        for (int y = 0; y < (int) spinnerLignes.getValue(); y++) { //Lignes
            for (int x = 0; x < (int) spinnerColonnes.getValue(); x++) { //Colonnes
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
            //    gridPane.getColumnConstraints().add(colConstraint);
            }
          //  gridPane.getRowConstraints().add(rowConstraints);
        }
    }


    private void ajouterMatrice() {
    }
}
