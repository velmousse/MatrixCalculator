package sample.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Database {
    private String[] tableau = new String[10];
    private int value = 0;

    private List<String> keys;
    private HashMap<String, Matrice> mapListe;

    public Database() {
        mapListe = new HashMap<>();
    }

    public ObservableList<String> getObservableList() {
        return FXCollections.observableList((List) Arrays.asList(tableau));
    }

    public void addMatrice(Matrice matrice) {
        if (value < 10) {
            tableau[value] = matrice.getNom();
            mapListe.put(matrice.getNom(), matrice);
            value++;
        }
    }
}
