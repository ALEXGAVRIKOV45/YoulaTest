package widgetobjects;

import com.codeborne.selenide.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;
import static java.time.Duration.*;

public class MainPage {
    static String buttonListCategories = "//a[text()='%1$s']",
            buttonSwitch = "//h2[text() = '%1$s']",
            productList = "//div[@data-test-component='ProductOrAdCard']//a[contains(@rel,'noreferrer')]";

    public static SelenideElement buttonCategories = $x("//button[@data-test-action='CategoriesClick']");
    public static SelenideElement buttonSearch = $x("//button[@data-test-action='SearchSubmit']");
    public static SelenideElement inputSearch = $x("//input[@type='text' and contains(@placeholder,'Поиск')]");

//    public static ElementsCollection productList = $$x("//div[@data-test-component='ProductOrAdCard']//a[contains(@rel,'noreferrer')]");

    public static void open(String page) {
        String baseUrl = page;
        Selenide.open(baseUrl);
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    public static void clickCategories(String categories) {
        $x(format(buttonListCategories, categories)).shouldBe(visible).click();
    }

    public static void clickSwitch(String name) {
        $x(format(buttonSwitch, name)).shouldBe(visible).click();
    }

    public static void clickProductList(int index) {
        SelenideElement productSearch = $$x("//div[@data-test-component='ProductOrAdCard']//a[contains(@rel,'noreferrer')]").get(index-1);
        productSearch.shouldBe(visible).click();
    }

}

