package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import sample.data.Matrice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Controller {
    private HashMap<String, Matrice> map = new HashMap<>();
    private ArrayList<TextField> textFields = new ArrayList<>();
    private int tfWidth = 60, tfHeight = 30;
    private ArrayList<String> listeNoms = new ArrayList<>();
    private ObservableList<String> observableList;
    private ChoiceBox<String> choixGauche, choixDroite;

    @FXML
    private Spinner spinnerLignes, spinnerColonnes;

    @FXML
    private GridPane gridPane;

    @FXML
    private HBox hBox;

    public void initialize() {
        listeNoms.add("");
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        setChoiceBox();

        gridPane.setAlignment(Pos.CENTER);

        gridPane.setGridLinesVisible(true);
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
        String resultat = "";
        boolean notNew = true;

        while (notNew) {
            notNew = false;

            TextInputDialog alerte = new TextInputDialog("Entrez ici");

            alerte.setTitle("Nom de matrice");
            alerte.setHeaderText("Veuillez entrer le nom de la matrice");
            alerte.setContentText("Nom: ");
            resultat = alerte.showAndWait().get();

            for (String nom : listeNoms)
                if (nom.equals(resultat) || resultat.isEmpty()) notNew = true;
        }

        listeNoms.add(resultat);

        Matrice tempo = new Matrice(resultat, (int) spinnerColonnes.getValue(), (int) spinnerLignes.getValue());

        int value = 0;
        for(int i = 0; i < (int) spinnerLignes.getValue() ;i++){
            for(int j = 0; j < (int) spinnerColonnes.getValue(); j++) {
                if (!textFields.get(value).getText().isEmpty())
                    tempo.setValue(Float.parseFloat(textFields.get(value).getText()), j, i);
                else
                    tempo.setValue(0, j, i);

                value++;
            }
        }
        setChoiceBox();
        map.put(resultat,tempo);
    }

    public void getMatrice() {

    }

    public void setChoiceBox() {
        hBox.getChildren().clear();

        observableList = FXCollections.observableList((List) listeNoms);
        choixGauche = new ChoiceBox<>(observableList);
        choixGauche.setValue(listeNoms.get(0));
        choixGauche.setMaxWidth(Region.USE_PREF_SIZE);
        choixGauche.setMaxHeight(Region.USE_PREF_SIZE);
        choixGauche.setMinWidth(Region.USE_PREF_SIZE);
        choixGauche.setMinHeight(Region.USE_PREF_SIZE);
        choixGauche.setPrefWidth(70);
        choixGauche.setPrefHeight(25);

        choixDroite = new ChoiceBox<>(observableList);
        choixDroite.setValue(listeNoms.get(0));
        choixDroite.setMaxWidth(Region.USE_PREF_SIZE);
        choixDroite.setMaxHeight(Region.USE_PREF_SIZE);
        choixDroite.setMinWidth(Region.USE_PREF_SIZE);
        choixDroite.setMinHeight(Region.USE_PREF_SIZE);
        choixDroite.setPrefWidth(70);
        choixDroite.setPrefHeight(25);

        hBox.getChildren().addAll(choixGauche, choixDroite);
    }
}
