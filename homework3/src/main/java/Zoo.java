import animals.*;
import employee.Worker;
import food.Food;
import food.Grass;
import food.Meat;
import model.Aviary;
import model.Size;

public class Zoo {

    private static Aviary<Carnivorous> carnivorousAviary = new Aviary<>(Size.MEDIUM);
    private static Aviary<Herbivore> herbivoreAviary = new Aviary<>(Size.LARGE);

    public static void main(String[] args) {
        // создать животных
        Cow cow = new Cow("Мурка");
        Duck duck = new Duck("Donald Duck");
        Horse horse = new Horse("Юлий");
        Fish fish = new Fish("Щука");
        Tiger tiger = new Tiger("Шер-Хан");
        Kotik kotik = new Kotik("Матроскин");

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

        fillCarnivorousAviary();
        Carnivorous carnivorous = getCarnivorous(fish.getName());
        boolean b1 = carnivorousAviary.removeAnimal(fish.getName());

        fillHerbivoreAviary();
        Herbivore herbivore = getHerbivore(cow.getName());
        boolean b2 = herbivoreAviary.removeAnimal(cow.getName());
    }

    private static Carnivorous getCarnivorous(String name) {
        return carnivorousAviary.getAnimal(name);
    }

    private static Herbivore getHerbivore(String name) {
        return herbivoreAviary.getAnimal(name);
    }

    private static void fillCarnivorousAviary() {
        Cow cow = new Cow("Гаврюша");
        Fish fish = new Fish("Акула");
        Tiger tiger = new Tiger("Тигра");
        Kotik kotik = new Kotik("Леопольд");

        // carnivorousAviary.addAnimal(cow); // так нельзя, ошибка компиляции
        carnivorousAviary.addAnimal(fish);
        carnivorousAviary.addAnimal(tiger);
        carnivorousAviary.addAnimal(kotik);
    }

    private static void fillHerbivoreAviary() {
        Cow cow = new Cow("Зорька");
        Duck duck = new Duck("Черный плащ");
        Horse horse = new Horse("Спирит");
        Kotik kotik = new Kotik("Кот в сапогах");

        // herbivoreAviary.addAnimal(kotik); // так нельзя, ошибка компиляции
        herbivoreAviary.addAnimal(cow);
        herbivoreAviary.addAnimal(duck);
        herbivoreAviary.addAnimal(horse);
    }

    public static Swim[] createPond() {
        return new Swim[]{new Duck("Утка"), new Fish("Рыба")};
    }

}
