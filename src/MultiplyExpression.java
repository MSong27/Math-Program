public class MultiplyExpression implements Expression {
    private Expression left;
    private Expression right;

    MultiplyExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public double evaluate(double x) {
        return left.evaluate(x) * right.evaluate(x);
    }
}
