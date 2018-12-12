package sample.tests;

import javafx.scene.control.TextArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.controllers.Controller;
import sample.data.Calculateur;
import sample.data.Matrice;

import static org.junit.jupiter.api.Assertions.assertEquals;

//On ne teste pas les matrices qui ne correspondent pas aux calculs puisqu'on prend en compte que les méthodes pour set les fonctions
//le font déjà, évitant ainsi les erreurs.

class CalculateurTest {
    Calculateur calc;
    Matrice testGeneral33 = new Matrice("", 3, 3);

    @BeforeEach
    void setUp() {
        calc = new Calculateur();
        int a = 0;

        for (int i = 0; i < testGeneral33.getRows(); i++)
            for (int j = 0; j < testGeneral33.getColumns(); j++)
                testGeneral33.setValue(a++, j, i);
    }

    @Test
    void additionSoustraction() {
        Matrice expected = new Matrice("", 3, 3);
        int a = 0;

        for (int i = 0; i < testGeneral33.getRows(); i++)
            for (int j = 0; j < testGeneral33.getColumns(); j++) {
                testGeneral33.setValue(a + a, j, i);
                a++;
            }

        assertEquals(true, calc.isEqual(expected, calc.additionSoustraction(true, testGeneral33, testGeneral33)));
    }

    @Test
    void multScalaire() {
    }

    @Test
    void puissance() {
    }

    @Test
    void transposee() {
    }

    @Test
    void inversion() {
    }

    @Test
    void produitMat() {
    }

    @Test
    void produitVect() {
    }

    @Test
    void produitHad() {
    }

    @Test
    void produitTens() {
    }

    @Test
    void determinant() {
    }

}