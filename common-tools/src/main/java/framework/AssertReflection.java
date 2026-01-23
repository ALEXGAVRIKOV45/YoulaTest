package framework;

import io.qameta.allure.Step;
import org.testng.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class AssertReflection {

    @Step("Проверка переменной {fieldName} в классе {aClass.name}")
    public static Field shouldHaveAndGetField(Class aClass, String fieldName) {
        try {
            return aClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new AssertionError("Не найдена переменная экземпляра " + fieldName + "\n\n" + e.getMessage());
        }
    }

    @Step("Проверка метода {methodName} в классе {aClass.name}")
    public static Method shouldHaveAndGetMethod(Class aClass, String methodName, Class... parameterTypes) {
        try {
            return aClass.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new AssertionError("В классе " + aClass.getSimpleName()
                    + " не найден метод " + methodName + "\n\n" + e.getMessage(), e);
        }
    }

    @Step("Проверка модификатора доступа в поле {field.name}")
    public static void isPrivate(Field field) {
        Assert.assertTrue(Modifier.isPrivate(field.getModifiers()), "Поле " + field.getName() + " должно быть private");
    }

    @Step("Проверка модификатора static в поле {field.name}")
    public static void isNotStatic(Field field) {
        Assert.assertFalse(Modifier.isStatic(field.getModifiers()), "Поле " + field.getName() + " не должно быть static");
    }

    @Step("Проверка модификатора static в поле {field.name}")
    public static void isStatic(Field field) {
        Assert.assertTrue(Modifier.isStatic(field.getModifiers()), "Поле " + field.getName() + " должно быть static");
    }

    @Step("Проверка модификатора final в поле {field.name}")
    public static void isFinal(Field field) {
        Assert.assertTrue(Modifier.isFinal(field.getModifiers()), "Поле " + field.getName() + " должно быть final");
    }

    @Step("Проверка возвращаемого значения метода {method.name}")
    public static void returnType(Method method, String type) {
        Assert.assertEquals(type, method.getReturnType().getSimpleName(), "Тип возвращаемого значения должен быть " + type);
    }

    @Step("Проверка возвращаемого значения метода {method.name}")
    public static void returnType(Method method, Class type) {
        Assert.assertEquals(type, method.getReturnType(), "Тип возвращаемого значения должен быть " + type.getSimpleName());
    }

    @Step("Проверка модификатора static в методе {method.name}")
    public static void isStatic(Method method) {
        Assert.assertTrue(Modifier.isStatic(method.getModifiers()), "Метод " + method.getName() + " должен быть static");
    }

    @Step("Проверка отсутствия модификатора static в методе {method.name}")
    public static void isNotStatic(Method method) {
        Assert.assertFalse(Modifier.isStatic(method.getModifiers()), "Метод " + method.getName() + " не должен быть static");
    }
}

