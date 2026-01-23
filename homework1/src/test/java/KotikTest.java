import animals.Kotik;
import framework.AssertReflection;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Feature("Проверка кода (Java Homework 1)")
@Story("Проверка кода класса Kotik")
public class KotikTest {

    @DataProvider
    public Object[][] classInstanceFields() {
        return new Object[][]{
                {"name"},
                {"voice"},
                {"satiety"},
                {"weight"}
        };
    }

    @DataProvider
    public Object[][] classFields() {
        return new Object[][]{
                {"count"}
        };
    }

    @DataProvider
    public Object[][] classConstants() {
        return new Object[][]{
                {"METHODS"}
        };
    }

    @DataProvider
    public static Object[][] methods() {
        return new Object[][]{
                {"play"},
                {"sleep"},
                {"wash"},
                {"walk"},
                {"hunt"}
        };
    }

    @Test(description = "Проверка переменных экземпляра", dataProvider = "classInstanceFields", timeOut = 60_000)
    public void testFields(String fieldName) {
        testField(fieldName);
    }

    @Step("Проверка переменной {fieldName}")
    private void testField(String fieldName) {
        Field field = AssertReflection.shouldHaveAndGetField(Kotik.class, fieldName);
        if (field != null) {
            AssertReflection.isPrivate(field);
            AssertReflection.isNotStatic(field);
        }
    }

    @Test(description = "Проверка переменных класса", dataProvider = "classFields", timeOut = 60_000)
    public void testStaticFields(String fieldName) {
        testStaticField(fieldName);
    }

    @Step("Проверка переменной {fieldName}")
    private void testStaticField(String fieldName) {
        Field field = AssertReflection.shouldHaveAndGetField(Kotik.class, fieldName);
        if (field != null) {
            AssertReflection.isPrivate(field);
            AssertReflection.isStatic(field);
        }
    }

    @Test(description = "Проверка констант", dataProvider = "classConstants", timeOut = 60_000)
    public void testConstants(String fieldName) {
        testConstant(fieldName);
    }

    @Step("Проверка переменной {fieldName}")
    private void testConstant(String fieldName) {
        Field field = AssertReflection.shouldHaveAndGetField(Kotik.class, fieldName);
        if (field != null) {
            AssertReflection.isPrivate(field);
            AssertReflection.isStatic(field);
            AssertReflection.isFinal(field);
        }
    }

    @Test(description = "Проверка конструкторов", timeOut = 60_000)
    public void testConstructors() {
        testConstructorWithoutParams();
        testConstructorWithParams();
    }

    @Step("Проверка конструктора с параметрами")
    private void testConstructorWithParams() {
        try {
            getAllParamsConstructor();
        } catch (NoSuchMethodException e) {
            Assert.fail("Отсутствует конструктор c параметрами String name, String voice, int satiety, int weight");
        }
    }

    @Step("Проверка конструктора без параметров")
    private void testConstructorWithoutParams() {
        try {
            Kotik.class.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            Assert.fail("Отсутствует конструктор без параметров");
        }
    }

    @Test(description = "Проверка методов действий", dataProvider = "methods", timeOut = 60_000)
    public void testMethods(String methodName) {
        testMethod(methodName);
    }

    @Step("Проверка метода {methodName}")
    private void testMethod(String methodName) {
        Method method = AssertReflection.shouldHaveAndGetMethod(Kotik.class, methodName);
        AssertReflection.returnType(method, "boolean");
    }

    @DataProvider
    public Object[][] eatMethods() {
        return new Object[][]{
                {"eat", void.class, new Class[]{}},
                {"eat", void.class, new Class[]{int.class}},
                {"eat", void.class, new Class[]{int.class, String.class}},
        };
    }

    @Test(description = "Проверка методов eat", dataProvider = "eatMethods", timeOut = 60_000)
    private void testEatMethods(String methodName, Class returnType, Class[] param) {
        Method method = AssertReflection.shouldHaveAndGetMethod(Kotik.class, methodName, param);
        AssertReflection.returnType(method, returnType);
    }

    @Test(description = "Проверка метода liveAnotherDay", timeOut = 60_000)
    public void testLiveAnotherDayMethod() {
        Method method = AssertReflection.shouldHaveAndGetMethod(Kotik.class, "liveAnotherDay");
        testLiveAnotherDayMethodResult(method);
    }

    @Step("Проверка возвращаемого значения")
    private void testLiveAnotherDayMethodResult(Method method) {
        if (method != null) {
            AssertReflection.returnType(method, "String[]");
            Kotik kotik = getInstance("Кот", "мяу", 5, 5);
            String[] day;
            try {
                day = (String[]) method.invoke(kotik);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new AssertionError("Не удалось вызвать метод liveAnotherDay.\n" + e.getMessage());
            }
            Assert.assertEquals(24, day.length, "Возвращаемый массив строк должен содержать 24 элемента");
            for (int i = 0; i < day.length; i++) {
                Assert.assertTrue(day[i].startsWith(i + " - "), "Строка массива должна быть в формате 'час - действие'");
            }
        }
    }

