import io.qameta.allure.Feature;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Feature("Проверки загрузки проекта")
public class LoadingTest {

    @Test(description = "Проверка загрузки проекта")
    public void loadingTest() {
        String currentDir = System.getProperty("user.dir");
        String fileName = new File(currentDir) + File.separator + "git.log";
        String message;
        try {
            message = FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            message = "Неизвестная ошибка загрузки проекта";
        }
        String ruMessage = "Ошибка при чтении репозитория. Проверьте следующее:\n" +
                "Для проектов на gitlab:\n" +
                "- выдан доступ к проекту для учетной записи системы автопроверки, с ролью Developer или выше;\n" +
                "Для проектов на github:\n" +
                "- выдан доступ к проекту для учетной записи системы автопроверки;\n" +
                "- преподаватель подтвердил запрос.\n";
        Assert.fail(ruMessage + "\n" + message);
    }
}
