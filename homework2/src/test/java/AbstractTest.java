import animals.Animal;
import animals.Carnivorous;
import animals.Herbivore;
import food.Food;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Modifier;

@Feature("Проверка кода (Java Homework 2)")
@Story("Проверка абстрактных классов и методов")
public class AbstractTest {

    @DataProvider
    public Object[][] abstractClasses() {
        return new Object[][] {
                {Animal.class},
                {Herbivore.class},
                {Carnivorous.class},
                {Food.class}
        };
    }

    @Test(dataProvider = "abstractClasses", description = "Проверка абстрактных классов", timeOut = 60_000)
    public void testAbstractClasses(Class clazz) {
        int mod;
        mod = ((Class<?>) clazz).getModifiers();
        Assert.assertTrue(Modifier.isAbstract(mod), "В классе " + clazz.getSimpleName() + " ключевое слово abstract отсутствует");
    }
}
