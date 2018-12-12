package sample.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.controllers.Controller;
import sample.data.Matrice;

class ControllerTest {
    Controller test;
    Matrice testGeneral33 = new Matrice("", 3, 3);

    @BeforeEach
    void setUp() {
        test = new Controller();
        int a = 0;

        for (int i = 0; i < testGeneral33.getRows(); i++)
            for (int j = 0; j < testGeneral33.getColumns(); j++)
                testGeneral33.setValue(a++, j, i);
    }

    @Test
    void additionSoustraction() {

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