import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.Size;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Feature("Проверка кода (Java Homework 3)")
@Story("Проверка класса Size")
public class SizeTest {

    @DataProvider
    public Object[][] sizeValues() {
        return new Object[][]{
                {"SMALL"},
                {"MEDIUM"},
                {"LARGE"}
        };
    }

    @Test(description = "Проверка количества значений в Enum Size", timeOut = 60_000)
    public void testValuesCount() {
        Assert.assertEquals(Size.values().length, 3, "Количество значений в Size должно быть равно 3");
    }

    @Test(dataProvider = "sizeValues", description = "Проверка значений в Enum Size", timeOut = 60_000)
    public void testValueOf(String value) {
        try {
            Size.valueOf(value);
        } catch (IllegalArgumentException ex) {
            Assert.fail("В Enum 'Size' на найдено значение: " + value);
        }
    }
}