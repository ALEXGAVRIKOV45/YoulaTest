import animals.Kotik;

public class Application {
    public static void main(String[] args) {
        Kotik kotik1 = new Kotik("Котик1", new String("мяу"), 1, 1);

        Kotik kotik2 = new Kotik();
        kotik2.setName("Котик2");
        kotik2.setVoice(new String("мяу"));
        kotik2.setSatiety(2);
        kotik2.setWeight(2);

        String[] day = kotik1.liveAnotherDay();
        for (String hour : day) {
            System.out.println(hour);
        }

        System.out.printf("%s весит %d%n", kotik1.getName(), kotik1.getWeight());

        System.out.println(compareVoice(kotik1, kotik2));

        System.out.println(Kotik.getCount());
    }

    public static boolean compareVoice(Kotik k1, Kotik k2) {
        return k1.getVoice().equals(k2.getVoice());
    }
}
