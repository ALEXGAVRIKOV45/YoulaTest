package widgetobjects;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static java.lang.String.format;

public class ProductPage {

    static String textProductCategories = "//dt[text()='%1$s']/following-sibling::dd";

    public static SelenideElement textSaveDeal = $x("//div[@data-test-block='PaymentOption' and ./span[text()='Безопасная сделка']]");

    public static String getProductCategories(String categories) {
        return $x(format(textProductCategories, categories)).shouldBe(visible).getText();
    }
    public static void checkTextCategories(String categories, String text) {
        if (!$$x(format(textProductCategories, categories)).isEmpty())
            Assertions.assertTrue(getProductCategories(categories).contains(text));
    }
}
