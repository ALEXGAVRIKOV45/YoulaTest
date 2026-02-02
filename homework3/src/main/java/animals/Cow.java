package animals;

import model.Size;

public class Cow extends Herbivore implements Run, Voice {

    public Cow(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("Корова бежит");
    }

    @Override
    public String getVoice() {
        return "Му-у-у";
    }

    @Override
    public Size getSize() {
        return Size.LARGE;
    }
}
