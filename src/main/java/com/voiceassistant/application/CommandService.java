package com.voiceassistant.application;// Todos os prints foram utilizados para facilitar o debug

import com.voiceassistant.audio.SoundPlayer;
import com.voiceassistant.infrastructure.CommandExecutor;
import com.voiceassistant.infrastructure.Config;

import java.util.HashMap;
import java.util.Map;

public class CommandService {

    // private final Map<String, Runnable> commands = new HashMap<>();
    private Config config;

    // flag
    private boolean commandMode = false;
    public short trysNum = 0;

    public CommandService(Config config) {
        this.config = config;
    }

//    public CommandService() {
//        // Executaveis diretos
//        commands.put("photoshop", () -> CommandExecutor.exec("\"C:\\Program Files\\Adobe\\Adobe Photoshop 2023\\Photoshop.exe\""));
//        commands.put("desenho", () -> CommandExecutor.exec("\"C:\\Program Files\\Corel\\CorelDRAW Graphics Suite 2020\\Programs64\\CorelDRW.exe\""));
//        commands.put("navegador", () -> CommandExecutor.exec("\"C:\\Program Files\\Mozilla Firefox\\firefox.exe\""));
//        commands.put("java", () -> CommandExecutor.exec("\"C:\\Program Files\\JetBrains\\IntelliJ IDEA 2025.2.2\\bin\\idea64.exe\""));
//        commands.put("arquivos", () -> CommandExecutor.exec("explorer.exe \"F:\\00 TRABALHO\""));
//
//        // Atalhos .lnk
//        commands.put("som", () -> CommandExecutor.exec("explorer.exe \"C:\\Users\\Bruno\\Desktop\\Spotify.lnk\""));
//        commands.put("trabalho", () -> CommandExecutor.exec("explorer.exe \"C:\\Users\\Bruno\\Desktop\\Visual Studio Code.lnk\""));
//        commands.put("brilho", () -> CommandExecutor.exec("explorer.exe \"C:\\Users\\Bruno\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Dimmer.lnk\""));
//        commands.put("delta", () -> CommandExecutor.exec("explorer.exe \"\"C:\\Users\\Bruno\\Desktop\\Delta Force.url\""));
//    }

    public void activeCommandMode() {
        commandMode = true;
        SoundPlayer.play("src/main/resources/sounds/ativar.wav");
    }

    public void processText(String text) {
        text = text.toLowerCase();
        if (text.contains(config.wakeWord.toLowerCase()) && !commandMode) {
            activeCommandMode();
            return;
        }

        if (commandMode && config.commands != null) {
            for (String keyword : config.commands.keySet()) {
                if (text.contains(keyword.toLowerCase())) {
                    CommandExecutor.exec(config.commands.get(keyword));
                    commandMode = false;
                    trysNum = 0;
                    SoundPlayer.play("src/main/resources/sounds/desativar.wav");
                    return;
                }
            }

            // nenhum comando encontrado
            SoundPlayer.play("src/main/resources/sounds/erro.wav");
            trysNum++;

            if (trysNum >= 3) {
                SoundPlayer.play("src/main/resources/sounds/desativar.wav");
                trysNum = 0;
                commandMode = false;
            }
        }
    }
}
