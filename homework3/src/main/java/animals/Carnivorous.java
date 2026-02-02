package animals;

import food.Food;
import food.Meat;
import food.WrongFoodException;

public abstract class Carnivorous extends Animal {

    public Carnivorous(String name) {
        super(name);
    }

    @Override
    public void eat(Food food) throws WrongFoodException {
        if (food instanceof Meat) {
            satiety += food.getEnergy();
            System.out.println("Хищник поел, его сытость: " + satiety);
        } else {
            throw new WrongFoodException("Хищники не едят траву");
        }
    }

}
