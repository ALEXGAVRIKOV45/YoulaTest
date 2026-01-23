import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@Feature("Проверка файлов проекта (Java Homework 1)")
public class ProjectTest {

    private String workDir;
    private String javaDir;

    @BeforeClass
    public void init() {
        String currentDir = System.getProperty("user.dir");
        workDir = new File(currentDir).getParent() + File.separator + "homework1";
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
        shouldHaveAnimals();
    }

    @Step("Проверка пакета src/main/java/animals")
    private void shouldHaveAnimals() {
        String animals = javaDir + File.separator + "animals";
        Path animalsPath = Paths.get(animals);
        Assert.assertTrue(Files.exists(animalsPath), "В проекте не найден пакет src/main/java/animals");
    }

    @Test(description = "Проверка файлов")
    public void testFiles() {
        shouldHaveApp();
        shouldHaveKotik();
    }

    @Step("Проверка файла Application.java")
    private void shouldHaveApp() {
        String app = javaDir + File.separator + "Application.java";
        Path appPath = Paths.get(app);
        Assert.assertTrue(Files.exists(appPath), "В проекте не найден файл src/main/java/Application.java");
    }

    @Step("Проверка файла Kotik.java")
    private void shouldHaveKotik() {
        String kotik = javaDir + File.separator + "animals" + File.separator + "Kotik.java";
        Path kotikPath = Paths.get(kotik);
        Assert.assertTrue(Files.exists(kotikPath), "В проекте не найден файл src/main/java/animals/Kotik.java");
    }

    @DataProvider
    public Object[][] exclude() {
        return new Object[][]{
                {".idea"},
                {"out"},
                {"*.iml"},
        };
    }

    @Test(dataProvider = "exclude", description = "Проверка посторонних файлов")
    public void testExcludes(String name) {
        shouldHaveNotRegEx(workDir, name);
    }

    @Step("Проверка отсутствия папки/файла '{name}'")
    private void shouldHaveNotRegEx(String workDir, String gitignoreRegEx) {
        String regex = gitignoreRegEx.replace(".", "\\.").replace("*", ".*");
        File root = new File(workDir);
        File[] list = root.listFiles();
        if (list == null || list.length == 0) {
            throw new AssertionError("В проекте отсутствуют файлы");
        }
        for (File file : list) {
            Assert.assertFalse(Pattern.compile(regex).matcher(file.getName()).find(), "На удаленном репозитории не должно быть файла/папки '" + gitignoreRegEx + "'.");
        }
    }
}
