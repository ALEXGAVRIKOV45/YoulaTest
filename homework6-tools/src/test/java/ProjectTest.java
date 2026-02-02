import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
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

    private static final String MODULE = "homework6";

    private String moduleDir;
    private String sourceDir;
    private String resourceDir;
    private String testDir;
    private String testResourcesDir;
    private String rootDirName;

    @BeforeClass
    public void init() {
        String currentDir = System.getProperty("user.dir");
        moduleDir = new File(currentDir).getParent() + File.separator + MODULE;
        sourceDir = moduleDir + File.separator + "src" + File.separator + "main" + File.separator + "java";
        resourceDir = moduleDir + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        testDir = moduleDir + File.separator + "src" + File.separator + "test" + File.separator + "java";
        testResourcesDir = moduleDir + File.separator + "src" + File.separator + "test" + File.separator + "resources";
        rootDirName = Paths.get(moduleDir).getParent().getFileName().toString();
    }

    @DataProvider
    public Object[][] source() {
        return new Object[][]{
                {"model/Status.java"},
                {"model/Ticket.java"},
        };
    }

    @Story("Проверка файлов исходного кода")
    @Test(dataProvider = "source", description = "Проверка файла")
    public void testSource(String file) {
        shouldHaveFile(sourceDir, file);
    }

    @DataProvider
    public Object[][] tests() {
        return new Object[][]{
                {"api/BaseTest.java"},
                {"api/CreateTicketTest.java"},
                {"api/UpdateTicketTest.java"},
        };
    }

    @Story("Проверка тестовых файлов")
    @Test(dataProvider = "tests", description = "Проверка файла")
    public void testTest(String file) {
        shouldHaveFile(testDir, file);
    }

    @DataProvider
    public Object[][] testsResources() {
        return new Object[][]{
                {"config.properties"},
        };
    }

    @Story("Проверка тестовых файлов")
    @Test(dataProvider = "testsResources", description = "Проверка тестовых ресурсов")
    public void testResourcesTest(String file) {
        shouldHaveFile(testResourcesDir, file);
    }

    @Step("Проверка файла {file}")
    private void shouldHaveFile(String dir, String file) {
        file = dir + File.separator + file.replace("/", File.separator);
        Path filePath = Paths.get(file);
        Assert.assertTrue(Files.exists(filePath), "В проекте не найден файл " + file.split(rootDirName)[1]);
    }

    @DataProvider
    public Object[][] include() {
        return new Object[][]{
                {".gitignore"},
                {"pom.xml"},
        };
    }

    @Story("Проверка файлов проекта")
    @Test(dataProvider = "include", description = "Проверка файла")
    public void testIncludes(String fileName) {
        String file = moduleDir + File.separator + fileName;
        Path filePath = Paths.get(file);
        Assert.assertTrue(Files.exists(filePath), "В проекте не найден файл " + fileName);
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

}
