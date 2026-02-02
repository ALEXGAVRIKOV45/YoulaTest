import model.Calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] params = readParams();
        System.out.println(Calculator.execute(params));
    }

    public static String[] readParams() {
        String[] params = new String[3];
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < params.length; i++) {
            params[i] = in.nextLine();
        }
        in.close();
        return params;
    }
}
