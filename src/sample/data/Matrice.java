package sample.data;

public class Matrice {
    private String nom;
    private int columns, rows;
    private float tableau[][];

    public Matrice (String nom, int columns, int rows){
        this.nom = nom;
        this.columns = columns;
        this.rows = rows;

        tableau = new float[rows][columns];
    }

    public String getNom() {
        return this.nom;
    }

    public void setValue(float value, int column, int row)  {
        tableau[row][column] = value;
    }
}
