/**
 * Represents a two-dimensional matrix and provides operations without using any external math dependencies
 *<p>
 * Provides many features with matrices such as operations, algorithms, decompositions, etc.
 *
 * @todo Implement mechanisms against double-precision error and add tolerance threshold with epsilon to all functions that need it
 * @todo Add more features such as QR decomposition, PLU decomposition, back substitution, etc.
 *
 * @author Michael Song
 */

import java.lang.Math;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.util.Arrays;
import java.util.ArrayList;

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

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public double getEntry(int row, int column) {
        return this.matrix[row - 1][column - 1]; //Subtract 1 to convert 1 based indexing to 0 based indexing
    }

    public void setEntries(int row, int column, double entry) {
        if (row > this.row || column > this.column){
            throw new IllegalArgumentException("Entry is not valid for current matrix");
        }
        else {
            matrix[row - 1][column - 1] = entry;
        }
    }

    public void setEntries(double[][] entry) {
        if (entry.length > this.row || column > entry[0].length){
            throw new IllegalArgumentException("Entry is not valid for current matrix");
        }
        else {
            matrix = entry;
        }
    }

    public void setEntries(Matrix entry) {
        if (entry.row != this.row || column != entry.column){
            throw new IllegalArgumentException("Entry is not valid for current matrix");
        }
        else {
            for (int i = 1; i <= this.row; i++) {
                for (int j = 1; j <= this.column; j++) {
                    setEntries(i, j, entry.getEntry(i, j));
                }
            }
        }
    }

    /**
     * Adds two matrices and returns the sum
     *
     * @param other is the other matrix to be added
     * @return The sum of the two matrices
     * @throws IllegalArgumentException if matrices are different dimensions
     */
    public Matrix add(Matrix other) {
        if (this.row != other.row || this.column != other.column) {
            throw new IllegalArgumentException("Matrices are not equal dimensions");
        }
        Matrix sum = new Matrix(other.getRow(), other.getColumn());
            for (int i = 1 ; i <= row ; i++) {
                for (int j = 1 ; j <= column ; j++) {
                    sum.setEntries(i, j, this.getEntry(i, j) + other.getEntry(i, j));
                }
            }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder rowVector = new StringBuilder();
        StringBuilder totalMatrix = new StringBuilder();
        for (int i = 0 ; i < row ; i++)
        {
            rowVector.setLength(0);
            double initialValue = matrix[i][0] + 0.0;
            rowVector.append("[" + initialValue);
            for (int j = 1 ; j < column; j++)
            {
                double value = matrix[i][j] + 0.0;
                rowVector.append(", " + value);
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

    public Matrix scale(double scalar) {
        Matrix scalarMultiple = new Matrix(this.row, this.column);
        for (int i = 1 ; i <= row ; i++) {
            for (int j = 1 ; j <= column ; j++) {
                scalarMultiple.setEntries(i, j, scalar * this.getEntry(i, j));
            }
        }
        return scalarMultiple;
    }

    /**
     * Subtracts current matrix from another matrix
     *
     * @param other matrix that will subtract from the current matrix
     * @return the new matrix
     * @throws IllegalArgumentException if the matrices are not the same dimensions
     */
    public Matrix subtract(Matrix other) {
        return this.add(other.scale(-1));
    }

    /**
     * Multiplies current matrix from another matrix
     *
     * @param other matrix that will be multiplied with the current matrix
     * @return the new matrix
     * @throws IllegalArgumentException if the matrices are not the same dimensions
     */
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

    /**
     * Creates an identity matrix of a given size
     *
     * @param size is the size of the rows and columns of the matrix
     * @return an identity matrix of given size
     */
     public static Matrix identity(int size) {
         Matrix id = new Matrix(size, size);
         for (int i = 1 ; i <= size ; i ++) {
             id.setEntries(i, i, 1);
         }
         return id;
     }

    /**
     * Creates a zero matrix based off given dimensions
     *
     * @return a matrix filled with zeroes based off given dimension
     */
     public static Matrix zero(int row, int column) {
         return new Matrix(row, column);
     }

     public Matrix transpose() {
        Matrix transpose = new Matrix(this.column, this.row);
        for (int i = 1 ; i <= this.row ; i++) {
           for (int j = 1 ; j <= this.column; j++) {
               transpose.setEntries(j, i, this.getEntry(i, j));
           }
        }
        return transpose;
     }

    /**
     * Creates a zero matrix based off given dimensions
     *
     * @return a matrix filled with zeroes based off given dimension
     */
     public Matrix fill(double entry) {
         for (int i = 1 ; i <= this.row ; i++) {
             for (int j = 1 ; j <= this.column; j++) {
                 this.setEntries(i, j, entry);
             }
         }
         return this;
     }

    /**
     * Swaps two rows in a given matrix
     *
     * @param row1 one of the rows that will be swapped
     * @param row2 one of the rows that will be swapped
     * @return the matrix with the two given rows swapped
     * @throws IllegalArgumentException if the selected rows are greater than the dimension of the matrix
     */
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

    /**
     * Multiplies given row by a scalar
     *
     * @param row the row to be scaled
     * @param scalar the number the row will be scaled by
     * @return the matrix with the given row scaled
     * @throws IllegalArgumentException if the selected row is greater than the dimension of the matrix
     */
     public Matrix rowScale(int row, double scalar) {
         if (row < 1 || row > this.getRow()) {
             throw new IllegalArgumentException("Invalid row");
         }
         for (int i = 1 ; i <= this.column ; i++) {
             this.setEntries(row, i, scalar * this.getEntry(row, i));
         }
         return this;
     }

    /**
     * Adds a multiple of a row to another
     *
     * @param row1 the row that will be modified
     * @param row2 the row that will be added to row1
     * @param scalar the scalar that will be multiplied to row2
     * @return the matrix with row1 = row1 + scalar * row2
     * @throws IllegalArgumentException if the given rows are out of bounds
     */
     public Matrix rowReplace(int row1, int row2, double scalar) {
         if (row1 < 1 || row2 < 1 || row1 > this.getRow() || row2 > this.getRow()) {
             throw new IllegalArgumentException("Invalid row");
         }
         for (int i = 1 ; i <= this.column ; i++) {
             this.setEntries(row1, i, this.getEntry(row1, i) + scalar * this.getEntry(row2, i));
         }
         return this;
     }

    /**
     * Appends a matrix to the current matrix
     *
     * @param matrixB the matrix that will be added to the current matrix
     * @return the augmented matrix
     * @throws IllegalArgumentException if the rows of the two matrices are unequal
     */
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

    /**
     * Checks if the column below a certain entry (inclusive) is all zero
     *
     * @param row the row to be inspected
     * @param column the column and the columns to the right to be inspected
     * @return true if the column is zero, else false
     */
    public boolean columnIsZero(int row, int column) {
        boolean isZero = true;
        for (int i = row ; i <= this.row ; i++) {
            if (Math.abs(this.getEntry(i, column)) > epsilon) {
                isZero = false;
            }
        }
        return isZero;
    }

    /**
     * Checks if the row to the right of a certain entry (inclusive) is all zero
     *
     * @param row the row and the rows to the below to be inspected
     * @param column the column to be inspected
     * @return true if the column is zero, else false
     */
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

    /**
     * Finds a row echelon form of a given matrix
     *
     * @return the given matrix is row echelon form
     */
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

    /**
     * Swaps the row with the largest, leftmost (that is absolutely greater than 0) with the given row
     *
     * @param row the row and the rows below to inspect
     * @param column the column and the columns below to inspect
     * @return the matrix with the largest, leftmost value (that is absolutely greater than 0) at the top
     */
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

    /**
     * Finds which row has the largest, leftmost value (that is absolutely greater than 0)
     *
     * @param matrix the matrix to be inspected
     * @param row the row and the rows below to inspect
     * @param column the column and the columns below to inspect
     * @return the number that the row with the largest, leftmost value is
     */
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

    /**
     * Puts all rows that are 0 to the bottom of the matrix
     *
     * @return the matrix with all 0 rows at the bottom
     */
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

    /**
     * Finds the first nonzero column of a given row
     *
     * @param row the row to be inspected
     * @return the column number of the column with the first nonzero entry
     */
    public int findFirstNonZeroColumn(int row) {
        int targetColumn = -1;
        for (int i = 1 ; i <= this.column ; i++) {
            if (this.getEntry(row, i) != 0) {
                targetColumn = i;
            }
        }
        return targetColumn;
    }

    /**
     * Finds the reduced row echelon form of a given matrix
     *
     * @return the given matrix is reduced row echelon form
     */
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

    /**
     * Finds the inverse of the current matrix
     *
     * @return the inverse of the current matrix
     * @throws IllegalArgumentException if the matrix is noninvertible
     */
    public Matrix findInverse() {
        if (Matrix.findDeterminant(this) == 0) {
            throw new IllegalArgumentException("Inverse does not exist");
        }
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

    /**
     * Checks if the current matrix is stochastic
     *
     * @return true if the matrix is stochastic, false otherwise
     * @throws IllegalArgumentException if the given matrix does not have dimensions n x n
     */
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

    /**
     * Checks if a given matrix is a regular stochastic matrix. A regular stochastic matrix is a stochastic matrix
     * where there is at least 1 integer K such that if the matrix is raised to the power of K, every entry is positive
     *
     * @return true if it is a regular stochastic matrix, false otherwise
     * @throws IllegalArgumentException if the given matrix is not stochastic
     */
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

    /**
     * Finds the steady state matrix of a given stochastic matrix. The steady state matrix is a stochastic matrix where
     * the matrix does not change when multiplied with a transition matrix
     *
     * @param matrix the matrix to be inspected
     * @return the steady state matrix
     * @throws IllegalArgumentException if the given matrix is not stochastic
     */
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

    /**
     * Checks if a given matrix is elementary. An elementary matrix is an n x n matrix that can be achieved with
     * one single elementary row operation
     *
     * @param matrix is the matrix to be inspected
     * @return true if the matrix is elementary, false otherwise
     * @throws IllegalArgumentException if the given matrix does not have dimensions n x n
     */
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

    /**
     * Finds the given LU decomposition of a given matrix. Only works for certain square matrices
     *
     * @param matrix the matrix to be decomposed
     * @return a LU decomposition of the given matrix
     */
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

    /**
     * Finds the determinant of a given matrix.
     *
     * @param matrix the matrix to be computed
     * @return the determinant of a matrix
     * @throws IllegalArgumentException if the given matrix does not have dimensions n x n
     */
    public static double findDeterminant(Matrix matrix) {
        if (matrix.getRow() != matrix.getColumn()) {
            throw new IllegalArgumentException("Determinants only exist in n x n matrices");
        }
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
                    matrix.rowReplace(j, r, -1 * matrix.getEntry(j, i) / matrix.getEntry(r, i));
                }
            }
            r++;
            c = i + 1;
        }
        double determinant = 1;
        for (int i = 1 ; i <= matrix.getRow() ; i++) {
            determinant*= matrix.getEntry(i, i);
        }
        return determinant;
    }

    public Matrix solveEquation() {
        Matrix rref = new Matrix(this.getRow(), this.getColumn());
        rref.setEntries(this.gaussJordan());
        Matrix solveMatrix = new Matrix(this.getRow(), 1);
        for(int i = 1 ; i <= this.getRow() ; i++) {
            solveMatrix.setEntries(i, 1, rref.getEntry(i, rref.getColumn()));
        }
        return solveMatrix;
    }

    public int countLeadingOnes() {
        int counter = 0;
        for (int i = 1 ; i <= this.getRow() ; i++) {
            innerloop: for (int j = 1 ; j <= this.getColumn() ; j++) {
                if (Math.abs(Math.abs(this.getEntry(i, j)) - 1.0) < epsilon) {
                    counter++;
                    break innerloop;
                }
            }
        }
        return counter;
    }

    public int findRank() {
        this.gaussianElimination();
        int counter = 0;
        for(int i = 1 ; i <= this.getRow() ; i++) {
            if (!this.rowIsZero(i, 1)) {
               counter++;
            }
        }
        return counter;
    }

    public int findNullity() {
        return this.getColumn() - this.findRank();
    }

    public static Matrix dotProduct(Matrix one, Matrix two) {
        if (one.getColumn() != 1  || two.getColumn() != 1) {
            throw new IllegalArgumentException("Matrices must be column vectors");
        }
        return one.transpose().multiply(two);
    }
}
