
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import widgetobjects.MainPage;
import widgetobjects.ProductPage;

import java.util.logging.*;

import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;

public class UItests {

    String baseUrl = "https://youla.ru/";
    private final Logger logger = LoggerFactory.getLogger(UItests.class.getName());

    @BeforeAll
    static void configureTests() {
        Configuration.timeout = 6000;
    }

    @Test
    public void checkCategories() {
        MainPage.open(baseUrl);

        MainPage.buttonCategories.click();
        MainPage.clickCategories("Запчасти и автотовары");
        MainPage.clickCategories("Запчасти");
        MainPage.inputSearch.sendKeys("фара");
        MainPage.buttonSearch.click();
        MainPage.clickSwitch("Безопасная сделка");

        MainPage.clickProductList(1);
        switchTo().window(1);
        Assertions.assertTrue(ProductPage.textSaveDeal.shouldBe(Condition.visible).isDisplayed());
        ProductPage.checkTextCategories("Категория","Запчасти и автотовары");
        ProductPage.checkTextCategories("Подкатегория","Запчасти");
        ProductPage.checkTextCategories("Группа деталей","фара");
        logger.info(format("Название объявления - %s",ProductPage.textNameAdvert.getText()));
        logger.info(format("Цена объявления - %s",ProductPage.textPriceAdvert.getText()));
        logger.info(format("Описание - %s",ProductPage.getProductCategories("Описание")));
    }

    @AfterAll
    static void closeDrivers() {
        closeWebDriver();
    }
}
