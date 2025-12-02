import jdk.nashorn.internal.objects.NativeJSON;
import org.vosk.Model;
import org.vosk.Recognizer;
import org.vosk.LibVosk;
import javax.sound.sampled.*;

public class ReconhecedorVoz {

    private GerenciadorComandos gerenciadorComandos;

    public ReconhecedorVoz(GerenciadorComandos gerenciadorComandos) {
        this.gerenciadorComandos = gerenciadorComandos;
    }

    public void iniciar() throws Exception{

        Model model = new Model("F:\\00 TRABALHO\\00__CODES\\CODIGOS_PESSOAIS\\Codes\\Java\\OUTROS\\AssistenteVoz\\VoskSpeech\\vosk-model-small-pt-0.3");

        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();

        Recognizer recognizer = new Recognizer(model, 16000);

        byte[] buffer = new byte[4096];

        System.out.println("Fale algo...");

        while (true) {
            int bytesRead = microphone.read(buffer, 0, buffer.length);
            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                String jsonResultado = recognizer.getResult();
                gerenciadorComandos.processarTexto(jsonResultado);
            }
        }
    }
}
