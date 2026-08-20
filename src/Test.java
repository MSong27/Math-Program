public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix(3, 1);
        Matrix n = new Matrix(1, 3);
        double[][] entryOne = {
                {1},
                {3},
                {1}
        };
        double[][] entryTwo = {
                {-1, 2, 5},
        };
        m.setEntries(entryOne);
        n.setEntries(entryTwo);
        System.out.println(m.swap(1, 2));
        System.out.print(Matrix.identity(5));
    }
}
