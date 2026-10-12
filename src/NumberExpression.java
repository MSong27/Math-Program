public class NumberExpression implements Expression {
    private double value;


    NumberExpression(double value) {
        this.value = value;
    }
    public double evaluate(double x) {
        return this.value;
    }
}
