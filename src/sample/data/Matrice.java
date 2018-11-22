package sample.data;

import java.util.ArrayList;

public class Matrice {
    private String nom;

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

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

    public float getValue(int colonne, int row){
        return tableau[colonne][row];
    };
    public String toString(){
        ArrayList<String>resultat= new ArrayList<>();
        String matrice= "[";
        for(int i=0;i<this.getColumns();i++){
            for(int j=0;j<this.getRows();j++){
                matrice+= (int)this.getValue(j,i)+",";
            }

            if(i==rows-1)
                matrice+="]";
            else
            matrice+="\n";
        }
        return matrice;
    }
}
