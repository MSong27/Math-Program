public class ExponentExpression implements Expression{
    private Expression base;
    private Expression exponent;

    ExponentExpression(Expression base, Expression exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    public double evaluate(double x) {
        return Math.pow(this.base.evaluate(x), this.exponent.evaluate(x));
    }
}
