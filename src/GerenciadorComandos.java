import java.util.HashMap;
import java.util.Map;

public class GerenciadorComandos {

    private Map<String, Runnable> comandos = new HashMap<>();
    private boolean modoComando = false;

    public GerenciadorComandos() {
        comandos.put("photoshop", () -> Executor.exec("\"C:\\Program Files\\Adobe\\Adobe Photoshop 2023\\Photoshop.exe\""));
        comandos.put("desenho", () -> Executor.exec("\"C:\\Program Files\\Corel\\CorelDRAW Graphics Suite 2020\\Programs64\\CorelDRW.exe\""));
        comandos.put("navegador", () -> Executor.exec("\"C:\\Program Files\\Mozilla Firefox\\firefox.exe\""));
        comandos.put("música", () -> Executor.exec("C:\\Users\\Bruno\\AppData\\Roaming\\Spotify\\Spotify.exe"));
        comandos.put("java", () -> Executor.exec("\"C:\\Program Files\\JetBrains\\IntelliJ IDEA 2025.2.2\\bin\\idea64.exe\""));
        comandos.put("código", () -> Executor.exec("\"C:\\Users\\Bruno\\AppData\\Local\\Programs\\Microsoft VS Code\\Code.exe\""));
        comandos.put("arquivos", () -> Executor.exec("explorer.exe C:\\\\Users\\\\Bruno\\\\Downloads"));
        comandos.put("trabalho", () -> Executor.exec("explorer.exe F:\\00 TRABALHO"));
    }

    public void ativarModoComando() {
        modoComando = true;
        System.out.println("Modo comando ativado...");
        Som.tocar("F:\\00 TRABALHO\\00__CODES\\CODIGOS_PESSOAIS\\Codes\\Java\\OUTROS\\AssistenteVoz\\VoskSpeech\\src\\sons\\ativar.wav");
    }

    public void processarTexto(String texto) {
        texto = texto.toLowerCase();

        if(texto.contains("computador")){
            ativarModoComando();
            return;
        }

        if (modoComando) {
            for (String comandoChave : comandos.keySet()) {
                if (texto.contains(comandoChave)) {
                    comandos.get(comandoChave).run();
                    modoComando = false;
                    Som.tocar("F:\\00 TRABALHO\\00__CODES\\CODIGOS_PESSOAIS\\Codes\\Java\\OUTROS\\AssistenteVoz\\VoskSpeech\\src\\sons\\desativar.wav");
                    return;
                }
            }
        }
    }
}
