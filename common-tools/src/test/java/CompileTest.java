import io.qameta.allure.Feature;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Feature("Проверки компиляции проекта")
public class CompileTest {

    @Test(description = "Проверка компиляции проекта")
    public void compileTest() {
        String currentDir = System.getProperty("user.dir");
        String workDir = new File(currentDir).getParent() + File.separator + "mvn.log";
        String message;
        try {
            message = FileUtils.readFileToString(new File(workDir), StandardCharsets.UTF_8);
        } catch (IOException e) {
            message = "Неизвестная ошибка компиляции";
        }
        String ruMessage = "Ошибка компиляции программы. Возможные причины:\n" +
                "- выражение package указывает путь к классу не от src/main/java\n";
        Assert.fail(ruMessage + "\n" + message);
    }
}
