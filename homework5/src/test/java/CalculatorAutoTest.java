import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.Calculator;
import model.CalculatorException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Feature("Автоматизированная проверка калькулятора")
public class CalculatorAutoTest {

    private static final double ACCURACY = 0.001;

    @DataProvider
    public Object[][]positiveData() {
        return new Object[][]{
                {"+", "2", "3", 5},
                {"-", "-0.0", "-2.4", 2.4},
                {"*", "2", "4.8", 9.6},
                {"/", "2", "3", 0.667},
        };
    }

    @Story("Позитивные проверки")
    @Test(dataProvider = "positiveData")
    public void positiveTest(String operator, String v1, String v2, double expected) {
        String result = Calculator.execute(new String[] {operator, v1, v2});
        double actual = Double.parseDouble(result);
        Assert.assertEquals(actual, expected, ACCURACY,
                String.format("Ошибка вычисления\nExpected: %s\nActual: %s\nAccuracy: %s",
                        String.valueOf(expected), result, String.valueOf(ACCURACY))
        );
    }

    @DataProvider
    public Object[][] negativeData() {
        return new Object[][]{
                {"/", "1", "0"},
                {"/", "-1.1", "-0.0"},
                {"+", "", ""},
                {"-", "one", "two"},
                {"plus", "1", "1"},
                {"+", String.valueOf(Integer.MAX_VALUE), "1"},
                {"+", String.valueOf(Integer.MIN_VALUE), "-1"},
        };
    }

    @Story("Негативные проверки")
    @Test(dataProvider = "negativeData", expectedExceptions = CalculatorException.class)
    public void negativeTest(String operator, String v1, String v2) {
        System.out.println(Calculator.execute(new String[] {operator, v1, v2}));
    }
}
