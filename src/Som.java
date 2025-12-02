import javax.sound.sampled.*;
import java.io.File;

public class Som {
    public static void tocar(String caminho) {
        try {
            File audioFile = new File(caminho);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}