//Corriger l'erreur qui survient lors de l'annulation de l'entrée dans une AlertBox
//Améliorer les affichages de résultats
//Ajouter les démarches
//Tests unitaires
//CSV et print

package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import sample.data.Calculateur;
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
    private Calculateur calc;

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
    Tooltip tooltip = new Tooltip("Additionnez 2 matrices de même format ensemble");
    @FXML
    private Button soustraction;
    Tooltip tooltip2 = new Tooltip("Soustrayez la matrice de droite à celle de gauche (même format)");
    @FXML
    private Button scalaire;
    Tooltip tooltip3 = new Tooltip("Multipliez une matrice par un scalaire");
    @FXML
    private Button inversion;
    Tooltip tooltip4 = new Tooltip("Créez la matrice inverse d'une matrice carrée");
    @FXML
    private Button puissance;
    Tooltip tooltip5 = new Tooltip("Multipliez votre matrice de façon exponentielle!");
    @FXML
    private Button tensoriel;
    Tooltip tooltip6 = new Tooltip("Créez le produit tensoriel d'une matrice!");
    @FXML
    private Button determinant;
    Tooltip tooltip7 = new Tooltip("Trouvez le déterminant d'une matrice carrée");
    @FXML
    private Button vectoriel;
    Tooltip tooltip8 = new Tooltip("Créez le produit vectoriel de deux vecteurs 1*3");
    @FXML
    private Button matriciel;
    Tooltip tooltip9 = new Tooltip("Multipliez deux matrices ensemble");
    @FXML
    private Button hadamard;
    Tooltip tooltip10 = new Tooltip("Effectuez le produit d'Hadamard sur une matrice");
    @FXML
    private Button transposition;
    Tooltip tooltip11 = new Tooltip("Effectuez la transposée de votre matrice");

    public void initialize() {
        listeNoms.add("");
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        addition.setTooltip(tooltip);
        soustraction.setTooltip(tooltip2);
        scalaire.setTooltip(tooltip3);
        inversion.setTooltip(tooltip4);
        puissance.setTooltip(tooltip5);
        tensoriel.setTooltip(tooltip6);
        determinant.setTooltip(tooltip7);
        vectoriel.setTooltip(tooltip8);
        matriciel.setTooltip(tooltip9);
        hadamard.setTooltip(tooltip10);
        transposition.setTooltip(tooltip11);

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

        gridPane.setMaxSize((int) spinnerColonnes.getValue() * tfWidth, (int) spinnerLignes.getValue() * tfHeight);

        for (int y = 0; y < (int) spinnerLignes.getValue(); y++) {
            for (int x = 0; x < (int) spinnerColonnes.getValue(); x++) {
                TextField temp = new TextField();
                temp.setPromptText("0");
                Tooltip tooltip = new Tooltip("Vous devez au moins entrer une valeur");

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
                calc = new Calculateur();
            }
        }
    }

    public void ajouterMatrice() {
        String resultat = "", texte = "Veuillez entrer le nom de la matrice";
        boolean notNew = true, worked = false;

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
                } else {
                    tempo.setValue(0, j, i);
                }
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

    public void importerMatrice() {

    }

    public void afficherMatrices() {
        Matrice mats[] = getMatrices();

        if (mats.length == 1 || mats[0] == mats[1])
            textArea.setText(mats[0].getNom() + " = \n" + mats[0].toString());
        else if (mats.length == 2)
            textArea.setText(mats[0].getNom() + " = \n" + mats[0].toString() + "\n\n" + mats[1].getNom() + " = \n" + mats[1].toString());
        else
            textArea.setText("Veuillez entrer au moins une matrice");
    }

    public void setAddition() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 2) {
                if (mats[0].getRows() == mats[1].getRows() && mats[0].getColumns() == mats[0].getColumns()) {
                    Matrice resultat = calc.additionSoustraction(true, mats[0], mats[1]);
                    textArea.setText(mats[0].getNom() + " + " + mats[1].getNom() + " = \n" + resultat.toString());
                } else
                    textArea.setText("Les matrices sont incompatibles");
            } else
                textArea.setText("Veuillez entrer deux matrices");
        } catch (NullPointerException e) {}
    }

    public void setSoustraction() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 2) {
                if (mats[0].getRows() == mats[1].getRows() && mats[0].getColumns() == mats[0].getColumns()) {
                    Matrice resultat = calc.additionSoustraction(false, mats[0], mats[1]);
                    textArea.setText(mats[0].getNom() + " - " + mats[1].getNom() + " = \n" + resultat.toString());
                } else
                    textArea.setText("Les matrices sont incompatibles");
            } else
                textArea.setText("Veuillez entrer deux matrices");
        } catch (NullPointerException e) {}
    }

    public void setMultScalaire() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 1) {
                float multiplicateur = 0;
                TextInputDialog alerte = new TextInputDialog(" Entrez le scalaire");
                alerte.setTitle("Multiplication par un scalaire");
                alerte.setHeaderText("Veuillez entrer le scalaire");
                alerte.setContentText("Scalaire : ");
                String scalaire = alerte.showAndWait().get();

                try {
                    multiplicateur = Float.parseFloat(scalaire);
                } catch (NumberFormatException o) {
                    Alert alerte1 = new Alert(Alert.AlertType.INFORMATION);
                    alerte1.setTitle("Erreur");
                    alerte1.setHeaderText("Nombre requis");
                    alerte1.setContentText("Veuillez entrer un nombre valide");
                    alerte1.showAndWait();
                }
                Matrice resultat = calc.multScalaire(mats[0], multiplicateur);
                textArea.setText(multiplicateur + " * " + mats[0].getNom() + " = \n" + resultat.toString());
            } else
                textArea.setText("Veuillez entrer une seule matrice");
        } catch (NullPointerException e) {}
    }

    public void setPuissance() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 1) {
                int exp = 0;

                TextInputDialog alerte = new TextInputDialog(" Entrez l'exposant");
                alerte.setTitle("Multiplication par un exposant");
                alerte.setHeaderText("Veuillez entrer un exposant");
                alerte.setContentText("Exposant : ");
                String exposant = alerte.showAndWait().get();

                try {
                    exp = Integer.parseInt(exposant);
                } catch (NumberFormatException io) {
                    Alert alerte1 = new Alert(Alert.AlertType.INFORMATION);
                    alerte1.setTitle("Erreur");
                    alerte1.setHeaderText("Nombre invalide");
                    alerte1.setContentText("Veuillez entrer un nombre entier");
                    alerte1.showAndWait();
                }

                Matrice resultat = calc.puissance(mats[0], exp);
                textArea.setText(mats[0].getNom() + "^" + exp + " = \n" + resultat.toString());
            } else
                textArea.setText("Veuillez entrer une seule matrice");
        } catch (NullPointerException e) {}
    }

    public void setTransposee() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 1) {
                Matrice resultat = calc.transposee(mats[0]);
                textArea.setText(mats[0].getNom() + "^t = \n" + resultat.toString());
            } else
                textArea.setText("Veuillez entrer une seule matrice");
        } catch (NullPointerException e) {}
    }

    public void setInversion() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 1) {
                if (mats[0].getRows() == mats[0].getColumns()) {
                    Matrice resultat = calc.inversion(mats[0]);
                    if (calc.determinant(mats[0]) != 0)
                        textArea.setText(mats[0].getNom() + "^-1 = \n" + resultat.toString());
                    else
                        textArea.setText("Le déterminant de la matrice est nul, il n'y a donc pas de matrice inverse");
                } else
                    textArea.setText("La matrice doit être carrée");
            } else
                textArea.setText("Veuillez entrer une seule matrice");
        } catch (NullPointerException e) {}
    }

    public void setProduitMat() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 2) {
                if (mats[0].getColumns() == mats[1].getRows()) {
                    Matrice resultat = calc.produitMat(mats[0], mats[1]);
                    textArea.setText(mats[0].getNom() + " x " + mats[1].getNom() + " = \n" + resultat.toString());
                } else
                    textArea.setText("Matrices incompatibles");
            } else
                textArea.setText("Veuillez entrer deux matrices");
        } catch (NullPointerException e) {}
    }

    public void setProduitVect() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 2) {
                if (mats[0].getRows() == mats[1].getRows() && mats[0].getColumns() == mats[1].getColumns()) {
                    if (mats[0].getRows() == 1 && mats[0].getColumns() == 3) {
                        Matrice resultat = calc.produitVect(mats[0], mats[1]);
                        textArea.setText("Le produit vectoriel de " + mats[0].getNom() + " x " + mats[1].getNom() + " = \n" + resultat.toString());
                    } else
                        textArea.setText("Des vecteurs doivent être entrés");
                } else
                    textArea.setText("Les matrices doivent être de même format");
            } else
                textArea.setText("Deux vecteurs de format 1x3 doivent être entrés");
        } catch (NullPointerException e) {}
    }

    public void setProduitHad() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 2) {
                if (mats[0].getColumns() == mats[1].getColumns() && mats[0].getRows() == mats[1].getRows()) {
                    Matrice resultat = calc.produitHad(mats[0], mats[1]);
                    textArea.setText("Le produit d'Hadamard de " + mats[0].getNom() + " x " + mats[1].getNom() + " = \n" + resultat.toString());
                } else
                    textArea.setText("Matrices incompatibles");
            } else
                textArea.setText("Veuillez entrer deux matrices");
        } catch (NullPointerException e) {}
    }

    public void setProduitTens() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 2) {
                Matrice resultat = calc.produitTens(mats[0], mats[1]);
                textArea.setText("Le produit tensoriel de " + mats[0].getNom() + " x " + mats[1].getNom() + " = \n" + resultat.toString());
            } else
                textArea.setText("Veuillez entrer deux matrices");
        } catch (NullPointerException e) {}
    }

    public void setDeterminant() {
        Matrice mats[] = getMatrices();
        try {
            if (mats.length == 1) {
                if (mats[0].getRows() == mats[0].getColumns()) {
                    float resultat = calc.determinant(mats[0]);
                    textArea.setText("Det(" + mats[0].getNom() + ") = " + resultat);
                } else
                    textArea.setText("La matrice doit être carrée");
            } else
                textArea.setText("Veuillez entrer une seule matrice");
        } catch (NullPointerException e) {}
    }
}