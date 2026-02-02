import model.Calculator;
import model.CalculatorException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** Для отладки, не участвует в автопрвоерке */
public class NegativeCalculatorTest {
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

    @Test(enabled = false, dataProvider = "negativeData", expectedExceptions = CalculatorException.class)
    public void negativeTest(String operator, String v1, String v2) {
        System.out.println(Calculator.execute(new String[]{operator, v1, v2}));
    }
}
