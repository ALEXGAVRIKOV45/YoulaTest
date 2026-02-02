import framework.AssertReflection;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Scanner;

@Feature("Автоматизированная проверка калькулятора")
public class MainAutoTest {

    private static final double ACCURACY = 0.001;

    @Story("Проверка метода main")
    @Test
    public void mainTest() {
        double expected = 5;

        String input = "+\n2\n3\n";
        InputStream is = new ByteArrayInputStream(input.getBytes());
        System.setIn(is);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        try {
            Method main = AssertReflection.shouldHaveAndGetMethod(Class.forName("Main"), "main", String[].class);
            main.invoke(null, (Object) null);

            Scanner scanner = new Scanner(System.in);
            Assert.assertFalse(scanner.hasNextLine(), "Из консоли не были считаны данные");
            scanner.close();

            System.out.flush();
            System.setOut(old);
        } catch (Exception e) {
            Assert.fail("Ошибка в методе main: " + e.getMessage());
        }

        Assert.assertEquals(expected, Double.parseDouble(baos.toString()), ACCURACY, "Результат выполнения метода main не соответствует ожидаемому");
    }
}
