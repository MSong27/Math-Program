import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Expression test = new MultiplyExpression(new AddExpression(new VariableExpression(), new NumberExpression(1)),
                (new AddExpression(new VariableExpression(), new NumberExpression(3))));

        System.out.println(test.evaluate(2));
    }
}
