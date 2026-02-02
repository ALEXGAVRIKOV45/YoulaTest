import animals.*;
import employee.Worker;
import food.Food;
import food.Grass;
import food.Meat;
import framework.AssertReflection;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Feature("Проверка кода (Java Homework 2)")
@Story("Проверка кода зоопарка")
public class ZooTest {

    @DataProvider
    public Object[][] workerMethods() {
        return new Object[][]{
                {"feed", new Class[]{Animal.class, Food.class}},
                {"getVoice", new Class[]{Voice.class}}
        };
    }

    @Test(description = "Проверка метода eat", timeOut = 60_000)
    public void testEatMethod() {
        try {
            Method eatMethod = Animal.class.getMethod("eat", Food.class);
            Assert.assertTrue(Modifier.isAbstract(eatMethod.getModifiers()), "Метод eat() не содержит ключевое слово abstract");
        } catch (NoSuchMethodException e) {
            Assert.fail("В классе Animal нет метода eat()");
        }

        try {
            Herbivore.class.getMethod("eat", Food.class);
        } catch (NoSuchMethodException e) {
            Assert.fail("В классе Herbivore нет метода eat()");
        }

        try {
            Carnivorous.class.getMethod("eat", Food.class);
        } catch (NoSuchMethodException e) {
            Assert.fail("В классе Carnivorous нет метода eat()");
        }
    }

    @Test(dataProvider = "workerMethods", description = "Проверка методов в классе Worker", timeOut = 60_000)
    public void testWorker(String methodName, Class[] parameterTypes) {
        AssertReflection.shouldHaveAndGetMethod(Worker.class, methodName, parameterTypes);
    }

    @Test(description = "Проверка сытости", timeOut = 60_000)
    public void testLogic() {
        Worker worker = (Worker) getInstance(Worker.class);
        Meat meat = (Meat) getInstance(Meat.class);
        Grass grass = (Grass) getInstance(Grass.class);
        Duck duck = (Duck) getInstance(Duck.class);
        int satietyBefore;

        satietyBefore = getSatiety(duck);
        doFeed(worker, duck, grass);
        Assert.assertNotEquals(satietyBefore, getSatiety(duck), "Сытость не изменилась как ожидалось");
        Assert.assertTrue(satietyBefore < getSatiety(duck), "Сытость должна быть больше, чем до кормления");

        satietyBefore = getSatiety(duck);
        doFeed(worker, duck, meat);
        Assert.assertEquals(satietyBefore, getSatiety(duck), "Сытость не должна меняться");
    }

    @Test(description = "проверка питательности еды", timeOut = 60_000)
    public void testFoodEnergy() {
        Grass grass = (Grass) getInstance(Grass.class);
        Integer grassEnergyValue = getEnergyValue(grass);

        Meat meat = (Meat) getInstance(Meat.class);
        Integer meatEnergyValue = getEnergyValue(meat);

        Assert.assertFalse(meatEnergyValue.equals(grassEnergyValue),
                "Питательность травы равна питательности мяса, эти параметры должны отличаться.");
    }

    @Test(description = "Проверка точки входа в классе Zoo", timeOut = 60_000)
    public void testZooContainsMainMethod() {
        AssertReflection.shouldHaveAndGetMethod(Zoo.class, "main", String[].class);
    }

    @Test(description = "Проверка метода createPond", timeOut = 60_000)
    public void testPound() {
        Method method = AssertReflection.shouldHaveAndGetMethod(Zoo.class, "createPond");
        AssertReflection.returnType(method, Swim[].class);
        AssertReflection.isStatic(method);
    }

    private Object getInstance(Class clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Не удалось создать экземпляр класса " + clazz.getSimpleName() + ".\n" + e.getMessage());
        }
    }

    private Integer getEnergyValue(Food food) {
        Method getEnergy = AssertReflection.shouldHaveAndGetMethod(food.getClass(), "getEnergy");
        getEnergy.setAccessible(true);
        try {
            return  (Integer) getEnergy.invoke(food);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("Ошибка вызова метода " + getEnergy.getName() + ".\n" + e.getMessage());
        }
    }

    private int getSatiety(Animal animal) {
        try {
            Method method = AssertReflection.shouldHaveAndGetMethod(animal.getClass().getSuperclass().getSuperclass(), "getSatiety");
            method.setAccessible(true);
            return (int) method.invoke(animal);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("Ошибка вызова метода getSatiety:\n" + e.getMessage());
        }
    }

    private void doFeed(Worker worker, Animal animal, Food food) {
        Method method = AssertReflection.shouldHaveAndGetMethod(worker.getClass(), "feed", Animal.class, Food.class);
        method.setAccessible(true);
        try {
            method.invoke(worker, animal, food);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("Ошибка вызова метода " + method.getName() + ".\n" + e.getMessage());
        }
    }
}
