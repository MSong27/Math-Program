import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Matrix test = new Matrix(2, 2);
        Matrix L = new Matrix(3, 3);
        Matrix U = new Matrix(3, 3);
        double[][] entryOne = {
                {2,1},
                {4, 2}

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
        test.setEntries(entryOne);
        System.out.print(Matrix.findDeterminant(test));
    }
}
