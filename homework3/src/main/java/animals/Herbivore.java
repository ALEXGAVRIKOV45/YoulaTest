package animals;

import food.Food;
import food.Grass;
import food.WrongFoodException;

public abstract class Herbivore extends Animal {

    public Herbivore(String name) {
        super(name);
    }

    @Override
    public void eat(Food food) throws WrongFoodException {
        if (food instanceof Grass) {
            satiety += food.getEnergy();
            System.out.println("Травоядное поел, его сытость: " + satiety);
        } else {
            throw new WrongFoodException("Травоядные не едят мясо");
        }
    }

}