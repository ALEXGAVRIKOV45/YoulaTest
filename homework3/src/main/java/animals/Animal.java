package animals;

import food.Food;
import food.WrongFoodException;
import model.Size;

public abstract class Animal {

    protected int satiety;
    protected String name;

    public abstract void eat(Food food) throws WrongFoodException;

    public abstract Size getSize();

    public Animal(String name) {
        this.name = name;
    }

    public int getSatiety() {
        return satiety;
    }

    public String getName() {
        return name;
    }
}
