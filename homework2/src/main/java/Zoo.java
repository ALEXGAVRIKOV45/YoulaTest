import animals.*;
import employee.Worker;
import food.Food;
import food.Grass;
import food.Meat;

public class Zoo {
    public static void main(String[] args) {
        // создать животных
        Cow cow = new Cow();
        Duck duck = new Duck();
        Horse horse = new Horse();
        Fish fish = new Fish();
        Tiger tiger = new Tiger();
        Kotik kotik = new Kotik();

        // создать рабочих
        Worker worker = new Worker();

        // создать еду
        Meat meat = new Meat();
        Grass grass = new Grass();

        // покормить всех животных разной едой
        Animal[] animals = {cow, duck, horse, fish, tiger, kotik};
        Food[] foods = {meat, grass};

        for (Animal animal : animals) {
            for (Food food : foods) {
                worker.feed(animal, food);
                System.out.println(animal.getSatiety());
            }
        }

        // заставить животных подать голос
        worker.getVoice(cow);
        worker.getVoice(duck);
        worker.getVoice(horse);
        // worker.getVoice(fish); // так нельзя, ошибка компиляции
        worker.getVoice(tiger);
        worker.getVoice(kotik);

        // животные плавают в пруду
        Swim[] pond = createPond();
        for (int i = 0; i < pond.length; i++) {
            pond[i].swim();
        }
    }

    public static Swim[] createPond() {
        return new Swim[]{new Duck(), new Fish()};
    }

}
