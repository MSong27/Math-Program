import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Matrix P = new Matrix(3, 3);
        Matrix L = new Matrix(3, 3);
        Matrix U = new Matrix(3, 3);
        Matrix test = new Matrix(3, 3);
        double[][] entryOne = {
                {0, 0, 1},
                {1, 0, 0},
                {0, 1, 0}

        };
        double[][] entryTwo = {
                {1, 0, 0},
                {0, 1, 0},
                {2, -4, 1}
        };
        double[][] entryThree= {
                {1, -3, 0},
                {0, 1, 3},
                {0, 0, 14}
        };
        double[][] entryFour= {
                {1, -3, 0},
                {0, 1, 3},
                {2, -10, 2}
        };
        test.setEntries(entryFour);
        P.setEntries(entryOne);
        L.setEntries(entryTwo);
        U.setEntries(entryThree);
        System.out.println(Arrays.toString(Matrix.luDecomposition(test)));
        System.out.print(L.multiply(U));
    }
}
