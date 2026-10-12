public class SubtractExpression implements Expression {
    private Expression left;
    private Expression right;

    SubtractExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public double evaluate(double x) {
        return left.evaluate(x) - right.evaluate(x);
    }
}
