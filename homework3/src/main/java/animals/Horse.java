package animals;

import model.Size;

public class Horse extends Herbivore implements Run, Voice {

    public Horse(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("Лошадь бежит");
    }

    @Override
    public String getVoice() {
        return "И-го-го";
    }

    @Override
    public Size getSize() {
        return Size.LARGE;
    }
}