    @Test(description = "Проверка инкремента count при создании объектов", timeOut = 60_000)
    public void testCount() {
        try {
            // arrange
            int startCount = (int) AssertReflection.shouldHaveAndGetMethod(Kotik.class, "getCount").invoke(null);

            //act
            getInstance();
            getInstance(null, null, -10, 0);
            int actualCount = (int) AssertReflection.shouldHaveAndGetMethod(Kotik.class, "getCount").invoke(null);

            //assert
            Assert.assertEquals(startCount + 2, actualCount, "Количество экземпляров класса не соответствует ожидаемому");
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("Ошибка вызова метода: " + e.getMessage());
        }
    }

    @Test(description = "Проверка сравнения голосов", timeOut = 60_000)
    public void testCompareVoice() {
        Kotik kotik1 = getInstance("Котик1", new String("мяу"), 1, 1);
        Kotik kotik2 = getInstance();
        try {
            AssertReflection.shouldHaveAndGetMethod(Kotik.class, "setVoice", String.class).invoke(kotik2, new String("мяу"));
            Method compareVoice = AssertReflection.shouldHaveAndGetMethod(Application.class, "compareVoice", Kotik.class, Kotik.class);
            compareVoice.setAccessible(true);
            Assert.assertTrue((boolean) compareVoice.invoke(null, kotik1, kotik2));
        } catch (IllegalAccessException | InvocationTargetException e) {
            Assert.fail("Ошибка вызова метода: " + e.getMessage());
        }
    }

    @Test(description = "Проверка изменения сытости при активных действиях (негативный сценарий)", timeOut = 60_000)
    public void testNegativeSatiety() {
        Kotik kotik = getInstance("Котик", "мяу", -5, 5);
        try {
            int satietyBeforePlay = getSatiety(kotik);
            Method action = AssertReflection.shouldHaveAndGetMethod(Kotik.class, "play");
            action.setAccessible(true);
            Assert.assertFalse((boolean) action.invoke(kotik),
                    "Котик не просит есть когда голоден");
            Assert.assertEquals(satietyBeforePlay, getSatiety(kotik), "Сытость меняется от бездействия");
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("Ошибка вызова метода:\n" + e.getMessage());
        }
    }

    @Test(description = "Проверка изменения сытости при активных действиях", timeOut = 60_000)
    public void testPositiveSatiety() {
        Kotik kotik = getInstance("Котик", "мяу", 5, 5);
        try {
            int satietyBeforePlay = getSatiety(kotik);
            Method action = AssertReflection.shouldHaveAndGetMethod(Kotik.class, "play");
            action.setAccessible(true);
            Assert.assertTrue((boolean) action.invoke(kotik),
                    "При положительной сытости котик не должен хотеть есть");
            Assert.assertNotEquals(satietyBeforePlay, getSatiety(kotik),
                    "При выполнении действия сытость должна меняться");
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("Ошибка вызова метода:\n" + e.getMessage());
        }
    }

    @Test(description = "Проверка изменения сытости при приеме пищи", timeOut = 60_000)
    public void testEatAndSatiety() {
        Kotik kotik = getInstance("Котик", "мяу", 5, 5);
        try {
            int satietyBeforeEat = getSatiety(kotik);
            Method eat = AssertReflection.shouldHaveAndGetMethod(Kotik.class, "eat");
            eat.setAccessible(true);
            eat.invoke(kotik);
            Assert.assertTrue(satietyBeforeEat < getSatiety(kotik), "Метод eat() не повышает показатель сытости");
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("Ошибка вызова метода:\n" + e.getMessage());
        }
    }

    private int getSatiety(Kotik kotik) {
        try {
            return (int) AssertReflection.shouldHaveAndGetMethod(Kotik.class, "getSatiety").invoke(kotik);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("Ошибка вызова метода getSatiety:\n" + e.getMessage());
        }
    }

    private Kotik getInstance(String name, String voice, int satiety, int weight) {
        try {
            return getAllParamsConstructor().newInstance(name, voice, satiety, weight);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Не удалось создать эекземпляр класса Kotik.\n" + e.getMessage());
        }
    }

    private Kotik getInstance() {
        try {
            return Kotik.class.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Не удалось создать эекземпляр класса Kotik.\n" + e.getMessage());
        }
    }

    private Constructor<Kotik> getAllParamsConstructor() throws NoSuchMethodException {
        return Kotik.class.getDeclaredConstructor(String.class, String.class, int.class, int.class);
    }
}
