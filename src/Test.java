import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Matrix test = new Matrix(4, 4);
        Matrix L = new Matrix(4, 1);
        Matrix U = new Matrix(3, 3);
        double[][] entryOne = {
                {0, 1, 1, -2},
                {1, 2, -1, 0},
                {2, 4, 1, -3},
                {1, -4, -7, -1}

        };
        double[][] entryTwo = {
                {-3},
                {2},
                {-2},
                {-19}
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
        Vector vTest = new Vector(5);
        vTest.setComponents(0, -2, 1, 4, -2);
        System.out.println(vTest.findLength());
    }
}
