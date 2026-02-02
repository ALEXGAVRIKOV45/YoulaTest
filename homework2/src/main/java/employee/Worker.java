package employee;

import animals.Animal;
import animals.Voice;
import food.Food;

public class Worker {

    public void feed(Animal animal, Food food) {
        animal.eat(food);
    }

    public void getVoice(Voice voiceAnimal) {
        System.out.println(voiceAnimal.getVoice());
    }

}
