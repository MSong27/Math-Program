import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Vector {
    private double[] components;
    private int dimension;

    public Vector(int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("Vectors must be dimension 1 or higher");
        }
        this.dimension = dimension;
        this.components = new double[dimension];
    }

    public void setComponents(double... components) {
        int i = 1;
        for (double component : components) {
            this.components[i - 1] = component;
            i++;
        }
    }

    public void setComponents(Vector vector) {
        for (int i = 1 ; i <= vector.getDimension() ; i++) {
            this.setComponent(i, vector.getComponent(i));
        }
    }

    public void setComponent(int componentIndex, double componentValue) {
        this.components[componentIndex - 1] = componentValue;
    }

    public int getDimension() {
        return this.dimension;
    }

    public double getComponent(int i) {
        return this.components[i - 1]; //1 based indexing
    }

    @Override
    public String toString() {
        StringBuilder vector = new StringBuilder();
        double initialValue = this.components[0] + 0.0000;
        if (Math.abs(initialValue) < Matrix.epsilon && initialValue < 0) {
            initialValue *= -1;
        }
        vector.append("<" + String.format("%.4f", initialValue));
        for (int i = 1 ; i < this.dimension ; i++) {
            double value = this.components[i] + 0.0000;
            if (Math.abs(value) < Matrix.epsilon && value < 0) {
                value *= -1;
            }
            vector.append(", " + String.format("%.4f", value));
        }
        vector.append(">");
        return vector.toString();
    }

    public Vector add(Vector other) {
        if (this.dimension != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must be the same dimension");
        }
        Vector sum = new Vector(this.dimension);
        for (int i = 1 ; i <= sum.getDimension() ; i++) {
            sum.setComponent(i, this.getComponent(i) + other.getComponent(i));
        }
        return sum;
    }

    public Vector scale(double scalar) {
        Vector scaledVector = new Vector(this.dimension);
        for (int i = 1 ; i <= scaledVector.getDimension() ; i++) {
            scaledVector.setComponent(i, this.getComponent(i) * scalar);
        }
        return scaledVector;
    }

    public Vector subtract(Vector other) {
        Vector difference = new Vector(this.dimension);
        difference.add(other.scale(-1));
        return difference;
    }

    //Only works if matrix is solvable
    public Matrix findLinearCombination(Vector[] vectors) {
        for(Vector vector : vectors) {
            if (vector.getDimension() != this.getDimension()) {
                throw new IllegalArgumentException("Vectors must be same dimensionality");
            }
        }
        Matrix matrix = new Matrix(vectors.length, this.getDimension() + 1);
        for(int i = 1 ; i <= vectors.length ; i++) {
            for (int j = 1 ; j <= matrix.getColumn() - 1 ; j++) {
                matrix.setEntries(j, i, vectors[i - 1].getComponent(j));
            }
        }
        for (int k = 1 ; k <= this.getDimension() ; k++) {
            matrix.setEntries(k, matrix.getColumn(), this.getComponent(k));
        }
        return matrix.solveEquation();
    }

    public static boolean isLinearlyIndependent(ArrayList<Vector> vectorSet) {
        Matrix matrix = new Matrix(vectorSet.size(), vectorSet.getFirst().getDimension() + 1);
        for(int i = 1 ; i <= vectorSet.size() ; i++) {
            for (int j = 1 ; j <= matrix.getColumn() - 1 ; j++) {
                matrix.setEntries(j, i, vectorSet.get(i - 1).getComponent(j));
            }
        }
        for (int k = 1 ; k <= vectorSet.getFirst().getDimension() ; k++) {
            matrix.setEntries(k, matrix.getColumn(), 0);
        }
        return matrix.gaussJordan().countLeadingOnes() == vectorSet.getFirst().getDimension();
    }

    public static ArrayList<Vector> findBasisOfRowSpace(ArrayList<Vector> vectorSet) {
        Matrix matrix = new Matrix(vectorSet.size(), vectorSet.getFirst().getDimension());
        ArrayList<Vector> basisOfRowSpace = new ArrayList<>();
        for(int i = 1 ; i <= vectorSet.size() ; i++) {
            for (int j = 1 ; j <= matrix.getColumn() ; j++) {
                matrix.setEntries(i, j, vectorSet.get(i - 1).getComponent(j));
            }
        }
        matrix.setEntries(matrix.gaussJordan());
        for(int i = 1 ; i <= matrix.getRow() ; i++) {
           if (!matrix.rowIsZero(i, 1)) {
               Vector vector = new Vector(vectorSet.getFirst().getDimension());
               for (int j = 1 ; j <= matrix.getColumn() ; j++) {
                   vector.setComponent(j, matrix.getEntry(i, j));
               }
               basisOfRowSpace.add(vector);
           }
        }
        return basisOfRowSpace;
    }
    public static ArrayList<Vector> findBasisOfColumnSpace(ArrayList<Vector> vectorSet) {
        Matrix matrix = new Matrix(vectorSet.size(), vectorSet.getFirst().getDimension());
        Matrix transposedMatrix = new Matrix(vectorSet.getFirst().getDimension(), vectorSet.size());
        ArrayList<Vector> basisOfColumnSpace = new ArrayList<>();
        for(int i = 1 ; i <= vectorSet.size() ; i++) {
            for (int j = 1 ; j <= matrix.getColumn() ; j++) {
                matrix.setEntries(i, j, vectorSet.get(i - 1).getComponent(j));
            }
        }
        transposedMatrix.setEntries(matrix.transpose().gaussJordan());
        for(int i = 1 ; i <= transposedMatrix.getRow() ; i++) {
            if (!transposedMatrix.rowIsZero(i, 1)) {
                Vector vector = new Vector(transposedMatrix.getColumn());
                for (int j = 1 ; j <= transposedMatrix.getColumn() ; j++) {
                    vector.setComponent(j, transposedMatrix.getEntry(i, j));
                }
                basisOfColumnSpace.add(vector);
            }
        }
        return basisOfColumnSpace;
    }

    public double findLength() {
        double sum = 0;
        for (int i = 1 ; i <= this.getDimension() ; i++) {
            sum+= this.getComponent(i) * this.getComponent(i);
        }
        return Math.pow(sum, 0.5);
    }

    public boolean isUnitVector() {
        return this.findLength() == 1;
    }

    public Vector findUnitVector() {
        Vector unitVector = new Vector(this.getDimension());
        double magnitude = this.findLength();
        for (int i = 1 ; i <= this.getDimension() ; i++) {
            this.setComponent(i, this.getComponent(i) / magnitude);
        }
        return unitVector;
    }

    public double findDistanceBetweenVectors(Vector other) {
        if (this.getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must be same dimension");
        }
        Vector difference = new Vector(this.getDimension());
        for (int i = 1 ; i <= difference.getDimension() ; i++) {
            difference.setComponent(i, this.getComponent(i) - other.getComponent(i));
        }
        return difference.findLength();
    }

    public static double dotProduct(Vector vectorOne, Vector vectorTwo) {
        if (vectorOne.getDimension() != vectorTwo.getDimension()) {
            throw new IllegalArgumentException("Vectors must be same dimension");
        }
        double dotProduct = 0;
        for (int i = 1 ; i <= vectorOne.getDimension() ; i++) {
             dotProduct += vectorOne.getComponent(i) *vectorTwo.getComponent(i);
        }
        return dotProduct;
    }

    public boolean checkIfOrthogonal(Vector vectorOne, Vector vectorTwo) {
        return Vector.dotProduct(vectorOne, vectorTwo) == 0;
    }

    public static double findScalarProjection(Vector vectorOne, Vector vectorTwo) {
        double projection = 0;
        projection = Vector.dotProduct(vectorOne, vectorTwo);
        projection *= 1 / Vector.dotProduct(vectorTwo, vectorTwo);
        return projection;
    }

    public static Vector findVectorProjection(Vector vectorOne, Vector vectorTwo) {
        Vector vectorProjection = new Vector(vectorTwo.getDimension());
        vectorProjection.setComponents(vectorTwo);
        vectorProjection.scale(Vector.findScalarProjection(vectorOne, vectorTwo));
        return vectorProjection;
    }
}
