package animals;

public class Kotik {

    // константы
    /** количество методов действия */
    private static final int METHODS = 5;

    // статические переменные
    /** счетчик экземпляров класса */
    private static int count;

    // переменные экземпляра
    /** имя */
    private String name;
    /** голос */
    private String voice;
    /** сытость */
    private int satiety;
    /** вес */
    private int weight;


    // конструктор с параметрами
    public Kotik(String name, String voice, int satiety, int weight) {
        count++;
        this.name = name;
        this.voice = voice;
        this.satiety = satiety;
        this.weight = weight;
    }

    // конструктор без параметров
    public Kotik() {
        count++;
    }


    // геттеры
    public static int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public String getVoice() {
        return voice;
    }

    public int getSatiety() {
        return satiety;
    }

    public int getWeight() {
        return weight;
    }

    // сеттеры
    public void setName(String name) {
        this.name = name;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    // методы, описывающие поведение

    /** игарть */
    public boolean play() {
        if (satiety > 0) {
            System.out.println(name + " играет");
            satiety--;
            return true;
        }
        System.out.println(name + " хочет есть");
        return false;
    }

    /** спать */
    public boolean sleep() {
        if (satiety > 0) {
            System.out.println(name + " спит");
            satiety--;
            return true;
        }
        System.out.println(name + " хочет есть");
        return false;
    }

    /** умываться */
    public boolean wash() {
        if (satiety > 0) {
            System.out.println(name + " умывается");
            satiety--;
            return true;
        }
        System.out.println(name + " хочет есть");
        return false;
    }

    /** гулять */
    public boolean walk() {
        if (satiety > 0) {
            System.out.println(name + " гуляет");
            satiety--;
            return true;
        }
        System.out.println(name + " хочет есть");
        return false;
    }

    /** охотиться */
    public boolean hunt() {
        if (satiety > 0) {
            System.out.println(name + " охотится");
            satiety--;
            return true;
        }
        System.out.println(name + " хочет есть");
        return false;
    }

    // методы еды
    public void eat(int energy) {
        this.satiety += energy;
        System.out.println(name + " повысил сытость на " + energy + " единиц");
    }

    public void eat(int energy, String food) {
        this.satiety += energy;
        System.out.printf("%s съел %s, сытость +%d%n", name, food, energy);
    }

    public void eat() {
        eat(5, "сметана");
    }


    /** прожить день */
    public String[] liveAnotherDay() {
        String[] result = new String[24];
        for (int i = 0; i < 24; i++) {
            System.out.print(i + " ");
            String action;
            int randomValue = (int) (Math.random() * METHODS) + 1;
            switch (randomValue) {
                case 1:
                    if (!play()) {
                        action = "играл";
                    } else {
                        eat(1);
                        action = "кушал";
                    }
                    break;
                case 2:
                    if (sleep()) {
                        action = "спал";
                    } else {
                        eat(2, "мясо");
                        action = "кушал";
                    }
                    break;
                case 3:
                    if (wash()) {
                        action = "умывался";
                    } else {
                        eat();
                        action = "кушал";
                    }
                    break;
                case 4:
                    if (walk()) {
                        action = "гулял";
                    } else {
                        eat();
                        action = "кушал";
                    }
                    break;
                case 5:
                    if (hunt()) {
                        action = "охотился";
                    } else {
                        eat();
                        action = "кушал";
                    }
                    break;
                default:
                    System.out.println("Ошибка: количество методов действий меньше " + randomValue);
                    action = "";
            }
            result[i] = i + " - " + action;
        }
        return result;
    }
}
