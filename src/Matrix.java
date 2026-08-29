import java.lang.Math;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class Matrix {
    private int row;
    private int column;
    private double[][] matrix;
    public static final double epsilon = 10e-9;
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
            if (entry == 0) {
                entry = 0;
            }
            matrix[row - 1][column - 1] = entry;
    }

    public void setEntries(double[][] entry) {
        if (entry.length > this.row || column > entry[0].length){
            throw new IllegalArgumentException("Entry is not valid for current matrix");
        }
        else
            matrix = entry;
    }

    public void setEntries(Matrix entry) {
        if (entry.row != this.row || column != entry.column){
            throw new IllegalArgumentException("Entry is not valid for current matrix");
        }
        else
            for (int i = 1 ; i <= this.row ; i++) {
                for (int j = 1 ; j <= this.column ; j++) {
                    setEntries(i, j, entry.getEntry(i, j));
                }
            }
    }

    public Matrix add(Matrix n) {
        if (this.row != n.row || this.column != n.column) {
            throw new IllegalArgumentException("Matrices are not equal dimensions");
        }
        Matrix sum = new Matrix(n.getRow(), n.getColumn());
            for (int i = 1 ; i <= row ; i++) {
                for (int j = 1 ; j <= column ; j++) {
                    sum.setEntries(i, j, this.getEntry(i, j) + n.getEntry(i, j));
                }
            }
        return sum;
    }

    public String toString() {
        StringBuilder rowVector = new StringBuilder();
        StringBuilder totalMatrix = new StringBuilder();
        for (int i = 0 ; i < row ; i++)
        {
            rowVector.setLength(0);
            rowVector.append("[" + matrix[i][0]);
            for (int j = 1 ; j < column; j++)
            {
                rowVector.append(", " + matrix[i][j]);
            }
            totalMatrix.append(rowVector + "]\n");
        }
        return totalMatrix.toString();
    }

    public boolean equals(Matrix other) {
        boolean isEquals = true;
        for (int i = 1 ; i <= row ; i++) {
            for (int j = 1 ; j <= column ; j++) {
                if (this.getEntry(i, j) != other.getEntry(i,j)) {
                    isEquals = false;
                }
            }
        }
        return isEquals;
    }

    public Matrix scale(double scalar)
    {
        Matrix scalarMultiple = new Matrix(this.row, this.column);
        for (int i = 1 ; i <= row ; i++) {
            for (int j = 1 ; j <= column ; j++) {
                scalarMultiple.setEntries(i, j, scalar * this.getEntry(i, j));
            }
        }
        return scalarMultiple;
    }

    public Matrix subtract(Matrix other)
    {
        return this.add(other.scale(-1));
    }

    public Matrix multiply(Matrix other) {
        if (this.getColumn() != other.getRow()) {
            throw new IllegalArgumentException("Invalid dimensions for one or both of the matrices");
        }
        Matrix product = new Matrix(this.row, other.getColumn());
        for (int i = 1 ; i <= product.getRow() ; i++) {
            for (int j = 1 ; j <= product.getColumn() ; j++) {
                double entry = 0;
                for (int k = 1 ; k <= this.column ; k++) {
                    entry += this.getEntry(i, k) * other.getEntry(k, j);
                }
                product.setEntries(i, j, entry);
            }
        }
        return product;
    }

     public static Matrix identity(int size) {
         Matrix id = new Matrix(size, size);
         for (int i = 1 ; i <= size ; i ++){
             id.setEntries(i, i, 1);
         }
         return id;
     }

     public static Matrix zero(int row, int column) {
         return new Matrix(row, column);
     }

     public Matrix transpose() {
        Matrix transpose = new Matrix(this.column, this.row);
        for (int i = 1 ; i <= this.row ; i++)
        {
           for (int j = 1 ; j <= this.column; j++)
           {
               transpose.setEntries(j, i, this.getEntry(i, j));
           }
        }
        return transpose;
     }

     public Matrix fill(double entry) {
         for (int i = 1 ; i <= this.row ; i++)
         {
             for (int j = 1 ; j <= this.column; j++)
             {
                 this.setEntries(i, j, entry);
             }
         }
         return this;
     }

     //Elementary Row Operations

     public Matrix swap(int row1, int row2) {
        if (row1 < 1 || row2 < 1 || row1 > this.getRow() || row2 > this.getRow()) {
            throw new IllegalArgumentException("Invalid row");
        }
        double temp;
        for (int i = 1 ; i <= this.column ; i++) {
            temp = this.getEntry(row1, i);
            this.setEntries(row1, i, this.getEntry(row2, i));
            this.setEntries(row2, i, temp);
        }
        return this;
     }

     public Matrix rowScale(int row, double scalar) {
         if (row < 1 || row > this.getRow()) {
             throw new IllegalArgumentException("Invalid row");
         }
         for (int i = 1 ; i <= this.column ; i++) {
             this.setEntries(row, i, scalar * this.getEntry(row, i));
         }
         return this;
     }

     public Matrix rowReplace(int row1, int row2, double scalar) {
         if (row1 < 1 || row2 < 1 || row1 > this.getRow() || row2 > this.getRow()) {
             throw new IllegalArgumentException("Invalid row");
         }
         for (int i = 1 ; i <= this.column ; i++) {
             this.setEntries(row1, i, this.getEntry(row1, i) + scalar * this.getEntry(row2, i));
         }
         return this;
     }

     public Matrix augment(Matrix matrixB) {
         if (this.row != matrixB.getRow()) {
             throw new IllegalArgumentException("Rows must be the same between the two matrices");
         }
         Matrix augmentedMatrix = new Matrix(this.row, matrixB.getColumn() + this.column);
         for (int i = 1; i <= this.row; i++) {
             for (int j = 1; j <= this.column; j++) {
                 augmentedMatrix.setEntries(i, j, this.getEntry(i, j));
             }
         }
         for (int i = 1; i <= matrixB.getRow(); i++) {
             for (int j = 1; j <= matrixB.getColumn(); j++) {
                 augmentedMatrix.setEntries(i, j + this.column, matrixB.getEntry(i, j));
             }
         }
         return augmentedMatrix;
     }

     //Gaussian Elimination Algorithms

    public boolean columnIsZero(int row, int column) {
        boolean isZero = true;
        for (int i = row ; i <= this.row ; i++) {
            if (Math.abs(this.getEntry(i, column)) > epsilon) {
                isZero = false;
            }
        }
        return isZero;
    }

    public boolean rowIsZero(int row, int column) {
        boolean isZero = true;
        for (int i = column ; i <= this.column ; i++) {
            if (Math.abs(this.getEntry(row, i)) > epsilon) {
                isZero = false;
                break;
            }
        }
        return isZero;
    }

    //Errors IF there is a zero row

    public Matrix gaussianElimination() {
        Matrix ref = new Matrix(this.row, this.column);
        ref.setEntries(this);
        int r = 1;
        int c = 1;
        while (r <= ref.getRow() && c <= ref.getColumn()) {
            int i = c;
            while (i <= ref.getColumn()) {
                if (ref.columnIsZero(r, i)) {
                    i++;
                    if (i > ref.getColumn()) {
                        return ref;
                    }
                }
                else {
                    break;
                }
            }
            ref.partialPivot(r, i);
            ref.rowScale(r, 1 / ref.getEntry(r, i));
            if (r + 1 <= ref.getRow()) {
                for (int j = r + 1; j <= ref.getRow() ; j ++) {
                    ref.rowReplace(j, r, -1 * ref.getEntry(j, i));
                }
            }
            r++;
            c = i + 1;
        }
        return ref;
    }

    public Matrix partialPivot(int row, int column) {
        double maxValue = this.getEntry(row, column);
        int maxRow = row;
        for (int i = row ; i <= this.row ; i++) {
            if (Math.abs(this.getEntry(i, column)) > Math.abs(maxValue)) {
                maxValue = this.getEntry(i, column);
                maxRow = i;
            }
        }
        swap(row, maxRow);
        return this;
    }

    //Method to see which row is swapped for
    public static int partialPivotMaxRow(Matrix matrix, int row, int column) {
        double maxValue = matrix.getEntry(row, column);
        int maxRow = row;
        for (int i = row ; i <= matrix.row ; i++) {
            if (Math.abs(matrix.getEntry(i, column)) > Math.abs(maxValue)) {
                maxValue = matrix.getEntry(i, column);
                maxRow = i;
            }
        }
        return maxRow;
    }


    public Matrix zeroesAllBelow() {
        int i = 1;
        int j = this.row;
        while(i < j) {

            if (!this.rowIsZero(i, 1)) {
                i++;
            }
            if (this.rowIsZero(j, 1)) {
                j--;
            }
            else if (this.rowIsZero(i, 1) && !this.rowIsZero(j, 1)) {
                swap(i, j);
            }
        }
        return this;
    }

    public int findMaxLeftRow(int row, int column) {
        int targetRow = row;
        int targetColumn = -1;
        double maxValue = 0;
        for (int i = column ; i <= this.column ; i++) {
            if (!this.columnIsZero(row, i)) {
                targetColumn = i;
                break;
            }
        }
        if (targetColumn == -1) {
            return targetColumn;
        }
        else {
            for (int j = column ; j <= this.row ; j++) {
                if (Math.abs(this.getEntry(j, targetColumn)) > Math.abs(maxValue)) {
                    maxValue = this.getEntry(j, targetColumn);
                    targetRow = j;
                }
            }
        }
        return targetRow;
    }

    public int findFirstNonZeroColumn(int row) {
        int targetColumn = -1;
        for (int i = 1 ; i <= this.column ; i++) {
            if (this.getEntry(row, i) != 0) {
                targetColumn = i;
            }
        }
        return targetColumn;
    }

    public Matrix gaussJordan() {
        Matrix rref = new Matrix(this.row, this.column);
        rref.setEntries(this);
        rref.zeroesAllBelow();
        int c = 1;
        int r = 1;
        while (c <= rref.getColumn() && r <= rref.getRow()) {
            int i = c;
            while (i <= rref.getColumn()) {
                if (rref.columnIsZero(r, i)) {
                    i++;
                    if (i > rref.getColumn()) {
                        return rref;
                    }
                }
                else {
                    break;
                }
            }
            rref.partialPivot(r, i);
            rref.rowScale(r, 1.0 / rref.getEntry(r, i));
            for (int j = 1 ; j <= rref.getRow() ; j++) {
                if (j != r) {
                    rref.rowReplace(j, r, -1.0 * rref.getEntry(j, i));
                }
            }
            r++;
            c = i + 1;
        }
        return rref;
    }

    public Matrix findInverse() {
        Matrix identityAugmented = new Matrix(this.row, 2 * this.column);
        identityAugmented.setEntries(augment(Matrix.identity(this.row)));
        identityAugmented = identityAugmented.gaussJordan();
        Matrix inverse = new Matrix(this.row, this.column);
        for (int i = 1 ; i <= this.row ; i++) {
            for (int j = 1 ; j <= this.column ; j++) {
                inverse.setEntries(i, j, identityAugmented.getEntry(i, j + this.column));
            }
        }
        return inverse;
    }

    public boolean isStochastic() {
        if (this.row != this.column) {
            throw new IllegalArgumentException("Stochastic matrices must be n x n");
        }
        for (int i = 1 ; i <= this.row ; i++) {
            double columnSum = 0;
            for (int j = 1 ; j <= this.column ; j++) {
                columnSum += this.getEntry(j, i);
            }
            if (columnSum - 1 > epsilon) {
                return false;
            }
        }
        return true;
    }

    public boolean hasZeroEntry() {
        for (int i = 1 ; i <= this.row ; i++) {
            for (int j = 1 ; j <= this.column ; j++) {
                if (this.getEntry(i, j) == 0.0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isRegularStochastic() {
        if (!this.isStochastic()) {
            throw new IllegalArgumentException("Matrix is not stochastic");
        }
        Matrix regularCheck = new Matrix(this.row, 2 * this.column);
        Matrix exponentFactor = new Matrix(this.row, 2 * this.column);
        regularCheck.setEntries(this);
        exponentFactor.setEntries(this);
        if (!regularCheck.hasZeroEntry()) {
            return true;
        }
        for (int i = 1 ; i <= (regularCheck.getRow() - 1) * (regularCheck.getRow() - 1) + 1 ; i++) {
            regularCheck.setEntries(regularCheck.multiply(exponentFactor));
            if (!regularCheck.hasZeroEntry()) {
                return true;
            }
        }
        return false;
    }

    public static Matrix steadyStateMatrix(Matrix matrix) {
        if (!matrix.isStochastic()) {
            throw new IllegalArgumentException("Matrix must be stochastic!");
        }
        Matrix nthSteadyState = new Matrix(matrix.getRow() + 1, matrix.getColumn());
        Matrix columnMatrix = new Matrix(matrix.getRow() + 1, 1);
        columnMatrix.fill(1);
        nthSteadyState.setEntries(matrix.multiply(columnMatrix));
        for (int i = 1 ; i < matrix.getColumn() ; i++) {
            nthSteadyState.setEntries(matrix.getRow(), i, 1);
        }
        nthSteadyState.setEntries(nthSteadyState.augment(columnMatrix));
        return nthSteadyState;
        //add a solve feature
    }

    public static boolean checkIfElementary(Matrix matrix) {
        if (matrix.getRow() != matrix.getColumn()) {
            throw new IllegalArgumentException("Elementary matrices are n x n");
        }
        Matrix compare = new Matrix(matrix.getRow(), matrix.getColumn());
        compare.setEntries(matrix.subtract(Matrix.identity(matrix.getRow())));
        int nonZeroCounter = 0;
        for (int i = 1 ; i <= compare.getRow() ; i++) {
            for (int j = 1 ; j <= compare.getColumn() ; j++) {
                if (Math.abs(compare.getEntry(i, j)) > epsilon) {
                    nonZeroCounter++;
                }
            }
        }
        if (nonZeroCounter == 0) {
            return true;
        }
        else if (nonZeroCounter == 1) {
                for (int i = 1 ; i <= compare.getColumn() ; i++) {
                    if (Math.abs(matrix.getEntry(i, i)) < epsilon) {
                        return false;
                    }
                }
            return true;
        }
        else if (nonZeroCounter == 4) {
            for (int i = 1 ; i <= compare.getRow() ; i++) {
                for (int j = 1 ; j <= compare.getColumn() ; j++) {
                    if (Math.abs(compare.getEntry(i, j)) - 1.0 > epsilon) {
                        return false;
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public static int findOriginalRow(ArrayList<Integer> rowTracker, Integer target) {
        for (int i = 0 ; i < rowTracker.size(); i+= 2) {
            if (rowTracker.get(i + 1).equals(target)) {
                return rowTracker.get(i);
            }
        }
        throw new IllegalArgumentException("No such target found");
    }

    //Only works for squares for now
    public static Matrix[] luDecomposition(Matrix matrix) {
        ArrayList<Matrix> elementaryMatrixTracker = new ArrayList<>(0);
        int r = 1;
        int c = 1;
        while (r <= matrix.getRow() && c <= matrix.getColumn()) {
            int i = c;
            outerloop: while (i <= matrix.getColumn()) {
                if (matrix.columnIsZero(r, i)) {
                    i++;
                    if (i > matrix.getColumn()) {
                        break outerloop;
                    }
                }
                else {
                    break;
                }
            }
            if (r + 1 <= matrix.getRow()) {
                for (int j = r + 1; j <= matrix.getRow() ; j ++) {
                    elementaryMatrixTracker.add(Matrix.identity(matrix.getRow()).rowReplace(j, r, -1 * matrix.getEntry(j, i) / matrix.getEntry(r, i)));
                    matrix.rowReplace(j, r, -1 * matrix.getEntry(j, i) / matrix.getEntry(r, i));
                }
            }
            r++;
            c = i + 1;
        }
        Matrix L = new Matrix(matrix.getRow(), matrix.getColumn());
        L.setEntries(elementaryMatrixTracker.getFirst().findInverse());
        for (int i = 1 ; i < elementaryMatrixTracker.size() ; i++) {
            L.setEntries(L.multiply(elementaryMatrixTracker.get(i).findInverse()));
        }
        Matrix[] LU = new Matrix[2];
        LU[0] = L;
        LU[1] = matrix;
        return LU;
    }
}
