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
        if (mats.length == 2) {
            if (mats[0].getRows() == mats[1].getRows() && mats[0].getColumns() == mats[0].getColumns()) {
                try {
                    Matrice resultat = additionSoustraction(true, mats[0], mats[1]);
                    textArea.setText(mats[0].getNom() + " + " + mats[1].getNom() + " = \n" + resultat.toString());
                }
                catch (NullPointerException e) {e.printStackTrace();}
            } else
                textArea.setText("Les matrices sont incompatibles");
        } else
            textArea.setText("Veuillez entrer deux matrices");
    }

    public void setSoustraction() {
        Matrice mats[] = getMatrices();
        if (mats.length == 2) {
            if (mats[0].getRows() == mats[1].getRows() && mats[0].getColumns() == mats[0].getColumns()) {
                try {
                    Matrice resultat = additionSoustraction(false, mats[0], mats[1]);
                    textArea.setText(mats[0].getNom() + " - " + mats[1].getNom() + " = \n" + resultat.toString());
                }
                catch (NullPointerException e) {e.printStackTrace();}
            } else
                textArea.setText("Les matrices sont incompatibles");
        } else
            textArea.setText("Veuillez entrer deux matrices");
    }

    public void setMultScalaire() {
        Matrice mats[] = getMatrices();
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

            try {
                Matrice resultat = multScalaire(mats[0], multiplicateur);
                textArea.setText(multiplicateur + " * " + mats[0].getNom() + " = \n" + resultat.toString());
            }
            catch (NullPointerException e) {e.printStackTrace();}
        } else
            textArea.setText("Veuillez entrer une seule matrice");
    }

    public void setPuissance() {
        Matrice mats[] = getMatrices();
        if (mats.length == 1) {
            int exp = 0;

            TextInputDialog alerte = new TextInputDialog(" Entrez l'exposant");
            alerte.setTitle("Multiplication par un exposant");
            alerte.setHeaderText("Veuillez entrer un exposant");
            alerte.setContentText("Exposant : ");
            String exposant = alerte.showAndWait().get();

            try { exp = Integer.parseInt(exposant); }
            catch (NumberFormatException io) {
            Alert alerte1 = new Alert(Alert.AlertType.INFORMATION);
            alerte1.setTitle("Erreur");
            alerte1.setHeaderText("Nombre invalide");
            alerte1.setContentText("Veuillez entrer un nombre entier");
            alerte1.showAndWait();
            }

            try {
                Matrice resultat = puissance(mats[0], exp);
                textArea.setText(mats[0].getNom() + "^" + exp + " = \n" + resultat.toString());
            }
            catch (NullPointerException e) {e.printStackTrace();}
        } else
            textArea.setText("Veuillez entrer une seule matrice");
    }

    public void setTransposee() {
        Matrice mats[] = getMatrices();
        if (mats.length == 1) {
            try {
                Matrice resultat = transposee(mats[0]);
                textArea.setText(mats[0].getNom() + "^t = \n" + resultat.toString());
            } catch (NullPointerException e) {e.printStackTrace();}
        } else
            textArea.setText("Veuillez entrer une seule matrice");
    }

    public void setInversion() {
        Matrice mats[] = getMatrices();
        if (mats.length == 1) {
            if (mats[0].getRows() == mats[0].getColumns()) {
                try {
                    Matrice resultat = inversion(mats[0]);
                    if (resultat != null)
                        textArea.setText(mats[0].getNom() + "^-1 = \n" + resultat.toString());
                } catch (NullPointerException e) { e.printStackTrace();}
            } else
                textArea.setText("La matrice doit être carrée");
        } else
            textArea.setText("Veuillez entrer une seule matrice");
    }

    public void setProduitMat() {
        Matrice mats[] = getMatrices();
        if (mats.length == 2) {
            if (mats[0].getColumns() == mats[1].getRows()) {
                try {
                    Matrice resultat = produitMat(mats[0], mats[1]);
                    textArea.setText(mats[0].getNom() + " x " + mats[1].getNom() + " = \n" + resultat.toString());
                } catch (NullPointerException e) {e.printStackTrace();}
            } else
                textArea.setText("Matrices incompatibles");
        } else
            textArea.setText("Veuillez entrer deux matrices");
    }

    public void setProduitVect() {
        Matrice mats[] = getMatrices();
        if (mats.length == 2) {
            if (mats[0].getRows() == mats[1].getRows() && mats[0].getColumns() == mats[1].getColumns()) {
                if (mats[0].getRows() == 1 && mats[0].getColumns() == 3) {
                    try {
                        Matrice resultat = produitVect(mats[0], mats[1]);
                        textArea.setText("Le produit vectoriel de " + mats[0].getNom() + " x " + mats[1].getNom() + " = \n" + resultat.toString());
                    } catch (NullPointerException e) {e.printStackTrace();}
                } else
                    textArea.setText("Des vecteurs doivent être entrés");
            } else
                textArea.setText("Les matrices doivent être de même format");
        } else
            textArea.setText("Deux vecteurs de format 1x3 doivent être entrés");
    }

    public void setProduitHad() {
        Matrice mats[] = getMatrices();
        if (mats.length == 2) {
            if (mats[0].getColumns() == mats[1].getColumns() && mats[0].getRows() == mats[1].getRows()) {
                try {
                    Matrice resultat = produitHad(mats[0], mats[1]);
                    textArea.setText("Le produit d'Hadamard de " + mats[0].getNom() + " x " + mats[1].getNom() + " = \n" + resultat.toString());
                } catch (NullPointerException e) {e.printStackTrace();}
            } else
                textArea.setText("Matrices incompatibles");
        } else
            textArea.setText("Veuillez entrer deux matrices");
    }

    public void setProduitTens() {
        Matrice mats[] = getMatrices();
        if (mats.length == 2) {
            try {
                Matrice resultat = produitTens(mats[0], mats[1]);
                textArea.setText("Le produit tensoriel de " + mats[0].getNom() + " x " + mats[1].getNom() + " = \n" + resultat.toString());
            } catch (NullPointerException e) {e.printStackTrace();}
        } else
            textArea.setText("Veuillez entrer deux matrices");
    }

    public void setDeterminant() {
        Matrice mats[] = getMatrices();
        if (mats.length == 1) {
            if (mats[0].getRows() == mats[0].getColumns()) {
                try {
                    float resultat = determinant(mats[0]);
                    textArea.setText("Det(" + mats[0].getNom() + ") = " + resultat);
                } catch (NullPointerException e) {e.printStackTrace();}
            } else
                textArea.setText("La matrice doit être carrée");
        } else
            textArea.setText("Veuillez entrer une seule matrice");
    }

    public Matrice additionSoustraction(boolean addition, Matrice matrice1, Matrice matrice2) {
        Matrice resultat = new Matrice("", matrice1.getColumns(), matrice1.getRows());
        for (int i = 0; i < matrice1.getRows(); i++) {
            for (int j = 0; j < matrice1.getColumns(); j++) {
                if (addition)
                    resultat.setValue(matrice1.getValue(j, i) + matrice2.getValue(j, i), j, i);
                else
                    resultat.setValue(matrice1.getValue(j, i) - matrice2.getValue(j, i), j, i);
            }
        }
        return resultat;
    }

    public Matrice multScalaire(Matrice matrice, float multiplicateur) {
        Matrice resultat = new Matrice("", matrice.getColumns(), matrice.getRows());
        for (int i = 0; i < matrice.getRows(); i++)
            for (int j = 0; j < matrice.getColumns(); j++)
                resultat.setValue(multiplicateur * matrice.getValue(j, i), j, i);
        return resultat;
    }

    public Matrice puissance(Matrice matrice, int exp) {
        for (int i = 0; i < exp - 1; i++)
            matrice = produitMat(matrice, matrice);
        return matrice;
    }

    public Matrice transposee(Matrice matrice) {
        Matrice resultat = new Matrice("", matrice.getRows(), matrice.getColumns());
        for (int i = 0; i < matrice.getRows(); i++)
            for (int j = 0; j < matrice.getColumns(); j++)
                resultat.setValue(matrice.getValue(j, i), i, j);
        return resultat;
    }

    public Matrice inversion(Matrice matrice) {
        Matrice inverse = new Matrice("", matrice.getColumns(), matrice.getRows());
        float scalaire = determinant(matrice);

        if (scalaire == 0) {
            textArea.setText("Le déterminant de la matrice est nul, la matrice inverse est donc nulle");
            return null;
        } else {
            for (int i = 0; i < matrice.getRows(); i++)
                for (int j = 0; j < matrice.getColumns(); j++)
                    inverse.setValue((float) (Math.pow(-1, (i + 1) + (j + 1))) * (1 / scalaire) * determinant(mineur(matrice, j, i)), i, j);
            return inverse;
        }
    }

    public Matrice produitMat(Matrice matrice1, Matrice matrice2) {  //Ne fonctionne pas
        int valeur = 0, calculee = 0, valeur2 = 0;
        Matrice resultat = new Matrice("", matrice2.getColumns(), matrice1.getRows());

        for (int i = 0; i < resultat.getRows(); i++) {
            for (int j = 0; j < resultat.getColumns(); j++) {
                calculee = 0;
                for (int k = 0; k < matrice1.getColumns(); k++) {
                    calculee += (matrice1.getValue(k, valeur2) * matrice2.getValue(valeur, k));
                    resultat.setValue(calculee, j, i);
                }
                valeur++;
            }
            valeur = 0;
            valeur2++;
        }
        return resultat;
    }

    public Matrice produitVect(Matrice matrice1, Matrice matrice2) {
        Matrice resultat = new Matrice("", 3, 1);
        resultat.setValue((matrice1.getValue(1, 0) * matrice2.getValue(2, 0)) - (matrice1.getValue(2, 0) * matrice2.getValue(1, 0)), 0, 0);
        resultat.setValue(-((matrice1.getValue(0, 0) * matrice2.getValue(2, 0)) - (matrice1.getValue(2, 0) * matrice2.getValue(0, 0))), 1, 0);
        resultat.setValue((matrice1.getValue(0, 0) * matrice2.getValue(1, 0)) - (matrice1.getValue(1, 0) * matrice2.getValue(0, 0)), 2, 0);
        return resultat;
    }

    public Matrice produitHad(Matrice matrice1, Matrice matrice2) {
        Matrice resultat = new Matrice("", matrice1.getColumns(), matrice1.getRows());
        for (int i = 0; i < matrice1.getRows(); i++)
            for (int j = 0; j < matrice1.getColumns(); j++)
                resultat.setValue(matrice1.getValue(j, i) * matrice2.getValue(j, i), j, i);
        return resultat;
    }

    public Matrice produitTens(Matrice matrice1, Matrice matrice2) {
        Matrice resultat = new Matrice("", matrice1.getColumns() * matrice2.getColumns(), matrice1.getRows() * matrice2.getRows());
        int row = 0, column = 0;
        for (int i = 0; i < matrice1.getRows(); i++) {
            for (int j = 0; j < matrice1.getColumns(); j++) {
                row = matrice2.getRows() * i;
                for (int a = 0; a < matrice2.getRows(); a++) {
                    column = matrice2.getColumns() * j;
                    for (int b = 0; b < matrice2.getColumns(); b++) {
                        resultat.setValue(matrice1.getValue(j, i) * matrice2.getValue(b, a), column, row);
                        column++;
                    }
                    row++;
                }
            }
        }
        return resultat;
    }

    public float determinant(Matrice matrice) {
        float resultat = 0;

        if (matrice.getColumns() == 1)
            return matrice.getValue(0, 0);
        else if (matrice.getColumns() == 2)
            return  (matrice.getValue(0, 0) * matrice.getValue(1, 1)) - (matrice.getValue(1, 0) * matrice.getValue(0, 1));
        else if (matrice.getColumns() > 2) {
            float valeurs[] = new float[matrice.getColumns()];
            float determinantDparD[] = new float[matrice.getColumns()];
            Matrice mineurs[] = new Matrice[matrice.getColumns()];

            for (int i = 0; i < matrice.getColumns(); i++) {
                valeurs[i] = matrice.getValue(i, 0);
                mineurs[i] = mineur(matrice, i, 0);
            }

            if (mineurs[0].getColumns() > 2)
                for (int i = 0; i < mineurs.length; i++)
                    determinantDparD[i] = determinant(mineurs[i]);
            else if (mineurs[0].getColumns() == 2)
                for (int i = 0; i < mineurs.length; i++)
                    determinantDparD[i] = (mineurs[i].getValue(0, 0) * mineurs[i].getValue(1, 1)) - (mineurs[i].getValue(1, 0) * mineurs[i].getValue(0, 1));

            for (int i = 0; i < valeurs.length; i++)
                resultat += valeurs[i] * Math.pow(-1, i + 2) * determinantDparD[i];
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