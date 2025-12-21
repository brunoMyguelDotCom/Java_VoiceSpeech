// Todos os prints foram utilizados para facilitar o debug

import java.util.HashMap;
import java.util.Map;

public class GerenciadorComandos {

    private final Map<String, Runnable> comandos = new HashMap<>();
    private boolean modoComando = false;

    public short numTentativas = 0;

    public GerenciadorComandos() {
        // Executáveis diretos (caminhos com espaços entre aspas)
        comandos.put("photoshop", () -> Executor.exec("\"C:\\Program Files\\Adobe\\Adobe Photoshop 2023\\Photoshop.exe\""));
        comandos.put("desenho", () -> Executor.exec("\"C:\\Program Files\\Corel\\CorelDRAW Graphics Suite 2020\\Programs64\\CorelDRW.exe\""));
        comandos.put("navegador", () -> Executor.exec("\"C:\\Program Files\\Mozilla Firefox\\firefox.exe\""));
        comandos.put("java", () -> Executor.exec("\"C:\\Program Files\\JetBrains\\IntelliJ IDEA 2025.2.2\\bin\\idea64.exe\""));
        comandos.put("arquivos", () -> Executor.exec("explorer.exe \"F:\\00 TRABALHO\""));

        // Atalhos .lnk usando explorer.exe
        comandos.put("som", () -> Executor.exec("explorer.exe \"C:\\Users\\Bruno\\Desktop\\Spotify.lnk\""));
        comandos.put("trabalho", () -> Executor.exec("explorer.exe \"C:\\Users\\Bruno\\Desktop\\Visual Studio Code.lnk\""));
        comandos.put("brilho", () -> Executor.exec("explorer.exe \"C:\\Users\\Bruno\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Dimmer.lnk\""));
        comandos.put("delta", () -> Executor.exec("explorer.exe \"\"C:\\Users\\Bruno\\Desktop\\Delta Force.url\""));

    }

    public void ativarModoComando() {
        modoComando = true;
        System.out.println("Modo comando ativado...");
        Som.tocar("src/sons/ativar.wav");
    }

    public void processarTexto(String texto) {
        texto = texto.toLowerCase();

        if (texto.contains("computador") && !modoComando) {
            ativarModoComando();
            return;
        }

        // flag
        boolean encontrado = false;
        if (modoComando) {
            for (String comandoChave : comandos.keySet()) {
                if (texto.contains(comandoChave)) {
                    encontrado = true;
                    comandos.get(comandoChave).run();
                    modoComando = false;
                    Som.tocar("src/sons/desativar.wav");
                    return;
                }
            }
            if (!encontrado) {
                Som.tocar("src/sons/erro.wav");
                numTentativas++;
                if (numTentativas == 3) {
                    Som.tocar("src/sons/desativar.wav");
                    numTentativas = 0;
                    modoComando = false;
                }
            }
        }
    }
}
