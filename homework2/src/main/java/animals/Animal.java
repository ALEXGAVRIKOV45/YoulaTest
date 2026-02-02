package animals;

import food.Food;

public abstract class Animal {

    public abstract void eat(Food food);

    protected int satiety;

    public int getSatiety() {
        return satiety;
    }

}
