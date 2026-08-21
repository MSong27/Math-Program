public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix(3, 3);
        Matrix n = new Matrix(1, 3);
        double[][] entryOne = {
                {-3, 5, 2},
                {2, -4,-1},
                {-3, 0, 6}

        };
        double[][] entryTwo = {
                {-1, 2, 5},
        };
        m.setEntries(entryOne);
        n.setEntries(entryTwo);
        System.out.println(m.calculateDeterminant());
        System.out.println(m.gaussianElimination());
        System.out.print(Matrix.identity(5));
    }
}
