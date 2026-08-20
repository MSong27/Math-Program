public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix(2, 2);
        Matrix n = new Matrix(2, 2);
        double[][] entry = {
                {5, 6},
                {7, 3},
        };
        m.setEntries(1, 1, 1);
        m.setEntries(1, 2, 2);
        m.setEntries(2, 2, 4);
        m.scale(2);
        System.out.println(m);
        System.out.println(n);
    }
}
