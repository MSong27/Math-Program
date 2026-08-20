import java.lang.Math;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class Matrix {
    private int row;
    private int column;
    private double[][] matrix;
    public Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        this.matrix = new double[row][column];
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    public double getEntry(int row, int column){
        return this.matrix[row - 1][column - 1];
    }

    public void setEntries(int row, int column, double entry) {
        if (row > this.row || column > this.column){
            throw new IllegalArgumentException("Entry is not valid for current matrix");
        }
        else
            matrix[row - 1][column - 1] = entry;
    }

    public void addEntries(double[][] entry) {
        if (entry.length > this.row || column > entry[0].length){
            throw new IllegalArgumentException("Entry is not valid for current matrix");
        }
        else
            matrix = entry;
    }

    public Matrix add(Matrix n) {
        if(this.row != n.row || this.column != n.column) {
            throw new IllegalArgumentException("Matrices are not equal dimensions");
        }
        Matrix sum = new Matrix(n.getRow(), n.getColumn());
            for(int i = 1 ; i <= row ; i++) {
                for(int j = 1 ; j <= column ; j++) {
                    sum.setEntries(i, j, this.getEntry(i, j) + n.getEntry(i, j));
                }
            }
        return sum;
    }

    public String toString() {
        StringBuilder rowVector = new StringBuilder();
        StringBuilder totalMatrix = new StringBuilder();
        for(int i = 0 ; i < row ; i++)
        {
            rowVector.setLength(0);
            rowVector.append("[" + matrix[i][0]);
            for(int j = 1 ; j < column; j++)
            {
                rowVector.append(", " + matrix[i][j]);
            }
            totalMatrix.append(rowVector + "]\n");
        }
        return totalMatrix.toString();
    }

    public Matrix scale(double scalar)
    {
        Matrix scalarMultiple = new Matrix(this.row, this.column);
        for(int i = 1 ; i <= row ; i++) {
            for(int j = 1 ; j <= column ; j++) {
                scalarMultiple.setEntries(i, j, scalar * this.getEntry(i, j));
            }
        }
        return scalarMultiple;
    }
    //public Matrix multiply(Matrix other)
   // {
        //if(this.getColumn() != other.getRow()) {
          //  throw new IllegalArgumentException
       // }
  //  }
}
