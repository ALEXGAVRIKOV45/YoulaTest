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

@Feature("Проверка файлов проекта")
public class ProjectTest {

    private String moduleDir;
    private String sourceDir;
    private String resourceDir;

    @BeforeClass
    public void init() {
        String currentDir = System.getProperty("user.dir");
        moduleDir = new File(currentDir).getParent() + File.separator + "homework2";
        sourceDir = moduleDir + File.separator + "src" + File.separator + "main" + File.separator + "java";
        resourceDir = moduleDir + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    }

    @Test(description = "Проверка корневой директории")
    public void testRoot() {
        shouldHaveRoot();
    }

    @Step("Проверка директории src/main/java")
    private void shouldHaveRoot() {
        Path javaPath = Paths.get(sourceDir);
        Assert.assertTrue(Files.exists(javaPath), "В проекте не найден пакет src/main/java");
    }

    @Test(description = "Проверка пакетов")
    public void testDirs() {
        String[] packages = {"animals", "employee", "food"};
        for (String aPackage : packages) {
            shouldHavePackage(aPackage);
        }
    }

    @Step("Проверка пакета src/main/java/{aPackage}")
    private void shouldHavePackage(String packageName) {
        String aPackage = sourceDir + File.separator + packageName;
        Path packagePath = Paths.get(aPackage);
        Assert.assertTrue(Files.exists(packagePath), "В проекте не найден пакет src/main/java/" + packageName);
    }

    @Test(description = "Проверка файлов исходного кода")
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
                "Zoo.java"
        };
        for (String file : files) {
            shouldHaveFile(file);
        }
    }

    @Step("Проверка файла {file}")
    private void shouldHaveFile(String file) {
        file = sourceDir + File.separator + file.replace("/", File.separator);
        Path filePath = Paths.get(file);
        Assert.assertTrue(Files.exists(filePath), "В проекте не найден файл " + file);
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
    public void testExcludes(String gitignoreName) {
        shouldHaveNotRegEx(moduleDir, gitignoreName);
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

//    @Test(description = "Проверка файлов проекта")
//    public void testIncludes() {
//        String file = moduleDir + File.separator + ".gitignore";
//        Path filePath = Paths.get(file);
//        Assert.assertTrue(Files.exists(filePath),
//                "В проекте не найден файл " + file.split(Paths.get(moduleDir).getParent().getFileName().toString())[1]);
//    }
}
