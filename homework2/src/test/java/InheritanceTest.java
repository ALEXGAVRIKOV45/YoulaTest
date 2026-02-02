import animals.*;
import food.Food;
import food.Grass;
import food.Meat;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

@Feature("Проверка кода (Java Homework 2)")
@Story("Проверка наследования")
public class InheritanceTest {

    @DataProvider
    public Object[][] inheritance() {
        return new Object[][]{
                {Herbivore.class, Animal.class},
                {Carnivorous.class, Animal.class},
                {Duck.class, Herbivore.class},
                {Kotik.class, Carnivorous.class},
                {Grass.class, Food.class},
                {Meat.class, Food.class}
        };
    }

    @DataProvider
    public Object[][] implementation() {
        return new Object[][]{
                {Duck.class, Fly.class},
                {Duck.class, Run.class},
                {Duck.class, Swim.class},
                {Duck.class, Voice.class},
                {Kotik.class, Run.class},
                {Kotik.class, Voice.class},
                {Fish.class, Swim.class}
        };
    }

    @Test(dataProvider = "inheritance", description = "Проверка наследования", timeOut = 60_000)
    public void testInheritance(Class child, Class parent) {
        Assert.assertEquals(child.getSuperclass(), parent,
                "Класс " + child.getSimpleName() + " должен быть наследником " + parent.getSimpleName());
    }

    @Test(description = "Проверка наследования Fish", timeOut = 60_000)
    public void testFishInheritance() {
        Class parent = Fish.class.getSuperclass();
        boolean result = parent.equals(Carnivorous.class) || parent.equals(Herbivore.class);
        Assert.assertTrue(result, "Класс Fish должен быть наследником Carnivorous или Herbivore");
    }

    @Test(dataProvider = "implementation", description = "Проверка реализации интерфейсов", timeOut = 60_000)
    public void testInterfaces(Class clazz, Class interfaceName) {
        Class<?>[] interfaces = clazz.getInterfaces();
        Assert.assertTrue(Arrays.asList(interfaces).contains(interfaceName),
                "Класс " + clazz.getSimpleName() + " не реализует интерфейс " + interfaceName.getSimpleName());
    }
}
