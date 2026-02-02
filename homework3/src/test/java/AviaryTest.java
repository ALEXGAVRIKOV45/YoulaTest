import animals.Animal;
import animals.Duck;
import animals.Fish;
import animals.Kotik;
import framework.AssertReflection;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.Aviary;
import model.Size;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

@Feature("Проверка кода (Java Homework 3)")
@Story("Проверка класса Aviary")
public class AviaryTest {

    @DataProvider
    public Object[][] fields() {
        return new Object[][]{
                {"size", Size.class, Modifier.PRIVATE},
                {"aviaryMap", HashMap.class, Modifier.PRIVATE}
        };
    }

    @Test(description = "Проверка переменных", dataProvider = "fields", timeOut = 60_000)
    public void testFields(String fieldName, Class type, int mod) {
        Field field = AssertReflection.shouldHaveAndGetField(Aviary.class, fieldName);
        Assert.assertEquals(String.format("Поле %s должно иметь тип %s", fieldName, type.getSimpleName()), type, field.getType());
        Assert.assertEquals(String.format("Поле %s должно иметь модификатор %s", fieldName, Modifier.toString(mod)), mod, field.getModifiers());
    }

    @DataProvider
    public Object[][] methods() {
        return new Object[][]{
                {"addAnimal", void.class, Animal.class},
                {"getAnimal", Animal.class, String.class},
                {"removeAnimal", boolean.class, String.class}
        };
    }

    @Test(description = "Проверка методов", dataProvider = "methods", timeOut = 60_000)
    private void testMethods(String methodName, Class returnType, Class param) {
        Method method = AssertReflection.shouldHaveAndGetMethod(Aviary.class, methodName, param);
        AssertReflection.returnType(method, returnType);
    }

    @DataProvider
    public Object[][] animals() {
        return new Object[][]{
                {Kotik.class, "cat", Size.SMALL},
                {Duck.class, "duck", Size.SMALL},
                {Fish.class, "fish", Size.MEDIUM}
        };
    }

    @Test(description = "Проверка размера коллекции после добавления/удаления животных", dataProvider = "animals", timeOut = 60_000)
    public void testCollection(Class clazz, String animalName, Size size) throws IllegalAccessException {
        Aviary<Animal> aviary = (Aviary<Animal>) getInstanceWithParams(Aviary.class, size);
        Field field = AssertReflection.shouldHaveAndGetField(Aviary.class, "aviaryMap");
        field.setAccessible(true);
        HashMap map = (HashMap) field.get(aviary);
        Animal animalInstance = getAnimalInstance(clazz, animalName);
        int before = map.size();
        aviary.addAnimal(animalInstance);
        int afterAdding = map.size();
        aviary.removeAnimal(animalInstance.getName());
        int afterRemove = map.size();

        Assert.assertTrue("Размер коллекции после добавления животного должен был увеличиться",before < afterAdding);
        Assert.assertTrue("Размер коллекции после удаления животного должен был уменьшиться",afterAdding > afterRemove);
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
}
