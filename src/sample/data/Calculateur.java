package sample.data;

import javafx.scene.control.TextArea;

public class Calculateur {

    public boolean isEqual(Matrice matrice1, Matrice matrice2) {
        if (matrice1.getRows() == matrice2.getRows() && matrice1.getColumns() == matrice2.getColumns()) {
            for (int i = 0; i < matrice1.getRows(); i++)
                for (int j = 0; j < matrice1.getColumns(); j++)
                    if (matrice1.getValue(j, i) != matrice1.getValue(j, i))
                        return false;
            return true;
        } else
            return false;
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

        for (int i = 0; i < matrice.getRows(); i++)
            for (int j = 0; j < matrice.getColumns(); j++)
                inverse.setValue((float) (Math.pow(-1, (i + 1) + (j + 1))) * (1 / scalaire) * determinant(mineur(matrice, j, i)), i, j);
        return inverse;
    }

    public Matrice produitMat(Matrice matrice1, Matrice matrice2) {
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
