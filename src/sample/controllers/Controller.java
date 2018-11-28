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
    public String addition( Matrice as, Matrice bs ){

        if(as.getRows()==bs.getRows()&&as.getColumns()==as.getColumns())
        { int tempoa;
            int tempob;
            int addition;
            Matrice resultat=new Matrice("",as.getColumns(), as.getRows());
            for(int i=0; i<as.getRows();i++){
                for(int j=0; j<as.getColumns();j++){
                    tempoa=(int)as.getValue(j,i);
                    tempob=(int)bs.getValue(j,i);
                    addition= tempoa+tempob;
                    resultat.setValue(addition,j,i);
                }
            }
            String finalle= resultat.toString();
            return finalle;
        }
        else{
            return "Les formats des matrices ne sont pas identiques.";
        }

    }
    public String soustration( Matrice as, Matrice bs ){

        if(as.getRows()==bs.getRows()&&as.getColumns()==as.getColumns())
        { int tempoa;
            int tempob;
            int soustraction;
            Matrice resultat=new Matrice("",as.getColumns(), as.getRows());
            for(int i=0; i<as.getRows();i++){
                for(int j=0; j<as.getColumns();j++){
                    tempoa=(int)as.getValue(j,i);
                    tempob=(int)bs.getValue(j,i);
                    soustraction= tempoa-tempob;
                    resultat.setValue(soustraction,j,i);
                }
            }
            String finalle= resultat.toString();
            return finalle;
        }
        else{
            return "Les formats des matrices ne sont pas identiques.";
        }

    }
    public String multiplierScalaire(int nombre, Matrice initiale) {
        Matrice resultat= new Matrice("",initiale.getColumns(),initiale.getRows());
        for(int i=0; i<initiale.getRows();i++){
            for(int j=0;j<initiale.getColumns();j++){
                int temporaire= (int)initiale.getValue(j,i)*nombre;
                resultat.setValue(temporaire,j,i);
            }
        }
        String finalle=resultat.toString();
        return finalle;
    }
    public String produitMatriciel(Matrice a,Matrice b){

        if(a.getColumns()==b.getRows()){
            Matrice resultat = new Matrice("",b.getColumns(),a.getRows());
            for(int i=0;i<a.getRows();i++){
                for(int j=0;j<a.getColumns();j++){
                }
            }
        }


        return new String();
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
