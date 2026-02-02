import animals.Fly;
import animals.Run;
import animals.Swim;
import animals.Voice;
import framework.AssertReflection;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

@Feature("Проверка кода (Java Homework 2)")
@Story("Проверка интерфейсов")
public class InterfaceTest {

    @DataProvider
    public Object[][] interfaces() {
        return new Object[][]{
                {Fly.class, "fly"},
                {Run.class, "run"},
                {Swim.class, "swim"},
                {Voice.class, "getVoice"}
        };
    }

    @DataProvider
    public Object[][] interfacesAndMethods() {
        return new Object[][]{
                {Voice.class, "getVoice", String.class}
        };
    }

    @Test(dataProvider = "interfaces", description = "Проверка методов интерфейсов", timeOut = 60_000)
    public void testInterfaces(Class clazz, String methodName) {
        AssertReflection.shouldHaveAndGetMethod(clazz, methodName);
    }

    @Test(dataProvider = "interfacesAndMethods", description = "Проверка типа возвращаемого значения метода интерфейса", timeOut = 60_000)
    public void testReturnTypeForInterfaceMethod(Class clazz, String methodName, Class<?> returnType) {
        Method getMethod = AssertReflection.shouldHaveAndGetMethod(clazz, methodName);
        Assert.assertEquals(getMethod.getGenericReturnType(), returnType, "Тип возвращаемого значения не соответствует ожидаемому");
    }
}
