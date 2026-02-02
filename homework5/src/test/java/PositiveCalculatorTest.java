import model.Calculator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** Для отладки, не участвует в автопрвоерке */
public class PositiveCalculatorTest {
    private static final double ACCURACY = 0.001;

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {"+", "2", "3", 5},
                {"-", "-0.0", "-2.4", 2.4},
                {"*", "2", "4.8", 9.6},
                {"/", "2", "3", 0.667},
        };
    }

    @Test(enabled = false, dataProvider = "positiveData")
    public void positiveTest(String operator, String v1, String v2, double expected) {
        String result = Calculator.execute(new String[]{operator, v1, v2});
        double actual = Double.parseDouble(result);
        Assert.assertEquals(actual, expected, ACCURACY,
                String.format("Ошибка вычисления\nExpected: %s\nActual: %s\nAccuracy: %s",
                        String.valueOf(expected), result, String.valueOf(ACCURACY))
        );
    }
}