package sample.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.data.Calculateur;
import sample.data.Matrice;

import static org.junit.jupiter.api.Assertions.assertEquals;

//On ne teste pas les matrices qui ne correspondent pas aux calculs puisqu'on prend en compte que les méthodes pour set les fonctions
//le font déjà, évitant ainsi les erreurs.

class CalculateurTest {
    private Calculateur calc;
    private Matrice testGeneral33 = new Matrice("", 3, 3),
        testDeterminantEtInverse = new Matrice("", 3, 3),
        testProduitVectoriel1 = new Matrice("", 3, 1),
        testProduitVectoriel2 = new Matrice("", 3, 1),
        testProduitHadamard1 = new Matrice("", 3, 2),
        testProduitHadamard2 = new Matrice("", 3, 2),
        testProduitTensoriel1 = new Matrice("", 2, 2),
        testProduitTensoriel2 = new Matrice("", 2, 2);

    @BeforeEach
    void setUp() {
        calc = new Calculateur();
        int a = 0;

        for (int i = 0; i < testGeneral33.getRows(); i++)
            for (int j = 0; j < testGeneral33.getColumns(); j++)
                testGeneral33.setValue(a++, j, i);

        testDeterminantEtInverse.setValue(4, 0, 0);
        testDeterminantEtInverse.setValue(2, 1, 0);
        testDeterminantEtInverse.setValue(3, 2, 0);
        testDeterminantEtInverse.setValue(5, 0, 1);
        testDeterminantEtInverse.setValue(5, 1, 1);
        testDeterminantEtInverse.setValue(6, 2, 1);
        testDeterminantEtInverse.setValue(7, 0, 2);
        testDeterminantEtInverse.setValue(8, 1, 2);
        testDeterminantEtInverse.setValue(9, 2, 2);

        testProduitVectoriel1.setValue(1, 0, 0);
        testProduitVectoriel1.setValue(-2, 1, 0);
        testProduitVectoriel1.setValue(1, 2, 0);

        testProduitVectoriel2.setValue(2, 0, 0);
        testProduitVectoriel2.setValue(2, 1, 0);
        testProduitVectoriel2.setValue(-1, 2, 0);

        testProduitHadamard1.setValue(1, 0, 0);
        testProduitHadamard1.setValue(5, 1,0);

    }

    @Test
    void additionSoustraction() {
        Matrice expected = new Matrice("", 3, 3);
        int a = 0;

        for (int i = 0; i < testGeneral33.getRows(); i++)
            for (int j = 0; j < testGeneral33.getColumns(); j++) {
                expected.setValue(a + a, j, i);
                a++;
            }

        assertEquals(true, calc.isEqual(expected, calc.additionSoustraction(true, testGeneral33, testGeneral33)));
    }

    @Test
    void multScalaire() {
        Matrice expected = new Matrice("", 3, 3);
        int a = 0;

        for (int i = 0; i < testGeneral33.getRows(); i++)
            for (int j = 0; j < testGeneral33.getColumns(); j++) {
                expected.setValue(4 * a, j, i);
                a++;
            }

        assertEquals(true, calc.isEqual(expected, calc.multScalaire(testGeneral33, 4)));
    }

    @Test
    void puissance() {
        Matrice expected = new Matrice("", 3, 3);
        expected.setValue(468, 0, 0);
        expected.setValue(576, 1, 0);
        expected.setValue(684, 2, 0);
        expected.setValue(1062, 0, 1);
        expected.setValue(1305, 1, 1);
        expected.setValue(1548, 2, 1);
        expected.setValue(1656, 0, 2);
        expected.setValue(2034, 1, 2);
        expected.setValue(2412, 2, 2);

        assertEquals(true, calc.isEqual(expected, calc.puissance(testGeneral33, 2)));
    }

    @Test
    void transposee() {
        Matrice expected = new Matrice("", 3, 3);
        expected.setValue(1, 0, 0);
        expected.setValue(4, 1, 0);
        expected.setValue(7, 2, 0);
        expected.setValue(2, 0, 1);
        expected.setValue(5, 1, 1);
        expected.setValue(8, 2, 1);
        expected.setValue(3, 0, 2);
        expected.setValue(6, 1, 2);
        expected.setValue(9, 2, 2);

        assertEquals(true, calc.isEqual(expected, calc.transposee(testGeneral33)));
    }

    @Test
    void inversion() {
        Matrice expected = new Matrice("", 3, 3);
        expected.setValue(1, 0, 0);
        expected.setValue(-2, 1, 0);
        expected.setValue(1, 2, 0);
        expected.setValue(1, 0, 1);
        expected.setValue(-5, 1, 1);
        expected.setValue(3, 2, 1);
        expected.setValue(-5 / 3, 0, 2);
        expected.setValue(6, 1, 2);
        expected.setValue(-10 / 3, 2, 2);

        assertEquals(true, calc.isEqual(expected, calc.inversion(testDeterminantEtInverse)));
    }

    @Test
    void produitMat() {
        Matrice expected = new Matrice("", 3, 3);
        expected.setValue(35, 0, 0);
        expected.setValue(36, 1, 0);
        expected.setValue(42, 2, 0);
        expected.setValue(83, 0, 1);
        expected.setValue(81, 1, 1);
        expected.setValue(96, 2, 1);
        expected.setValue(131, 0, 2);
        expected.setValue(126, 1, 2);
        expected.setValue(150, 2, 2);

        assertEquals(true, calc.isEqual(expected, calc.produitMat(testGeneral33, testDeterminantEtInverse)));
    }

    @Test
    void produitVect() {
        Matrice expected = new Matrice("", 3, 1);
        expected.setValue(0, 0, 0);
        expected.setValue(3, 1, 0);
        expected.setValue(6, 2, 0);

        assertEquals(true, calc.isEqual(expected, calc.produitVect(testProduitVectoriel1, testProduitVectoriel2)));
    }

    @Test
    void produitHad() {
        Matrice expected = new Matrice("", 3, 2);
        expected.setValue(2, 0, 0);
        expected.setValue(35, 1, 0);
        expected.setValue(2, 2, 0);
        expected.setValue(12, 0, 1);
        expected.setValue(4, 1, 1);
        expected.setValue(6, 2, 1);

        assertEquals(true, calc.isEqual(expected, calc.produitHad(testProduitHadamard1, testProduitHadamard2)));
    }

    @Test
    void produitTens() {
        Matrice expected = new Matrice("", 4, 4);
        expected.setValue(1, 0, 0);
        expected.setValue(1, 1, 0);
        expected.setValue(2, 2, 0);
        expected.setValue(2, 3, 0);
        expected.setValue(2, 0, 1);
        expected.setValue(4, 1, 1);
        expected.setValue(4, 2, 1);
        expected.setValue(8, 3, 1);
        expected.setValue(3, 0, 2);
        expected.setValue(3, 1, 2);
        expected.setValue(1, 2, 2);
        expected.setValue(1, 3, 2);
        expected.setValue(6, 0, 3);
        expected.setValue(12, 1, 3);
        expected.setValue(2, 2, 3);
        expected.setValue(4, 3, 3);

        assertEquals(true, calc.isEqual(expected, calc.produitTens(testProduitTensoriel1, testProduitTensoriel2)));
    }

    @Test
    void determinant() {
        assertEquals(-3, calc.determinant(testDeterminantEtInverse));
    }

}