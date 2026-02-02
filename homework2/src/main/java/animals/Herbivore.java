package animals;

import food.Food;
import food.Grass;

public abstract class Herbivore extends Animal {

    @Override
    public void eat(Food food) {
        if (food instanceof Grass) {
            satiety += food.getEnergy();
            System.out.println("Травоядное поел, его сытость: " + satiety);
        } else {
            System.out.println("Травоядные не едят мясо");
        }
    }
}