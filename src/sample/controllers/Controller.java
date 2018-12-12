package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import sample.data.Matrice;

import java.util.ArrayList;
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

    @FXML
    private TextArea textArea;

    @FXML
    private Button addition;
    Tooltip tooltip=new Tooltip("Additionnez 2 matrices de même format ensemble");

    @FXML
    private Button soustraction;
    Tooltip tooltip2=new Tooltip("Soustrayez la matrice de droite à celle de gauche (même format)");

    @FXML
    private Button scalaire;
    Tooltip tooltip3=new Tooltip("Multipliez une matrice par un scalaire");

    @FXML
    private Button inversion;
    Tooltip tooltip4= new Tooltip("Créez la matrice inverse d'une matrice carrée");

    @FXML
    private Button puissance;
    Tooltip tooltip5= new Tooltip("Multipliez votre matrice de façon exponentielle!");

    @FXML
    private Button tensoriel;
    Tooltip tooltip6= new Tooltip("Créez le produit tensoriel d'une matrice!");

    @FXML
    private Button determinant;
    Tooltip tooltip7= new Tooltip("Trouvez le déterminant d'une matrice carrée");

    @FXML
    private Button vectoriel;
    Tooltip tooltip8= new Tooltip("Créez le produit vectoriel de deux vecteurs 1*3");

    @FXML
    private Button matriciel;
    Tooltip tooltip9= new Tooltip("Multipliez deux matrices ensemble");

    @FXML
    private Button hadamard;
    Tooltip tooltip10= new Tooltip("Effectuez le produit d'Hadamard sur une matrice");

    @FXML
    private Button transposition;
    Tooltip tooltip11= new Tooltip("Effectuez la transposée de votre matrice");

    public void initialize() {
        listeNoms.add("");
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        addition.setTooltip(tooltip);
        soustraction.setTooltip(tooltip2);
        setChoiceBox();
        scalaire.setTooltip(tooltip3);
        inversion.setTooltip(tooltip4);
        puissance.setTooltip(tooltip5);
        tensoriel.setTooltip(tooltip6);
        determinant.setTooltip(tooltip7);
        vectoriel.setTooltip(tooltip8);
        matriciel.setTooltip(tooltip9);
        hadamard.setTooltip(tooltip10);
        transposition.setTooltip(tooltip11);

        gridPane.setAlignment(Pos.CENTER);

        gridPane.setGridLinesVisible(true);
        setGridPane();

        spinnerLignes.valueProperty().addListener((that, oldValue, newValue) -> setGridPane());
        spinnerColonnes.valueProperty().addListener((that, oldValue, newValue) -> setGridPane());

    }

    private void setGridPane() {
        gridPane.getChildren().clear();
        textFields.clear();

        gridPane.setMaxSize((int) spinnerColonnes.getValue() * tfWidth, (int) spinnerLignes.getValue() * tfHeight);

        for (int y = 0; y < (int) spinnerLignes.getValue(); y++) {
            for (int x = 0; x < (int) spinnerColonnes.getValue(); x++) {
                TextField temp = new TextField();
                temp.setPromptText("0");
                Tooltip tooltip= new Tooltip("Vous devez au moins entrer une valeur");

                temp.setAlignment(Pos.CENTER);
                temp.setMaxWidth(Region.USE_PREF_SIZE);
                temp.setMaxHeight(Region.USE_PREF_SIZE);
                temp.setMinWidth(Region.USE_PREF_SIZE);
                temp.setMinHeight(Region.USE_PREF_SIZE);
                temp.setPrefWidth(tfWidth);
                temp.setPrefHeight(tfHeight);


                textFields.add(temp);
                textFields.get(0).setTooltip(tooltip);
                gridPane.add(temp, x, y);
            }
        }
    }

    public void ajouterMatrice() {
        String resultat = "", texte = "Veuillez entrer le nom de la matrice";
        boolean notNew = true;

        while (notNew) {
            notNew = false;

            TextInputDialog alerte = new TextInputDialog("Entrez ici");

            alerte.setTitle("Nom de matrice");
            alerte.setHeaderText(texte);
            alerte.setContentText("Nom: ");
            resultat = alerte.showAndWait().get();

            for (String nom : listeNoms) {
                if (nom.equals(resultat) || resultat.isEmpty()) {
                    notNew = true;
                    texte = "Veuillez entrer un nom valide";
                }
            }
        }

        boolean worked = false;

        Matrice tempo = new Matrice(resultat, (int) spinnerColonnes.getValue(), (int) spinnerLignes.getValue());

        int value = 0;
        for (int i = 0; i < (int) spinnerLignes.getValue(); i++) {
            for (int j = 0; j < (int) spinnerColonnes.getValue(); j++) {

                if (!textFields.get(value).getText().isEmpty()) {
                    try {
                        tempo.setValue(Float.parseFloat(textFields.get(value).getText()), j, i);

                        worked = true;
                    } catch (NumberFormatException o) {
                        worked = false;
                        Alert alerte2 = new Alert(Alert.AlertType.INFORMATION);
                        alerte2.setTitle("Erreur");
                        alerte2.setHeaderText("Catactère non pris en charge");
                        alerte2.setContentText("Veuillez entrer des nombres");
                        alerte2.showAndWait();
                    }
                }
                else{
                    tempo.setValue(0, j, i);}

                value++;
            }
        }
        if (worked) {
            setChoiceBox();
            map.put(resultat, tempo);
            listeNoms.add(resultat);
        }

    }

    private void setChoiceBox() {
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

    public Matrice[] getMatrices() {
        if (choixGauche.getValue().isEmpty() && choixDroite.getValue().isEmpty()) {
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("Erreur");
            alerte.setHeaderText("Aucune matrice entrée");
            alerte.setContentText("Veuillez entrer des matrices valides");
            alerte.showAndWait();
            return null;
        } else if (choixGauche.getValue().isEmpty())
            return new Matrice[]{map.get(choixDroite.getValue())};
        else if (choixDroite.getValue().isEmpty())
            return new Matrice[]{map.get(choixGauche.getValue())};
        else
            return new Matrice[]{map.get(choixGauche.getValue()), map.get(choixDroite.getValue())};
    }

    public void addition() {
        Matrice mats[] = getMatrices();
        try{
        if (mats.length == 2) {
            if (mats[0].getRows() == mats[1].getRows() && mats[0].getColumns() == mats[0].getColumns()) {
                float tempoA;
                float tempoB;
                float addition;
                Matrice resultat = new Matrice("", mats[0].getColumns(), mats[0].getRows());
                for (int i = 0; i < mats[0].getRows(); i++) {
                    for (int j = 0; j < mats[0].getColumns(); j++) {
                        tempoA = mats[0].getValue(j, i);
                        tempoB = mats[1].getValue(j, i);
                        addition = tempoA + tempoB;
                        resultat.setValue(addition, j, i);
                    }
                }
                String resultatTexte = mats[0].getNom() + " + " + mats[1].getNom() + " = \n" + resultat.toString();
                textArea.setText(resultatTexte);
            } else {
                textArea.setText("Les formats des matrices ne sont pas identiques");
            }
        } else {
            textArea.setText("Veuillez entrer deux matrices");
        }}
        catch (NullPointerException o){}
    }

    public void soustraction() {
        Matrice mats[] = getMatrices();
        try{
        if (mats.length == 2) {
            if (mats[0].getRows() == mats[1].getRows() && mats[0].getColumns() == mats[0].getColumns()) {
                float tempoA;
                float tempoB;
                float soustraction;
                Matrice resultat = new Matrice("", mats[0].getColumns(), mats[0].getRows());
                for (int i = 0; i < mats[0].getRows(); i++) {
                    for (int j = 0; j < mats[0].getColumns(); j++) {
                        tempoA = mats[0].getValue(j, i);
                        tempoB = mats[1].getValue(j, i);
                        soustraction = tempoA - tempoB;
                        resultat.setValue(soustraction, j, i);
                    }
                }
                String resultatTexte = mats[0].getNom() + " - " + mats[1].getNom() + " = \n" + resultat.toString();
                textArea.setText(resultatTexte);
            } else {
                textArea.setText("Les formats des matrices ne sont pas identiques");
            }
        } else {
            textArea.setText("Veuillez entrer deux matrices");
        }
        }
        catch (NullPointerException o){}
    }

    public void multScalaire() {
        Matrice mats[] = getMatrices();
        try{
        if (mats.length == 1) {
            TextInputDialog alerte = new TextInputDialog(" Entrez le scalaire");
            alerte.setTitle("Multiplication par un scalaire");
            alerte.setHeaderText("Veuillez entrer le scalaire");
            alerte.setContentText("Scalaire : ");
            String scalaire = alerte.showAndWait().get();
            float multiplicateur=0;
            try {multiplicateur = Float.parseFloat(scalaire);}
            catch (NumberFormatException o ){
                Alert alerte9 = new Alert(Alert.AlertType.INFORMATION);
                alerte9.setTitle("Erreur");
                alerte9.setHeaderText("Nombre requis");
                alerte9.setContentText("Veuillez entrer un nombre valide");
                alerte9.showAndWait();
            }


            Matrice resultat = new Matrice("", mats[0].getColumns(), mats[0].getRows());
            for (int i = 0; i < mats[0].getRows(); i++) {
                for (int j = 0; j < mats[0].getColumns(); j++) {
                    float element = mats[0].getValue(j, i);
                    resultat.setValue(multiplicateur * element, j, i);
                }
            }
            textArea.setText(resultat.toString());

        } else {
            textArea.setText("Vous devez entrer une seule matrice à multiplier");
        }}
        catch (NullPointerException o){}
    }

    public void puissance() {

        try{
        Matrice mats[] = getMatrices();
        if (mats.length == 1) {
            Matrice matrices[] = {mats[0], mats[0]};

            if (matrices[1] != null) {
                TextInputDialog alerte = new TextInputDialog(" Entrez l'exposant");
                alerte.setTitle("Multiplication par un exposant");
                alerte.setHeaderText("Veuillez entrer un exposant");
                alerte.setContentText("Exposant : ");
                String exposant = alerte.showAndWait().get();

                int exp = 0;
                try{
                exp = Integer.parseInt(exposant);}
                catch (NumberFormatException io){
                    Alert alerte3 = new Alert(Alert.AlertType.INFORMATION);
                    alerte3.setTitle("Erreur");
                    alerte3.setHeaderText("Nombre invalide");
                    alerte3.setContentText("Veuillez entrer un nombre entier");
                    alerte3.showAndWait();
                }
                for(int i =0;i<exp-1;i++){
                    matrices[1]=produitMatSuperieur(matrices);
                }
                textArea.setText(matrices[1].toString());
            }
        } else
            textArea.setText("Veuillez entrer une seule matrice");
        }
        catch (NullPointerException o ){}
    }

    public void transposee() {
        Matrice mats[] = getMatrices();
    try{
        if (mats.length == 1) {
            Matrice resultat = new Matrice("", mats[0].getRows(), mats[0].getColumns());
            for (int i = 0; i < mats[0].getRows(); i++) {
                for (int j = 0; j < mats[0].getColumns(); j++) {
                    float transpo = mats[0].getValue(j, i);
                    resultat.setValue(transpo, i, j);
                }
            }
            textArea.setText(resultat.toString());
        }
        if (mats.length == 2) {
            textArea.setText("Veuillez entrer une seule matrice");
        }
        if (mats.length == 0)
            textArea.setText("Veuillez entrer une matrice à transposer");
    }
    catch (NullPointerException o){}
    }

    public void inversion() {
        Matrice mats[] = getMatrices();
        try{
        if (mats.length == 1) {
            if (mats[0].getColumns() == mats[0].getRows()) {
                Matrice matrice = mats[0];
                Matrice inverse = new Matrice("", matrice.getColumns(), matrice.getRows());

                float scalaire = (1 / determinantSuperieur(matrice));

                if (determinantSuperieur(matrice) == 0) {
                    textArea.setText("Le déterminant de la matrice est nul, la matrice inverse est donc nulle");
                } else {
                    for (int i = 0; i < matrice.getRows(); i++) {
                        for (int j = 0; j < matrice.getColumns(); j++) {
                            float valeur = (float) (Math.pow(-1, (i + 1) + (j + 1))) * scalaire * determinantSuperieur(mineur(matrice, j, i));
                            inverse.setValue(valeur, i, j);
                        }
                    }
                    textArea.setText(matrice.getNom() + "^-1 = \n" + inverse.toString());
                }
            } else
                textArea.setText("La matrice doit être carrée");
        } else
            textArea.setText("Vous devez sélectionner une seule matrice");
    }catch (NullPointerException o){}}

    public void produitMat() {
        Matrice mats[] = getMatrices();
        try{
        if (mats.length == 2) {
            produitMatSuperieur(mats);
        } else
            textArea.setText("Veuillez entrer deux matrices");}
            catch (NullPointerException o){}
    }

    private Matrice produitMatSuperieur(Matrice mats[]) {
        int valeur = 0;

        if (mats[0].getColumns() == mats[1].getRows()) {
            Matrice resultat = new Matrice("", mats[1].getColumns(), mats[0].getRows());

            for (int i = 0; i < resultat.getRows(); i++) {
                for (int j = 0; j < resultat.getColumns(); j++) {
                    float calculee = 0;
                    for (int k = 0; k < mats[0].getColumns(); k++) {
                        calculee += (mats[0].getValue(k, valeur) * mats[1].getValue(valeur, k));///???
                        resultat.setValue(calculee, j, i);
                    }
                    valeur++;

                }
                valeur = 0;
            }
            textArea.setText(resultat.toString());
            return resultat;
        } else textArea.setText("Matrices incompatibles");
        return null;
    }

    public void produitVect() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 2) {
                if (mats[0].getRows() == mats[1].getRows() && mats[0].getColumns() == mats[1].getColumns()) {
                    if (mats[0].getRows() == 1 && mats[0].getColumns() == 3) {
                        Matrice resultat = new Matrice("", 3, 1);
                        resultat.setValue((mats[0].getValue(1, 0) * mats[1].getValue(2, 0)) - (mats[0].getValue(2, 0) * mats[1].getValue(1, 0)), 0, 0);
                        resultat.setValue(-((mats[0].getValue(0, 0) * mats[1].getValue(2, 0)) - (mats[0].getValue(2, 0) * mats[1].getValue(0, 0))), 1, 0);
                        resultat.setValue((mats[0].getValue(0, 0) * mats[1].getValue(1, 0)) - (mats[0].getValue(1, 0) * mats[1].getValue(0, 0)), 2, 0);
                        textArea.setText(mats[0].getNom() + " x " + mats[1].getNom() + " = " + resultat.toString());
                    } else
                        textArea.setText("Des vecteurs doivent être entrés");
                } else
                    textArea.setText("Les matrices doivent être de même format");
            } else
                textArea.setText("Deux vecteurs de format 1x3 doivent être entrés");
        }catch (NullPointerException o){}}

    public void produitHad() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 2) {
                if (mats[0].getColumns() == mats[1].getColumns() && mats[0].getRows() == mats[1].getRows()) {
                    Matrice resultat = new Matrice("", mats[0].getColumns(), mats[0].getRows());

                    for (int i = 0; i < mats[0].getRows(); i++) {
                        for (int j = 0; j < mats[0].getColumns(); j++) {
                            resultat.setValue(mats[0].getValue(j, i) * mats[1].getValue(j, i), j, i);
                            textArea.setText(resultat.toString());
                        }
                    }
                } else
                    textArea.setText("Matrices incompatibles");
            } else
                textArea.setText("Deux matrices sont nécessaires");
        }catch (NullPointerException o ){}}

    public void produitTens() {

    }

    public void determinant() {
        Matrice mat[] = getMatrices();
        try {
            if (mat.length == 1) {
                determinantSuperieur(mat[0]);
            } else
                textArea.setText("Vous devez entrer une seule matrice pour calculer son déterminant");
        }catch (NullPointerException o){}}

    private float determinantSuperieur(Matrice matrice) {
        float resultat = 0;

        if (matrice.getColumns() == 1) {
            resultat = matrice.getValue(0, 0);
            textArea.setText("Det(" + matrice.getNom() + ") = " + resultat);
        } else if (matrice.getColumns() == 2) {
            resultat = (matrice.getValue(0, 0) * matrice.getValue(1, 1)) - (matrice.getValue(1, 0) * matrice.getValue(0, 1));
            textArea.setText("Det(" + matrice.getNom() + ") = " + resultat);
        } else if (matrice.getColumns() > 2) {

            float valeurs[] = new float[matrice.getColumns()];
            float determinantDparD[] = new float[matrice.getColumns()];
            Matrice mineurs[] = new Matrice[matrice.getColumns()];

            for (int i = 0; i < matrice.getColumns(); i++) {
                valeurs[i] = matrice.getValue(i, 0);
                mineurs[i] = mineur(matrice, i, 0);
            }

            if (mineurs[0].getColumns() > 2) {
                for (int i = 0; i < mineurs.length; i++)
                    determinantDparD[i] = determinantSuperieur(mineurs[i]);

            } else if (mineurs[0].getColumns() == 2) {
                for (int i = 0; i < mineurs.length; i++)
                    determinantDparD[i] = (mineurs[i].getValue(0, 0) * mineurs[i].getValue(1, 1)) - (mineurs[i].getValue(1, 0) * mineurs[i].getValue(0, 1));
            }

            for (int i = 0; i < valeurs.length; i++)
                resultat += valeurs[i] * Math.pow(-1, i + 2) * determinantDparD[i];

            textArea.setText("Det(" + matrice.getNom() + ") = " + resultat);
        }
        return resultat;
    }

    private Matrice mineur(Matrice matrice, int columnToRemove, int rowToRemove) {
        Matrice resultat = new Matrice("", matrice.getColumns() - 1, matrice.getRows() - 1);

        for (int a = 0; a < matrice.getRows(); a++) {
            if (a != rowToRemove) {
                for (int b = 0; b < matrice.getColumns(); b++) {
                    if (b != columnToRemove) {
                        if (b < columnToRemove) {
                            if (a < rowToRemove)
                                resultat.setValue(matrice.getValue(b, a), b, a);
                            else if (a > rowToRemove)
                                resultat.setValue(matrice.getValue(b, a), b, a - 1);
                        } else if (b > columnToRemove) {
                            if (a < rowToRemove)
                                resultat.setValue(matrice.getValue(b, a), b - 1, a);
                            else if (a > rowToRemove)
                                resultat.setValue(matrice.getValue(b, a), b - 1, a - 1);
                        }
                    }
                }
            }
        }
        return resultat;
    }
}