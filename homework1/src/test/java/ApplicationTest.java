import animals.Kotik;
import framework.AssertReflection;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

@Feature("Проверка кода (Java Homework 1)")
@Story("Проверка кода класса Application")
public class ApplicationTest {
    @DataProvider
    public Object[][] methods() {
        return new Object[][]{
                {"main", void.class, new Class[]{String[].class}},
                {"compareVoice", boolean.class, new Class[]{Kotik.class, Kotik.class}},
        };
    }

    @Test(description = "Проверка методов", dataProvider = "methods", timeOut = 60_000)
    private void testMethods(String methodName, Class returnType, Class[] param) {
        Method method = AssertReflection.shouldHaveAndGetMethod(Application.class, methodName, param);
        AssertReflection.returnType(method, returnType);
    }
}
