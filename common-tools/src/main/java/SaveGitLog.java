import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGitLog {
    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        File file = new File(new File(currentDir).getParentFile().getParentFile().getParentFile() + File.separator + "git.log");
        String message = String.join(" ", args);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            System.out.println("Writing to file " + file);
            bw.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
