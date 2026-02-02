import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Feature("Проверка файлов проекта")
public class ProjectTest {

    private String workDir;
    private String javaDir;

    @BeforeClass
    public void init() {
        String currentDir = System.getProperty("user.dir");
        workDir = new File(currentDir).getParent() + File.separator + "homework3";
        javaDir = workDir + File.separator + "src" + File.separator + "main" + File.separator + "java";
    }

    @Test(description = "Проверка корневой директории")
    public void testRoot() {
        shouldHaveRoot();
    }

    @Step("Проверка директории src/main/java")
    private void shouldHaveRoot() {
        Path javaPath = Paths.get(javaDir);
        Assert.assertTrue(Files.exists(javaPath), "В проекте не найден пакет src/main/java");
    }

    @Test(description = "Проверка пакетов")
    public void testDirs() {
        String[] packages = {"animals", "employee", "food", "model"};
        for (String aPackage : packages) {
            shouldHavePackage(aPackage);
        }
    }

    @Step("Проверка пакета src/main/java/{aPackage}")
    private void shouldHavePackage(String packageName) {
        String aPackage = javaDir + File.separator + packageName;
        Path packagePath = Paths.get(aPackage);
        Assert.assertTrue(Files.exists(packagePath), "В проекте не найден пакет src/main/java/" + packageName);
    }

    @Test(description = "Проверка файлов")
    public void testFiles() {
        String[] files = {
                "animals/Animal.java",
                "animals/Carnivorous.java",
                "animals/Duck.java",
                "animals/Fish.java",
                "animals/Fly.java",
                "animals/Herbivore.java",
                "animals/Kotik.java",
                "animals/Run.java",
                "animals/Swim.java",
                "animals/Voice.java",
                "employee/Worker.java",
                "food/Food.java",
                "food/Grass.java",
                "food/Meat.java",
                "food/WrongFoodException.java",
                "model/Aviary.java",
                "model/Size.java",
                "model/WrongSizeException.java",
                "Zoo.java"
        };
        for (String file : files) {
            shouldHaveFile(file);
        }
    }

    @Step("Проверка файла {file}")
    private void shouldHaveFile(String file) {
        file = javaDir + File.separator + file.replace("/", File.separator);
        Path filePath = Paths.get(file);
        Assert.assertTrue(Files.exists(filePath), "В проекте не найден файл " + file);
    }
}
