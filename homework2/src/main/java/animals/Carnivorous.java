package animals;

import food.Food;
import food.Meat;

public abstract class Carnivorous extends Animal {

    @Override
    public void eat(Food food) {
        if (food instanceof Meat) {
            satiety += food.getEnergy();
            System.out.println("Хищник поел, его сытость: " + satiety);
        } else {
            System.out.println("Хищники не едят траву");
        }
    }

}
