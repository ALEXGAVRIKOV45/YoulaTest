import animals.Carnivorous;
import animals.Herbivore;
import framework.AssertReflection;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.Aviary;
import model.WrongSizeException;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

@Feature("Проверка кода (Java Homework 3)")
public class ZooTest {

    @DataProvider
    public Object[][] fields() {
        return new Object[][]{
                {"carnivorousAviary", Aviary.class, Modifier.PRIVATE},
                {"herbivoreAviary", Aviary.class, Modifier.PRIVATE}
        };
    }

    @Story("Проверка кода в классе Zoo")
    @Test(description = "Проверка переменных в классе Zoo", dataProvider = "fields", timeOut = 60_000)
    public void testFields(String fieldName, Class type, int mod) {
        Field field = AssertReflection.shouldHaveAndGetField(Zoo.class, fieldName);
        Assert.assertEquals(String.format("Поле %s должно иметь тип %s", fieldName, type.getSimpleName()), type, field.getType());
        AssertReflection.isPrivate(field);
        AssertReflection.isStatic(field);
    }

    @DataProvider
    public Object[][] fillMethods() {
        return new Object[][]{
                {"fillCarnivorousAviary", void.class, "carnivorousAviary"},
                {"fillHerbivoreAviary", void.class, "herbivoreAviary"}
        };
    }

    @Story("Проверка кода в классе Zoo")
    @Test(description = "Проверка заполнения вольера", dataProvider = "fillMethods", timeOut = 60_000)
    public void testMethods(String methodName, Class returnType, String fieldName) throws IllegalAccessException {
        Method method = AssertReflection.shouldHaveAndGetMethod(Zoo.class, methodName);
        AssertReflection.returnType(method, returnType);
        Field field = AssertReflection.shouldHaveAndGetField(Zoo.class, fieldName);

        Zoo zoo = (Zoo) getInstance(Zoo.class);
        field.setAccessible(true);
        Aviary o = (Aviary) field.get(zoo);

        Field aviaryMapField = AssertReflection.shouldHaveAndGetField(Aviary.class, "aviaryMap");
        aviaryMapField.setAccessible(true);
        HashMap map = (HashMap) aviaryMapField.get(o);
        method.setAccessible(true);

        boolean thrown = false;
        try {
            method.invoke(zoo);
        }catch (WrongSizeException | InvocationTargetException e) {
            thrown = true;
        }
        boolean sizeIsChanged = map.size() > 0;
        Assert.assertTrue(
                String.format("Метод %s не выбросил исключение или размер коллекции %s не увеличился в размере", methodName, fieldName),
                thrown || sizeIsChanged);
    }

    @DataProvider
    public Object[][] methods() {
        return new Object[][]{
                {"getCarnivorous", Carnivorous.class, String.class},
                {"getHerbivore", Herbivore.class, String.class}
        };
    }

    @Test(description = "Проверка методов", dataProvider = "methods", timeOut = 60_000)
    private void testMethods(String methodName, Class returnType, Class param) {
        Method method = AssertReflection.shouldHaveAndGetMethod(Zoo.class, methodName, param);
        AssertReflection.returnType(method, returnType);
    }

    private Object getInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Не удалось создать экземпляр класса " + clazz.getSimpleName() + ".\n" + e.getMessage());
        }
    }
}
