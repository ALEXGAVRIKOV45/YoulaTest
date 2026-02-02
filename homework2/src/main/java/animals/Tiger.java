package animals;

public class Tiger extends Carnivorous implements Run, Voice {

    @Override
    public void run() {
        System.out.println("Тигр бежит");
    }

    @Override
    public String getVoice() {
        return "Р-р-р";
    }

}
