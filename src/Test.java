public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix(3, 3);
        Matrix n = new Matrix(2, 4);
        double[][] entryOne = {
                {1, 2, 0},
                {3, -1, 2},
                {-2, 3, -2}

        };
        double[][] entryTwo = {
                {1.0, 1.0, 1.0, 0.0},
                {2.0, 4.0, 0.0, 1.0}
        };
        m.setEntries(entryOne);
        n.setEntries(entryTwo);
        System.out.println(m.findInverse());
        System.out.print(n.gaussJordan());
    }
}
