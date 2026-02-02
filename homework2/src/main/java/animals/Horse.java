package animals;

public class Horse extends Herbivore implements Run, Voice {

    @Override
    public void run() {
        System.out.println("Лошадь бежит");
    }

    @Override
    public String getVoice() {
        return "И-го-го";
    }

}
