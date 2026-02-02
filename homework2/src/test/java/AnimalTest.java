import animals.Animal;
import framework.AssertReflection;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Feature("Проверка кода (Java Homework 2)")
@Story("Проверка класса Animal")
public class AnimalTest {

    @Test(description = "Проверка переменных экземпляра", timeOut = 60_000)
    public void testFields() {
        String fieldName = "satiety";
        Field field = AssertReflection.shouldHaveAndGetField(Animal.class, fieldName);
        Assert.assertFalse(Modifier.isPublic(field.getModifiers()), "Поле " + fieldName + " не должно быть public");
    }

    @DataProvider
    public Object[][] methods() {
        return new Object[][]{
                {"getSatiety", int.class, new Class[]{}},
        };
    }

    @Test(description = "Проверка методов", dataProvider = "methods", timeOut = 60_000)
    private void testMethods(String methodName, Class returnType, Class[] param) {
        Method method = AssertReflection.shouldHaveAndGetMethod(Animal.class, methodName, param);
        AssertReflection.returnType(method, returnType);
    }

    @DataProvider
    public Object[][] excludeMethods() {
        return new Object[][]{
                {"getVoice"},
                {"run"},
                {"swim"},
                {"fly"},
        };
    }

    @Test(description = "Проверка отсутствия методов", dataProvider = "excludeMethods", timeOut = 60_000)
    private void testExcludeMethods(String methodName) {
        try {
            Animal.class.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            return;
        }
        Assert.fail("В классе Animal не должно быть метода: " + methodName);
    }
}
