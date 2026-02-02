import animals.*;
import employee.Worker;
import food.Food;
import food.Grass;
import food.WrongFoodException;
import framework.AssertReflection;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.Aviary;
import model.Size;
import model.WrongSizeException;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Feature("Проверка кода (Java Homework 3)")
@Story("Проверка исключений")
public class ExceptionTest {

    @DataProvider
    public Object[][] classWithFoodException() {
        return new Object[][]{
                {Carnivorous.class},
                {Herbivore.class}
        };
    }

    @DataProvider
    public Object[][] methodsWithoutException() {
        return new Object[][]{
                {Worker.class, "feed", new Class[]{Animal.class, Food.class}, WrongFoodException.class},
                {Aviary.class, "addAnimal", new Class[]{Animal.class}, WrongSizeException.class}
        };
    }

    @DataProvider
    public Object[][] animalsAndAviarySize() {
        return new Object[][]{
                {Kotik.class, "cat", Size.SMALL, Size.LARGE},
                {Duck.class, "duck", Size.SMALL, Size.MEDIUM},
                {Fish.class, "fish", Size.MEDIUM, Size.SMALL}
        };
    }

    @Test(description = "Проверка сигнатуры метода eat", dataProvider = "classWithFoodException", timeOut = 60_000)
    public void eatThrowsFoodExceptionTest(Class aClass) {
        Method method = AssertReflection.shouldHaveAndGetMethod(aClass, "eat", Food.class);
        Assert.assertTrue("В классе " + aClass.getSimpleName() + " в сигнатуре метода eat должно пробрасываться исключение "
                        + WrongFoodException.class.getSimpleName(),
                Arrays.asList(method.getExceptionTypes()).contains(WrongFoodException.class));
    }

    @Test(description = "Проверка выбрасывания исключения методом eat", timeOut = 60_000)
    public void eatThrowFoodExceptionTest() {
        Method method = AssertReflection.shouldHaveAndGetMethod(Carnivorous.class, "eat", Food.class);
        Kotik kotik = new Kotik("Kotik");
        Grass grass = new Grass();
        try {
            method.invoke(kotik, grass);
        } catch (IllegalAccessException | InvocationTargetException e) {
            if (e.getCause().getClass() == WrongFoodException.class) {
                return;
            } else {
                throw new AssertionError("Ошибка при вызове метода eat: " + e.getMessage(), e);
            }
        }
        Assert.fail("При попытке накормить хищника травой метод eat должен выбрасывать WrongFoodException");
    }

    @Test(description = "Проверка, что метод не должен пробрасывать исключение", dataProvider = "methodsWithoutException", timeOut = 60_000)
    public void methodNotThrowsExceptionTest(Class clazz, String methodName, Class[] params, Class exceptionClass) {
        Method method = AssertReflection.shouldHaveAndGetMethod(clazz, methodName, params);
        Assert.assertFalse(String.format("В классе %s в сигнатуре метода %s не должно пробрасываться исключение %s",
                ((Class<?>) clazz).getSimpleName(), methodName, exceptionClass.getSimpleName()),
                Arrays.asList(method.getExceptionTypes()).contains(exceptionClass));
    }

    @Test(description = "Проверка проверяемых исключений", timeOut = 60_000)
    public void checkedExceptionsTest() {
        Assert.assertEquals(String.format("Исключение %s должно относиться к проверяемым исключениям", WrongFoodException.class.getSimpleName()),
                Exception.class, WrongFoodException.class.getSuperclass());
    }

    @Test(description = "Проверка непроверяемых исключений", timeOut = 60_000)
    public void uncheckedExceptionsTest() {
        Assert.assertEquals(String.format("Исключение %s должно относиться к непроверяемым исключениям", WrongSizeException.class.getSimpleName()),
                RuntimeException.class, WrongSizeException.class.getSuperclass());
    }

    @Test(description = "Проверка, что метод addAnimal бросает исключение", dataProvider = "animalsAndAviarySize", timeOut = 60_000)
    public void addAnimalThrowException(Class clazz, String animalName, Size normalAviarySize, Size wrongAviarySize) {
        Animal animalInstance = getAnimalInstance(clazz, animalName);
        Aviary<Animal> appropriateAviary = (Aviary<Animal>) getInstanceWithParams(Aviary.class, normalAviarySize);
        Aviary<Animal> notAppropriateAviary = (Aviary<Animal>) getInstanceWithParams(Aviary.class, wrongAviarySize);
        try {
            invokeAddAnimal(appropriateAviary, animalInstance);
        } catch (WrongSizeException ex) {
            Assert.fail("Не удалось поместить животное в соответствующий ему вольер");
        }
        try {
            invokeAddAnimalWithThrowsException(notAppropriateAviary, animalInstance);
        } catch (WrongSizeException | InvocationTargetException | IllegalAccessException ex) {
            Assert.assertTrue(true);
        }
    }

    private Object getInstanceWithParams(Class clazz, Size size) {
        try {
            return clazz.getDeclaredConstructor(Size.class).newInstance(size);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Не удалось создать экземпляр класса " + clazz.getSimpleName() + ".\n" + e.getMessage());
        }
    }

    private Animal getAnimalInstance(Class clazz, String name) {
        try {
            return (Animal) clazz.getDeclaredConstructor(String.class).newInstance(name);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Не удалось создать экземпляр класса " + clazz.getSimpleName() + ".\n" + e.getMessage());
        }
    }

    private void invokeAddAnimal(Aviary aviary, Animal animal) {
        Method method = AssertReflection.shouldHaveAndGetMethod(aviary.getClass(), "addAnimal", Animal.class);
        method.setAccessible(true);
        try {
            method.invoke(aviary, animal);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("Ошибка вызова метода " + method.getName() + ".\n" + e.getMessage());
        }
    }

    private void invokeAddAnimalWithThrowsException(Aviary aviary, Animal animal) throws InvocationTargetException, IllegalAccessException {
        Method method = AssertReflection.shouldHaveAndGetMethod(aviary.getClass(), "addAnimal", Animal.class);
        method.setAccessible(true);
        method.invoke(aviary, animal);
    }
}
