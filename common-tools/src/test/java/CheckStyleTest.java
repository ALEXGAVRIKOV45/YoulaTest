import io.qameta.allure.Feature;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Feature("Проверки стиля кода")
public class CheckStyleTest {

    @Test(description = "Проверка стиля кода")
    public void checkStyleTest() {
        String currentDir = System.getProperty("user.dir");
        String fileName = new File(currentDir) + File.separator + "git.log";
        String message;
        try {
            message = FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            message = "Неизвестная ошибка проверки стиля кода";
        }
        String ruMessage = "Ошибка проверки стиля кода. Logs:";
        Assert.fail(ruMessage + "\n" + message);
    }
}
