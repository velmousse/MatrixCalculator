package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import sample.data.*;

public class Controller {
    private Database db = new Database();

    @FXML
    private ChoiceBox choiceBox;

    public void afficherListe() {
        db.addMatrice(new Matrice("A", 0, 0));
        db.addMatrice(new Matrice("B", 0, 0));

        choiceBox = new ChoiceBox<>(db.getObservableList());

        choiceBox.setItems(db.getObservableList());
        choiceBox.setValue(db.getObservableList().get(0));
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Valeur modifiée: " + newValue);
        });
    }

    public void ajoutMatrice() {

    }
}
