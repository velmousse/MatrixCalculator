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
    private double tableau[][];

    public Matrice (String nom, int columns, int rows){
        this.nom = nom;
        this.columns = columns;
        this.rows = rows;

        tableau = new double[columns][rows];
    }

    public String getNom() {
        return this.nom;
    }

    public void setValue(double value, int column, int row)  {
        tableau[column][row] = value;
    }

    public double getValue(int colonne, int row){
        return tableau[colonne][row];
    };
    public String toString(){
        String matrice= "[";
        for(int i=0;i<this.getRows();i++){
            for(int j=0;j<this.getColumns();j++){
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
