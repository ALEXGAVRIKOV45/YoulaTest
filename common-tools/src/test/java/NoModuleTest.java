import io.qameta.allure.Feature;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Feature("Загрузка сервиса автопроверки")
public class NoModuleTest {

    @Test(description = "Проверка модуля с домашним заданием")
    public void noModuleTest() {
        String moduleName = System.getProperty("moduleName");
        Assert.fail("Отсутствует модуль проверки задания " + moduleName + ". Обратитесь к преподавателю.");
    }
}
