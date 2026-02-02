package animals;

import model.Size;

public class Tiger extends Carnivorous implements Run, Voice {

    public Tiger(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("Тигр бежит");
    }

    @Override
    public String getVoice() {
        return "Р-р-р";
    }

    @Override
    public Size getSize() {
        return Size.MEDIUM;
    }
}
