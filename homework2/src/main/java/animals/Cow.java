package animals;

public class Cow extends Herbivore implements Run, Voice {

    @Override
    public void run() {
        System.out.println("Корова бежит");
    }

    @Override
    public String getVoice() {
        return "Му-у-у";
    }

}
